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
import sample.multimodule.dto.request.UserRequestDTO;
import sample.multimodule.exceptions.AppException;
import sample.multimodule.helpers.ServiceHelper;
import sample.multimodule.service.UserService;

import java.util.List;

/**
 *
 * @author brijeshdhaker
 */
@CrossOrigin(origins = {"*localhost*"})
@RestController
@RequestMapping(path ="api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    UserService userService;
    
    @Qualifier("auditlogPublisher")
    @Autowired
    private FluxSink<AuditlogDTO> auditLogSink;
    
    @Qualifier("userPublisher")
    @Autowired
    private FluxSink<UserDTO> userSink;

    @GetMapping()
    public ResponseEntity<List<UserRequestDTO>> listUsers() {
        ResponseEntity<List<UserRequestDTO>> response = null;
        try {
            List<UserRequestDTO> dtos = userService.getUsers();
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
    public ResponseEntity<UserRequestDTO> getUser(@PathVariable String id) {
        ResponseEntity<UserRequestDTO> response = null;
        try {
            UserRequestDTO dto =  userService.findOne(Long.parseLong(id));
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
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserRequestDTO input) {
        ResponseEntity<AppRestResponse> httpResponse;
        AppRestResponse appResponse;
        try {
            UserRequestDTO _user = userService.findOne(id);
            if (_user != null) {
                BeanUtils.copyProperties(input, _user);
                _user.setId(id);
                this.userSink.next(ServiceHelper.toUserDTO(_user));
                appResponse  = new AppRestResponse(String.format("User with id %d successfully updated.",_user.getId()));
                httpResponse =  new ResponseEntity<>(appResponse, HttpStatus.OK);
            } else {
                appResponse  = new AppRestResponse(String.format("User with id %d not found in system.",id));
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
    public ResponseEntity<?> saveUser(@RequestBody UserRequestDTO user) {
        ResponseEntity<AppRestResponse> httpResponse;
        AppRestResponse appResponse;
        try {
            this.userSink.next(ServiceHelper.toUserDTO(user));
            appResponse  = new AppRestResponse("User successfully added in system.");
            httpResponse =  new ResponseEntity<>(appResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            appResponse  = new AppRestResponse(ex.getMessage());
            httpResponse = new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return httpResponse;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        ResponseEntity<?> httpResponse;
        AppRestResponse appResponse;
        try {
            UserRequestDTO _user = userService.findOne(id);
            if (_user != null) {
                Boolean result = userService.deleteUserById(id);
                this.auditLogSink.next(ServiceHelper.toAuditlog(Long.toString(_user.getId()),"DELETE", String.format("User with id %d successfully deleted.",_user.getId())));
                appResponse  = new AppRestResponse(String.format("User with id %d successfully deleted.",_user.getId()));
                httpResponse =  new ResponseEntity<>(appResponse, HttpStatus.OK);
            } else {
                appResponse  = new AppRestResponse(String.format("User with id %d not found in system.",id));
                httpResponse =  new ResponseEntity<>(appResponse,HttpStatus.NOT_FOUND);
            }
        } catch (AppException ex) {
            log.error(ex.getMessage(),ex);
            appResponse  = new AppRestResponse(ex.getMessage());
            httpResponse = new ResponseEntity<>(appResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  httpResponse;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {

    }
    
}
