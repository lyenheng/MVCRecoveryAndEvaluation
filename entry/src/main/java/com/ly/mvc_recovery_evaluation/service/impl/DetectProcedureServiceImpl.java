package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.MvcRecovery;
import com.ly.mvc_recovery_evaluation.bean.ProjectNode;
import com.ly.mvc_recovery_evaluation.dao.*;
import com.ly.mvc_recovery_evaluation.dto.PageResult;
import com.ly.mvc_recovery_evaluation.entity.*;
import com.ly.mvc_recovery_evaluation.enums.ProjectType;
import com.ly.mvc_recovery_evaluation.service.*;
import com.ly.mvc_recovery_evaluation.vo.DetectProcedureVO;
import com.ly.mvc_recovery_evaluation.vo.SearchProcedureVO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author liuyue
 * @date 2022/6/14 17:22
 */
@Service
public class DetectProcedureServiceImpl implements DetectProcedureService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private DetectProcedureDao detectProcedureDao;

    @Autowired
    private MvcRecovery mvcRecovery;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleDependencyService moduleDependencyService;

    @Autowired
    private MethodService methodService;

    @Autowired
    private MethodCalledService methodCalledService;

    @Autowired
    private InjectionService injectionService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${project.repository.path}")
    private String repositoryPath;

    @Override
    public void startDetect(DetectProcedureVO detectProcedureVO) {
        DetectProcedure detectProcedure = new DetectProcedure();

        detectProcedure.setName(detectProcedureVO.getName());
        detectProcedure.setDescription(detectProcedureVO.getDescription());
        detectProcedure.setBeginTime(new Date());
        detectProcedure.setStatus("?????????");
        if (detectProcedureVO.getFiles() != null && detectProcedureVO.getFiles().length > 0){
            detectProcedure.setFileName(detectProcedureVO.getFiles()[0].getOriginalFilename().split("/")[0]);
        }

        // ??????????????????
        detectProcedure = save(detectProcedure);

        // ????????????
        String projectName = detectProcedure.getId() + "_" + detectProcedure.getName();
        fileUploadService.uploadMultiFile(projectName, detectProcedureVO.getFiles());

        File file = new File(repositoryPath, projectName);

        // ??????????????????
        ProjectNode projectNode = mvcRecovery.recover(file);

        // ????????????????????????
        ProjectNodePO projectNodePO = saveProjectNodePO(detectProcedure.getId(), projectNode);
        Long projectNodeId = projectNodePO.getId();

        // ???????????????????????????
        List<DataBaseDescription> dataBaseDescriptionList = projectNode.getDataBaseDescriptionList();
        saveDatabaseInfo(projectNodeId, dataBaseDescriptionList);

        List<ModuleNode> moduleNodeList = projectNode.getModuleNodeList();
        if (moduleNodeList != null && moduleNodeList.size() > 0){
            // ????????????????????????
            for (ModuleNode moduleNode : moduleNodeList) {

                ModuleNodePO moduleNodePO = getModuleNodePO(projectNodeId, moduleNode);
                Long moduleNodeId = moduleService.addModuleNode(moduleNodePO);

                // ????????????????????????
                List<ModuleCoordinate> moduleDependencies = moduleNode.getModuleDependencies();
                saveModuleDependency(moduleDependencies, moduleNodeId);

                // ?????????????????????
                List<EntryNode> entryNodes = moduleNode.getEntryNodes();
                saveEntryNode(moduleNodeId, entryNodes);

                // ?????????????????????
                List<ClassDescription> classDescriptions = moduleNode.getClassDescriptions();
                if (classDescriptions != null && classDescriptions.size() > 0){
                    for (ClassDescription classDescription : classDescriptions) {

                        ClassDescriptionPO classDescriptionPO = getClassDescriptionPO(moduleNodeId, classDescription);
                        Long classDescriptionId = classService.add(classDescriptionPO);

                        // ????????????????????????
                        List<InjectionInfo> injectionInfos = classDescription.getInjectionInfos();
                        saveInjectionInfo(classDescriptionId, injectionInfos);

                        // ??????????????????
                        List<MethodDescription> methodDescriptions = classDescription.getMethodDescriptionList();
                        if (methodDescriptions  != null && methodDescriptions.size() > 0){
                            for (MethodDescription methodDescription : methodDescriptions) {

                                MethodDescriptionPO methodDescriptionPO = getMethodDescriptionPO(classDescriptionId, methodDescription);
                                Long methodDescriptionId = methodService.add(methodDescriptionPO);

                                // ????????????????????????
                                List<MethodCalledNode> methodCalledNodeList = methodDescription.getMethodCalledNodeList();
                                saveMethodCalledInfo(methodDescriptionId, methodCalledNodeList);
                            }
                        }
                    }
                }
            }
        }

        detectProcedure.setStatus("????????????");
        detectProcedure.setEndTime(new Date());
        save(detectProcedure);

    }

    private void saveDatabaseInfo(Long projectNodeId, List<DataBaseDescription> dataBaseDescriptionList) {
        if (dataBaseDescriptionList != null && dataBaseDescriptionList.size() > 0){
            for (DataBaseDescription dataBaseDescription : dataBaseDescriptionList) {
                DatabaseDescriptionPO databaseDescriptionPO = new DatabaseDescriptionPO();
                databaseDescriptionPO.setProjectNodeId(projectNodeId);
                databaseDescriptionPO.setDatabaseType(dataBaseDescription.getDataBaseType().toString());
                databaseDescriptionPO.setUrl(dataBaseDescription.getUrl());
                databaseDescriptionPO.setPort(dataBaseDescription.getPort());
                databaseDescriptionPO.setDatabaseName(dataBaseDescription.getDataBaseName());

                databaseService.add(databaseDescriptionPO);
            }
        }
    }

    /**
     * ????????????????????????
     * @param methodDescriptionId
     * @param methodCalledNodeList
     */
    private void saveMethodCalledInfo(Long methodDescriptionId, List<MethodCalledNode> methodCalledNodeList) {
        if (methodCalledNodeList != null && methodCalledNodeList.size() > 0){
            for (MethodCalledNode methodCalledNode : methodCalledNodeList) {
                MethodCalledNodePO methodCalledNodePO = new MethodCalledNodePO();
                methodCalledNodePO.setMethodDescriptionId(methodDescriptionId);
                methodCalledNodePO.setCalledClassVarName(methodCalledNode.getCalledClassVarName());
                methodCalledNodePO.setCalledClassFullyName(methodCalledNode.getCalledClassFullyName());
                methodCalledNodePO.setCalledMthodName(methodCalledNode.getCalledMethodName());
                methodCalledNodePO.setMethodCalledType(methodCalledNode.getMethodCalledType().toString());

                methodCalledService.add(methodCalledNodePO);
            }
        }
    }

    /**
     * ??????MethodDescriptionPO
     * @param classDescriptionId
     * @param methodDescription
     * @return
     */
    private MethodDescriptionPO getMethodDescriptionPO(Long classDescriptionId, MethodDescription methodDescription) {
        MethodDescriptionPO methodDescriptionPO = new MethodDescriptionPO();
        methodDescriptionPO.setClassDescriptionId(classDescriptionId);
        methodDescriptionPO.setName(methodDescription.getMethodDeclaration().getNameAsString());
        if (methodDescription.getGranularityDescription() != null){
            methodDescriptionPO.setTotalLine(methodDescription.getGranularityDescription().getTotalLine());
            methodDescriptionPO.setBlankLine(methodDescription.getGranularityDescription().getBlankLine());
            methodDescriptionPO.setCommentLine(methodDescription.getGranularityDescription().getCommentLine());
            methodDescriptionPO.setCodeLine(methodDescription.getGranularityDescription().getCodeLine());
        }

        methodDescriptionPO.setCyclomaticComplexity(methodDescription.getCyclomaticComplexity());
        return methodDescriptionPO;
    }

    /**
     * ????????????????????????
     * @param classDescriptionId
     * @param injectionInfos
     */
    private void saveInjectionInfo(Long classDescriptionId, List<InjectionInfo> injectionInfos) {
        if (injectionInfos != null && injectionInfos.size() > 0){
            for (InjectionInfo injectionInfo : injectionInfos) {
                InjectionInfoPO injectionInfoPO = new InjectionInfoPO();
                injectionInfoPO.setClassDescriptionId(classDescriptionId);
                injectionInfoPO.setVarName(injectionInfo.getVarName());
                injectionInfoPO.setFullyQualifiedName(injectionInfo.getFullyQualifiedName());

                injectionService.add(injectionInfoPO);
            }
        }
    }

    /**
     * ??????ClassDescriptionPO
     * @param moduleNodeId
     * @param classDescription
     * @return
     */
    private ClassDescriptionPO getClassDescriptionPO(Long moduleNodeId, ClassDescription classDescription) {
        ClassDescriptionPO classDescriptionPO = new ClassDescriptionPO();
        classDescriptionPO.setModuleNodeId(moduleNodeId);
        classDescriptionPO.setName(classDescription.getName());
        classDescriptionPO.setFilePath(classDescription.getFile().getAbsolutePath());
        if (classDescription.getClassType() != null){
            classDescriptionPO.setClassType(classDescription.getClassType().toString());
        }
        classDescription.setFullyQualifiedName(classDescription.getFullyQualifiedName());

        StringBuilder annotationStr = new StringBuilder();
        List<String> annotations = classDescription.getAnnotations();
        if (annotations != null && annotations.size() > 0){
            for (String annotation : annotations) {
                annotationStr.append(annotation);
                annotationStr.append(";");
            }
        }
        classDescriptionPO.setAnnotations(annotationStr.toString());
        return classDescriptionPO;
    }

    /**
     * ?????????????????????
     * @param moduleNodeId
     * @param entryNodes
     */
    private void saveEntryNode(Long moduleNodeId, List<EntryNode> entryNodes) {
        if (entryNodes != null && entryNodes.size() > 0){
            for (EntryNode entryNode : entryNodes) {
                EntryNodePO entryNodePO = new EntryNodePO();
                entryNodePO.setModuleNodeId(moduleNodeId);
                entryNodePO.setFilePath(entryNode.getFile().getAbsolutePath());
                entryNodePO.setContent(entryNode.getContent());
                StringBuilder annotationStr = new StringBuilder();
                List<String> annotations = entryNode.getAnnotations();
                if (annotations != null && annotations.size() > 0){
                    for (String annotation : annotations) {
                        annotationStr.append(annotation);
                        annotationStr.append(";");
                    }
                }
                entryNodePO.setAnnotations(annotationStr.toString());

                entryService.add(entryNodePO);
            }
        }
    }

    /**
     * ????????????????????????
     * @param moduleDependencies
     * @param moduleNodeId
     */
    private void saveModuleDependency(List<ModuleCoordinate> moduleDependencies, Long moduleNodeId) {

        if (moduleDependencies != null && moduleDependencies.size() > 0){
            for (ModuleCoordinate moduleCoordinate : moduleDependencies) {
                ModuleDependencyPO moduleDependencyPO = new ModuleDependencyPO();
                moduleDependencyPO.setModuleNodeId(moduleNodeId);
                moduleDependencyPO.setGroupId(moduleCoordinate.getGroupId());
                moduleDependencyPO.setArtifactId(moduleCoordinate.getArtifactId());

                moduleDependencyService.add(moduleDependencyPO);
            }
        }
    }

    /**
     * ??????ModuleNodePO
     * @param projectNodeId
     * @param moduleNode
     * @return
     */
    private ModuleNodePO getModuleNodePO(Long projectNodeId, ModuleNode moduleNode) {
        ModuleNodePO moduleNodePO = new ModuleNodePO();

        moduleNodePO.setProjectNodeId(projectNodeId);
        moduleNodePO.setModuleName(moduleNode.getModuleName());
        moduleNodePO.setModuleFilePath(moduleNode.getModuleFile().getAbsolutePath());
        moduleNodePO.setModuleType(moduleNode.getModuleType().toString());
        moduleNodePO.setGroupId(moduleNode.getModuleCoordinate().getGroupId());
        moduleNodePO.setArtifactId(moduleNode.getModuleCoordinate().getArtifactId());
        return moduleNodePO;
    }

    /**
     * ????????????????????????
     * @param detectProcedureId
     * @param projectNode
     * @return
     */
    private ProjectNodePO saveProjectNodePO(Long detectProcedureId, ProjectNode projectNode) {
        ProjectNodePO projectNodePO = new ProjectNodePO();
        projectNodePO.setProcedureId(detectProcedureId);
        projectNodePO.setProjectName(projectNode.getProjectName());
        projectNodePO.setProjectFilePath(projectNode.getProjectFile().getAbsolutePath());

        if(projectNode.getProjectType() == null){
            projectNodePO.setProjectType(ProjectType.MVC.toString());
        }else {
            if (projectNode.getProjectType().equals(ProjectType.MVC)){
                projectNodePO.setProjectType(ProjectType.MVC.toString());
            }else {
                projectNodePO.setProjectType(ProjectType.OTHERS.toString());
            }
        }

        projectNodePO.setPort(projectNode.getApplicationConfig().getPort());
        projectNodePO.setContextPath(projectNode.getApplicationConfig().getContextPath());
        projectNodePO.setActiveFile(projectNode.getApplicationConfig().getActiveFile());

        projectNodePO = projectService.add(projectNodePO);
        return projectNodePO;
    }

    @Override
    public DetectProcedure save(DetectProcedure detectProcedure) {

        if (detectProcedure.getId() == null){
            return detectProcedureDao.save(detectProcedure);
        }else {
            Long id = detectProcedure.getId();
            Optional<DetectProcedure> byId = detectProcedureDao.findById(id);
            if (byId.isPresent()){
                DetectProcedure saved = byId.get();
                if (detectProcedure.getEndTime() != null){
                    saved.setEndTime(detectProcedure.getEndTime());
                }

                if (detectProcedure.getProjectSize() != null){
                    saved.setProjectSize(detectProcedure.getProjectSize());
                }

                if (detectProcedure.getLog() != null){
                    saved.setLog(detectProcedure.getLog());
                }

                return detectProcedureDao.save(saved);
            }
            return detectProcedureDao.save(detectProcedure);
        }

    }

    @Override
    public PageResult<DetectProcedure> listProcedure(SearchProcedureVO searchProcedureVO) {
        PageRequest pageRequest = PageRequest.of(searchProcedureVO.getPageNo(), searchProcedureVO.getPageSize());
        QueryResults<DetectProcedure> detectPage = findDetectPage(pageRequest, searchProcedureVO);

        return convertQueryResults2PageResult(detectPage, searchProcedureVO.getPageSize(), searchProcedureVO.getPageNo());
    }


    private <T> PageResult<T> convertQueryResults2PageResult(QueryResults<T> page, int pageSize, int pageNo){
        long total = page.getTotal();
        long totalPages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalElements(total);
        pageResult.setTotalPages(totalPages);
        pageResult.setData(page.getResults());
        return pageResult;
    }

    /**
     * ????????????
     * @param pageable
     * @param searchProcedureVO
     * @return
     */
    QueryResults<DetectProcedure> findDetectPage(Pageable pageable, SearchProcedureVO searchProcedureVO){
        QDetectProcedure qDetectProcedure = QDetectProcedure.detectProcedure;

        JPAQuery<DetectProcedure> jpaQuery = jpaQueryFactory
                .select(Projections.fields(
                        DetectProcedure.class,
                        qDetectProcedure.id,
                        qDetectProcedure.name,
                        qDetectProcedure.description,
                        qDetectProcedure.status,
                        qDetectProcedure.beginTime,
                        qDetectProcedure.endTime,
                        qDetectProcedure.fileName,
                        qDetectProcedure.projectSize,
                        qDetectProcedure.log
                        )
                ).from(qDetectProcedure);

        if (!StringUtils.isEmpty(searchProcedureVO.getKeyword())){
            jpaQuery.where(qDetectProcedure.name.like("%" + searchProcedureVO.getKeyword() + "%"));
        }

        // ??????
        if (!StringUtils.isEmpty(searchProcedureVO.getDir()) && searchProcedureVO.getDir().equalsIgnoreCase("asc")){
            jpaQuery.orderBy(qDetectProcedure.beginTime.asc());
        }else {
            jpaQuery.orderBy(qDetectProcedure.beginTime.desc());
        }

        jpaQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        return jpaQuery.fetchResults();
    }
}
