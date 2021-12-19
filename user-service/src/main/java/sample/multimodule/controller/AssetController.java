/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.FluxSink;
import sample.multimodule.dto.AppRestResponse;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.avro.UserDTO;
import sample.multimodule.dto.request.AssetRequestDTO;
import sample.multimodule.exceptions.AppException;
import sample.multimodule.helpers.ServiceHelper;
import sample.multimodule.service.AssetService;
import sample.multimodule.service.UserService;

import java.util.List;

/**
 *
 * @author brijeshdhaker
 */
@CrossOrigin(origins = {"*localhost*"})
@RestController
@RequestMapping(path ="api/asset", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssetController {
    
    private static final Logger log = LoggerFactory.getLogger(AssetController.class);
    
    @Autowired
    AssetService assetService;
    
    @Qualifier("auditlogPublisher")
    @Autowired
    private FluxSink<AuditlogDTO> auditLogSink;

    @GetMapping()
    public ResponseEntity<List<AssetRequestDTO>> listAssets() {
        ResponseEntity<List<AssetRequestDTO>> response = null;
        try {
            List<AssetRequestDTO> dtos = assetService.listAssets();
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
    
    @GetMapping("/{id}")
    public ResponseEntity<AssetRequestDTO> getAsset(@PathVariable String id) {
        ResponseEntity<AssetRequestDTO> response = null;
        try {
            AssetRequestDTO dto =  assetService.getAsset(Long.parseLong(id));
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
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable long id, @RequestBody AssetRequestDTO input) {
        ResponseEntity<AppRestResponse> httpResponse;
        AppRestResponse appResponse;
        try {
            AssetRequestDTO _asset = assetService.getAsset(id);
            if (_asset != null) {
                BeanUtils.copyProperties(input, _asset);
                _asset.setAssetId(id);
                appResponse  = new AppRestResponse(String.format("Asset with id %d successfully updated.",_asset.getAssetId()));
                httpResponse =  new ResponseEntity<>(appResponse, HttpStatus.OK);
            } else {
                appResponse  = new AppRestResponse(String.format("Asset with id %d not found in system.",id));
                httpResponse =  new ResponseEntity<>(appResponse,HttpStatus.NOT_FOUND);
            }
        } catch (AppException ex) {
            log.error(ex.getMessage(),ex);
            appResponse  = new AppRestResponse(ex.getMessage());
            httpResponse = new ResponseEntity<>(appResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  httpResponse;
    }
    
    @PostMapping(path="/add")
    public ResponseEntity<?> saveAsset(@RequestBody AssetRequestDTO assetRequestDTO) {
        ResponseEntity<AppRestResponse> httpResponse;
        AppRestResponse appResponse;
        try {

            assetService.saveAsset(assetRequestDTO);
            appResponse  = new AppRestResponse("Asset successfully added in system.");
            httpResponse =  new ResponseEntity<>(appResponse, HttpStatus.CREATED);

        } catch (Exception ex) {

            log.error(ex.getMessage(),ex);
            appResponse  = new AppRestResponse(ex.getMessage());
            httpResponse = new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return httpResponse;
    }
    

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {

    }
    
}
