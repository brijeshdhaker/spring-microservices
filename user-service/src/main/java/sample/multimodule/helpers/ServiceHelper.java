/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.helpers;

import org.springframework.beans.BeanUtils;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.avro.UserDTO;
import sample.multimodule.dto.request.UserRequestDTO;
import sample.multimodule.enums.AuditType;

import java.util.UUID;

/**
 *
 * @author brijeshdhaker
 */
public class ServiceHelper {
    
    public static AuditlogDTO toAuditlog(String refrenceId,String action,String description) {

        AuditlogDTO dto = new AuditlogDTO();
        
        dto.setUuid(UUID.randomUUID().toString());
        dto.setUserid("System");
        dto.setAuditType(AuditType.SYSTEM.getName());
        dto.setLogAction(action);
        dto.setLogMessage(description);
        dto.setRefrenceId(refrenceId);
        dto.setRefrenceType("USER");
        dto.setAddTs(System.currentTimeMillis());
        dto.setUpdTs(System.currentTimeMillis());
        
        return dto;
        
    }
    
    public static UserDTO toUserDTO(UserRequestDTO rdto) {
        
        UserDTO sdto = new UserDTO();
        String[] ignoreProperties = {"id","uuid"};
        BeanUtils.copyProperties(rdto,sdto,ignoreProperties);
        
        if(rdto.getId() != null){
            sdto.setId(rdto.getId());
        }
        
        if(rdto.getUuid() != null){
            sdto.setUuid(rdto.getUuid());
        }else{
            sdto.setUuid(UUID.randomUUID().toString());
        }

        return  sdto;
    }
    
    
    public static UserRequestDTO toRequestUserDTO(UserDTO s_user) {
        
        UserRequestDTO r_user = new UserRequestDTO();
        String[] ignoreProperties = {"id"};
        BeanUtils.copyProperties(s_user,r_user,ignoreProperties);
        
        if(s_user.getId() != null && s_user.getId() != 0){
            r_user.setId(s_user.getId());
        }
        
        return  r_user;
    }
}
