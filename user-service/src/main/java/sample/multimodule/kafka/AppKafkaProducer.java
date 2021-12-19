/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.multimodule.kafka;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import sample.multimodule.dto.avro.UserDTO;

import java.util.Properties;

/**
 *
 * @author brijeshdhaker
 */
//@Service
public class AppKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(AppKafkaProducer.class);

    @Value("${topic.name}")
    private String TOPIC;
    private static int counter = 0;
    private final KafkaTemplate<String, UserDTO> kafkaTemplate;
    
    @Autowired
    public AppKafkaProducer(KafkaTemplate<String, UserDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserDTO user) {
        
        this.kafkaTemplate.send(this.TOPIC, user.getName(), user);
        log.info(String.format("Produced Message -> %s", user));
        
    }
    
    public void processMessage(UserDTO user) {
        
        //this.kafkaTemplate.send(this.TOPIC, user.getName(), user);
        
        Properties properties = new Properties();
        // Kafka Properties
        properties.setProperty("bootstrap.servers", "broker:9092");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "10");
        // Avro properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://schema-registry:8081");

        KafkaProducer<String, UserDTO> producer = new KafkaProducer<String, UserDTO>(properties);
        
        String key = "id_"+counter++;
        ProducerRecord<String, UserDTO> producerRecord = new ProducerRecord<String, UserDTO>(TOPIC, key, user);
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    log.info("Successfully received the details as: \n" +  
                    "Topic:" + metadata.topic() + "\n" +  
                    "Partition:" + metadata.partition() + "\n" +  
                    "Offset" + metadata.offset() + "\n" +  
                    "Timestamp" + metadata.timestamp());  
                } else {
                    log.error(exception.getMessage());
                }
            }
        });

        producer.flush();
        producer.close();
            
        log.info(String.format("Produced user -> %s", user));
    }
}
