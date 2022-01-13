/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.FluxSink;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.request.AuditlogRequestDTO;
import sample.multimodule.exceptions.AppException;
import sample.multimodule.helpers.ServiceHelper;
import sample.multimodule.service.AuditlogService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author brijeshdhaker
 */
@CrossOrigin(origins = {"*localhost*"})
@RestController
@RequestMapping(path ="api/audit", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuditlogController {
    
    private static final Logger log = LoggerFactory.getLogger(AuditlogController.class);
    
    @Autowired
    AuditlogService auditlogService;

    @Qualifier("auditlogPublisher")
    @Autowired
    private FluxSink<AuditlogDTO> sink;

    @GetMapping("/list")
    public ResponseEntity<List<AuditlogRequestDTO>> getAllAuditlogs(@RequestParam(required = false) String title) {
        ResponseEntity<List<AuditlogRequestDTO>> response = null;
        try {
            List<AuditlogRequestDTO> dtos = auditlogService.getAuditlogs();
            if(dtos != null && !dtos.isEmpty()){
                response = new ResponseEntity<>(dtos,HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (AppException ex) {
            log.error(ex.getMessage(),ex);
            response = new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  response;
    }
    
    @GetMapping("/{logid}")
    public ResponseEntity<AuditlogRequestDTO> getAuditlog(@PathVariable long logid) {
        ResponseEntity<AuditlogRequestDTO> response = null;
        try {
            AuditlogRequestDTO dto =  auditlogService.getAuditlog(logid);
            if(dto != null){
                response = new ResponseEntity<>(dto,HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (AppException ex) {
            log.error(ex.getMessage(),ex);
            response = new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  response;
    }
    
    @PostMapping
    public ResponseEntity<AuditlogRequestDTO> saveAuditlog(@RequestBody AuditlogRequestDTO r_dto) {
        ResponseEntity<AuditlogRequestDTO> httpResponse;
        try {
            r_dto.setUuid(UUID.randomUUID().toString());
            AuditlogDTO s_dto = ServiceHelper.toAuditlogDTO(r_dto);
            this.sink.next(s_dto);
            httpResponse =  new ResponseEntity<>(r_dto,HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            httpResponse = new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return httpResponse;
    }
    
    @PutMapping("/{logid}")
    public ResponseEntity<AuditlogRequestDTO> updateAuditlog(@PathVariable long logid, @RequestBody AuditlogRequestDTO input) {
        ResponseEntity<AuditlogRequestDTO> httpResponse;
        try {
            AuditlogRequestDTO logDto = auditlogService.getAuditlog(logid);
            if (logDto != null) {
                auditlogService.saveAuditLog(logDto);
                httpResponse =  new ResponseEntity<>(input, HttpStatus.OK);
            } else {
                httpResponse =  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (AppException ex) {
            log.error(ex.getMessage(),ex);
            httpResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  httpResponse;
    }
    
    @DeleteMapping("/{logid}")
    public ResponseEntity<?> delete(@PathVariable long logid) {
        try {
            auditlogService.deleteAuditLog(logid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AppException ex) {
            log.error(ex.getMessage(),ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public Object handleError(HttpServletRequest req, Exception ex) {
        Object errorObject = new Object();
        return errorObject;
    }

}
