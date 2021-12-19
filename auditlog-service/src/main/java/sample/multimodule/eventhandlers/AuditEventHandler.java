/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import sample.multimodule.dto.avro.AuditlogDTO;
import sample.multimodule.dto.request.AuditlogRequestDTO;
import sample.multimodule.service.AuditlogService;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class AuditEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditEventHandler.class);

    @Autowired
    private DirectProcessor<AuditlogDTO> source;

    @Autowired
    private AuditlogService auditlogService;

    @Bean
    public Consumer<Flux<AuditlogDTO>> consumer(){
        return (flux) -> flux.subscribe(s_auditlog -> {
            try {

                System.out.println("Audit log received .... " + s_auditlog.getUuid());
                AuditlogRequestDTO r_auditlog = new AuditlogRequestDTO();

                String[] ignoreProperties = {"id","uuid"};
                BeanUtils.copyProperties(s_auditlog,r_auditlog,ignoreProperties);

                if(s_auditlog.getId() != null){
                    r_auditlog.setId(s_auditlog.getId());
                }

                if(s_auditlog.getUuid() != null){
                    r_auditlog.setUuid(s_auditlog.getUuid());
                }else{
                    r_auditlog.setUuid(UUID.randomUUID().toString());
                }

                System.out.println("Audit log successfully saved ... " + auditlogService.saveAuditLog(r_auditlog).getId());

            } catch(Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        });
    }

    @Bean
    public Supplier<Flux<AuditlogDTO>> supplier(){
        return () -> Flux.from(source);
    };

    /*
    @Autowired
    private Processor processor;

    @Bean
    public Consumer<AuditlogDTO> consumer(){
        return s_dto -> {
            try {

                System.out.println("Audit log received .... " + s_dto.getUuid());
                AuditlogRequestDTO r_dto = new AuditlogRequestDTO();
                BeanUtils.copyProperties(s_dto,r_dto);
                AuditlogRequestDTO p_r_dto = this.service.saveAuditLog(r_dto);
                System.out.println("Audit log successfully processed .... " + p_r_dto.getId());

            } catch(Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        };
    }

    public void produceAuditlogEvent(AuditlogRequestDTO rdto) {

        // creating employee details
        AuditlogDTO sdto = new AuditlogDTO();
        BeanUtils.copyProperties(rdto,sdto);

        // creating partition key for kafka topic
        //EmployeeKey employeeKey = new EmployeeKey();
        //employeeKey.setId(empId);
        //employeeKey.setDepartmentName("IT");

        Message<AuditlogDTO> message = MessageBuilder.withPayload(sdto)
                //.setHeader(KafkaHeaders.MESSAGE_KEY, employeeKey)
                .build();
        processor.output().send(message);

    }

    @StreamListener(Processor.INPUT)
    public void consumeEmployeeDetails(AuditlogDTO auditlog) {
        try {
            System.out.println("Audit log received .... " + auditlog.getUuid());
            LOGGER.info("Let's process Auditlog details: {}", auditlog);
            //this.service.saveAuditLog(auditlog);
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    */
}