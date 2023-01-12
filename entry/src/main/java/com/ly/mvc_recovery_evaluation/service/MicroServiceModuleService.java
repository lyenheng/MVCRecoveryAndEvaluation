package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.DatabaseDescriptionPO;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;
import com.ly.mvc_recovery_evaluation.vo.DependencyNode;
import com.ly.mvc_recovery_evaluation.vo.LayersRelationVO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/11/19 15:51
 */
public interface MicroServiceModuleService {

    List<ModuleNodePO> findMicroServiceModuleByDetectId(Long detectId);

    List<Long> findModulesByEntryModule(Long entryModuleId);

    /**
     * 构造子服务模块依赖树
     * @param moduleId
     * @return
     */
    DependencyNode getModuleDependencyTree(Long moduleId);

    /**
     * 获取数据库信息
     * @param moduleId
     * @return
     */
    List<DatabaseDescriptionPO> getDatabaseInfo(Long moduleId);

    /**
     * 构造层间调用关系图
     * @param entryModuleId
     * @return
     */
    LayersRelationVO getLayersRelationData(Long entryModuleId);

    LayersRelationVO getLayersRelationDataNew(Long entryModuleId);
}
