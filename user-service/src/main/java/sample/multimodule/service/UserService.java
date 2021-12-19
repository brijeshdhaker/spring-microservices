/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.service;

import sample.multimodule.dto.request.UserRequestDTO;
import sample.multimodule.exceptions.AppException;

import java.util.List;

/**
 *
 * @author brijeshdhaker
 */
public interface UserService {
    
    public List<UserRequestDTO> getUsers() throws AppException;
    
    public UserRequestDTO findOne(Long userid)throws AppException;
    
    UserRequestDTO saveUser(UserRequestDTO userDto) throws AppException;
    
    public Boolean deleteUserById(Long id)throws AppException;

}
