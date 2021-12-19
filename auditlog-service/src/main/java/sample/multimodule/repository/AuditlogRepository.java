/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sample.multimodule.domain.AuditlogEntity;

import java.util.List;
/**
 *
 * @author brijeshdhaker
 */
@Repository("auditlogRepository")
public interface AuditlogRepository extends PagingAndSortingRepository<AuditlogEntity, Long> {
    
    @Query("select al from AuditlogEntity al where al.auditTypeName=:type")
    List<AuditlogEntity> findByType(@Param("type") String type);
    
    @Query("select al from AuditlogEntity al where al.userid=:userid")
    List<AuditlogEntity> findByUser(@Param("userid") String userid);
    
}
