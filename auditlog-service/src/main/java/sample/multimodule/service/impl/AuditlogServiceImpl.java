/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.multimodule.domain.AuditlogEntity;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.request.AuditlogRequestDTO;
import sample.multimodule.enums.AuditType;
import sample.multimodule.exceptions.AppException;
import sample.multimodule.repository.AuditlogRepository;
import sample.multimodule.service.AuditlogService;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author brijeshdhaker
 */
@Service("auditlogService")
public class AuditlogServiceImpl implements AuditlogService {

    @Autowired
    AuditlogRepository auditlogRepository;

    @Override
    public List<AuditlogRequestDTO> getAuditlogs() throws AppException {
        List<AuditlogRequestDTO> dtos = new ArrayList<>();
        Iterable<AuditlogEntity> auditlogs = auditlogRepository.findAll();
        for (AuditlogEntity log : auditlogs) {
            dtos.add(toRequestDTO(log));
        }
        return dtos;
    }

    @Override
    public AuditlogRequestDTO getAuditlog(long id) throws AppException {
        AuditlogRequestDTO dto = null;
        //AuditLog auditlog = auditlogRepository.findById(id).orElse(null);
        Optional<AuditlogEntity> auditlog = auditlogRepository.findById(id);
        if(auditlog.isPresent()){
            dto = toRequestDTO(auditlog.get());
        }
        return dto;
    }

    @Override
    public void deleteAuditLog(long logid) throws AppException {
        auditlogRepository.deleteById(logid);
    }

    @Override
    public AuditlogRequestDTO saveAuditLog(AuditlogRequestDTO r_dto) throws AppException {
        AuditlogEntity entity = auditlogRepository.save(toEntity(r_dto));
        return toRequestDTO(entity);
    }

    public AuditlogEntity toEntity(AuditlogRequestDTO dto){

        AuditlogEntity entity = new AuditlogEntity();
        entity.setId(dto.getId());
        entity.setDescription(dto.getLogMessage());
        entity.setLogAction(dto.getLogAction());
        entity.setAuditType(AuditType.getByName(dto.getAuditType()));
        entity.setReferenceType(dto.getRefrenceType());
        entity.setRefrenceId(dto.getRefrenceId());
        entity.setUserid(dto.getUserid());

        return  entity;

    }

    public AuditlogRequestDTO toRequestDTO(AuditlogEntity entity) {

        AuditlogRequestDTO dto = new AuditlogRequestDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }

        if (entity.getAuditType() != null) {
            AuditType at = entity.getAuditType();
            dto.setAuditType(at.getName());
        }

        if (entity.getLogAction() != null) {
            dto.setLogAction(entity.getLogAction());
        }

        if (entity.getDescription() != null) {
            dto.setLogMessage(entity.getDescription());
        }
        if (entity.getRefrenceId() != null) {
            dto.setRefrenceId(entity.getRefrenceId());
        }
        if (entity.getReferenceType() != null) {
            dto.setRefrenceType(entity.getReferenceType());
        }
        if (entity.getUserid() != null) {
            dto.setUserid(entity.getUserid());
        }

        if (entity.getAddTs() != null) {
            dto.setAddTs(entity.getAddTs().toEpochSecond(ZoneOffset.UTC));
        }

        if (entity.getUpdTs() != null) {
            dto.setUpdTs(entity.getUpdTs().toEpochSecond(ZoneOffset.UTC));
        }

        return dto;
    }

    public AuditlogDTO toDTO(AuditlogEntity entity) {

        AuditlogDTO dto = new AuditlogDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }

        if (entity.getAuditType() != null) {
            AuditType at = entity.getAuditType();
            dto.setAuditType(at.getName());
        }

        if (entity.getLogAction() != null) {
            dto.setLogAction(entity.getLogAction());
        }

        if (entity.getDescription() != null) {
            dto.setLogMessage(entity.getDescription());
        }
        if (entity.getRefrenceId() != null) {
            dto.setRefrenceId(entity.getRefrenceId());
        }
        if (entity.getReferenceType() != null) {
            dto.setRefrenceType(entity.getReferenceType());
        }
        if (entity.getUserid() != null) {
            dto.setUserid(entity.getUserid());
        }

        if (entity.getAddTs() != null) {
            dto.setAddTs(entity.getAddTs().toEpochSecond(ZoneOffset.UTC));
        }

        if (entity.getUpdTs() != null) {
            dto.setUpdTs(entity.getUpdTs().toEpochSecond(ZoneOffset.UTC));
        }

        return dto;
    }
}
