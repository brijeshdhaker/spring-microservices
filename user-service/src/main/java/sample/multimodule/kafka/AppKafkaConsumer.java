/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.kafka;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import sample.multimodule.dto.avro.UserDTO;

/**
 *
 * @author brijeshdhaker
 */
//@Service
@CommonsLog(topic = "Consumer Logger")
public class AppKafkaConsumer {

  
  @Value("${topic.name}")   
  private String topicName;

  @KafkaListener(topics = "users", groupId = "group_id")   
  public void consume(ConsumerRecord<String, UserDTO> record) {
    log.info(String.format("Consumed Message -> %s", record.value()));
  }
}
