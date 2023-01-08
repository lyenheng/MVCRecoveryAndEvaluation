package com.ly.mvc_recovery_evaluation.dao;

import com.ly.mvc_recovery_evaluation.entity.ClassDescriptionPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 20:36
 */
@Repository
public interface ClassDescriptionDao extends JpaRepository<ClassDescriptionPO, Long> {

    List<ClassDescriptionPO> findAllByModuleNodeIdEquals(Long moduleId);

    List<ClassDescriptionPO> findAllByModuleNodeIdIn(List<Long> moduleIds);

    List<ClassDescriptionPO> findClassDescriptionPOSByFullyQualifiedNameEndsWithIgnoreCase(String fullyQualifiedName);
}
