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

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxSink;
import sample.multimodule.dto.avro.AuditlogDTO;


@Configuration
public class AuditStreamConfig {

    @Bean("auditlogProcessor")
    public DirectProcessor<AuditlogDTO> auditlogProcessor(){
        return DirectProcessor.create();
    }

    @Bean("auditlogPublisher")
    public FluxSink<AuditlogDTO> auditlogPublisher(DirectProcessor<AuditlogDTO> publisher){
        return publisher.sink();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }


}