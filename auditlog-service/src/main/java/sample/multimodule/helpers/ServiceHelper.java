/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.helpers;

import org.springframework.beans.BeanUtils;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.request.AuditlogRequestDTO;

import java.util.UUID;

/**
 *
 * @author brijeshdhaker
 */
public class ServiceHelper {

    public static AuditlogDTO toAuditlogDTO(AuditlogRequestDTO rdto) {
        AuditlogDTO s_dto = new AuditlogDTO();
        String[] ignoreProperties = {"id","uuid"};
        BeanUtils.copyProperties(rdto,s_dto,ignoreProperties);

        if(rdto.getId() != null){
            s_dto.setId(rdto.getId());
        }

        if(rdto.getUuid() != null){
            s_dto.setUuid(rdto.getUuid());
        }else{
            s_dto.setUuid(UUID.randomUUID().toString());
        }

        return s_dto;
    }

}
