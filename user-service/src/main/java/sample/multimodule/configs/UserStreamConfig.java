/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.configs;

/**
 *
 * @author brijeshdhaker
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxSink;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.avro.UserDTO;

@Configuration
public class UserStreamConfig {
    
    @Bean("userProcessor")
    public DirectProcessor<UserDTO> userProcessor(){
        return DirectProcessor.create();
    }

    @Bean("auditlogProcessor")
    public DirectProcessor<AuditlogDTO> auditlogPublisher(){
        return DirectProcessor.create();
    }
    
    @Bean("userPublisher")
    public FluxSink<UserDTO> userSink(DirectProcessor<UserDTO> publisher){
        return publisher.sink();
    }
    
    @Bean("auditlogPublisher")
    public FluxSink<AuditlogDTO> auditLogSink(DirectProcessor<AuditlogDTO> publisher){
        return publisher.sink();
    }
}