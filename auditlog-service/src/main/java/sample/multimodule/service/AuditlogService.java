/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.service;

import sample.multimodule.dto.request.AuditlogRequestDTO;
import sample.multimodule.exceptions.AppException;

import java.util.List;



/**
 *
 * @author brijeshdhaker
 */
public interface AuditlogService {
    
    public AuditlogRequestDTO getAuditlog(long id) throws AppException;
    
    public List<AuditlogRequestDTO> getAuditlogs() throws AppException;

    public AuditlogRequestDTO saveAuditLog(AuditlogRequestDTO log) throws AppException;

    public void deleteAuditLog(long log) throws AppException;
    
}
