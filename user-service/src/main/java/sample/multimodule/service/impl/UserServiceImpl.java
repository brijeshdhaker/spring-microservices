/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.FluxSink;
import sample.multimodule.domain.RoleEntity;
import sample.multimodule.domain.UserEntity;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.request.UserRequestDTO;
import sample.multimodule.enums.RoleTypes;
import sample.multimodule.exceptions.AppException;
import sample.multimodule.helpers.ServiceHelper;
import sample.multimodule.repository.UserRepository;
import sample.multimodule.service.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brijeshdhaker
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    CacheManager cacheManager;

    @Qualifier("auditlogPublisher")
    @Autowired
    private FluxSink<AuditlogDTO> auditLogSink;

    /*
    // which means cache unless result == null which means Cache if result != null.
    @Cacheable(value="userCache", key="#userid", condition="#userid <= 3", unless="#result == null")
    @Cacheable(value="userCache", key="#userid", condition="#userid <= 3", unless="#result.status ne 'ACTIVE'")
    */
    @Cacheable(value="userCache", key="#userid")
    public UserRequestDTO findOne(Long userid) {
        UserEntity entity = userRepository.findById(userid).orElse(null);
        return toRequestDTO(entity);
    }
    
    @Transactional
    @CacheEvict(value = "userCache", key = "#userDto.id", condition="#userDto.id != null")
    public UserRequestDTO saveUser(UserRequestDTO userDto){
        UserEntity entity = userRepository.save(toEntity(userDto));
        UserRequestDTO _user = toRequestDTO(entity);
        if(userDto.getId() == null || userDto.getId().equals(0)){
            this.auditLogSink.next(ServiceHelper.toAuditlog(Long.toString(_user.getId()),"SAVE", String.format("User with id %d successfully added.",_user.getId())));
            log.info("User with id {} successfully added.",_user.getId());
        }else{
            this.auditLogSink.next(ServiceHelper.toAuditlog(Long.toString(_user.getId()),"UPDATE", String.format("User with id %d successfully updated.",_user.getId())));
            log.info("User with id {} successfully updated.",_user.getId());
        }
        return _user;
    }

    
    @Override
    public List<UserRequestDTO> getUsers() throws AppException {
        List<UserRequestDTO> dtos = new ArrayList<>();
        Iterable<UserEntity> users = userRepository.findAll();
        for(UserEntity user : users){
            dtos.add(toRequestDTO(user));
        }
        return dtos;
    }

    @Override
    public Boolean deleteUserById(Long id) {
        Boolean result = Boolean.TRUE;
        UserRequestDTO r_dto = findOne(id);
        if (r_dto != null) {
            userRepository.deleteById(id);
        } else {
            result = Boolean.FALSE;
            log.info("User with {} not exist",id);
        }
        return result;
    }
    
    private UserEntity toEntity(UserRequestDTO dto){
        UserEntity entity = null;
        if(dto!= null){
            entity = new UserEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setEmail(dto.getEmailAddr());
            entity.setStatus(dto.getStatus());
            for(String role : dto.getRoles()){
                RoleTypes R = RoleTypes.getByName(role);
                entity.addRole(new RoleEntity(R.getId(),R.getName(),R.getStatus()));
            }
        }
        return  entity;
    }
    
    public UserRequestDTO toRequestDTO(UserEntity entity) {
        UserRequestDTO dto = null;
        if(entity != null){
            dto = new UserRequestDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setEmailAddr(entity.getEmail());
            dto.setStatus(entity.getStatus());
            for(RoleEntity role : entity.getRoles()){
                dto.addRoles(role.getName());
            }
        }
        return  dto;
    }

}
