/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.avro.UserDTO;
import sample.multimodule.dto.request.UserRequestDTO;
import sample.multimodule.helpers.ServiceHelper;
import sample.multimodule.service.UserService;

import java.util.function.Consumer;
import java.util.function.Supplier;


@Service
public class UserEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventHandler.class);
    
    @Qualifier("userProcessor")
    @Autowired
    private DirectProcessor<UserDTO> userProcessor;
    
    @Qualifier("auditlogProcessor")
    @Autowired
    private DirectProcessor<AuditlogDTO> auditlogProcessor;
    
    @Autowired
    private UserService userService;

    @Bean
    public Supplier<Flux<UserDTO>> u_supplier(){
        return () -> Flux.from(userProcessor);
    };

    @Bean
    public Supplier<Flux<AuditlogDTO>> l_supplier(){
        return () -> Flux.from(auditlogProcessor);
    };
    
    @Bean
    public Consumer<Flux<UserDTO>> u_consumer(){
        return (flux) -> flux.subscribe(s_user -> {
            try {
                
                System.out.println("User object received .... " + s_user.getUuid());
                UserRequestDTO r_user = ServiceHelper.toRequestUserDTO(s_user);
                UserRequestDTO _r_user = userService.saveUser(r_user);
                
            } catch(Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        });
    }
    
}