package sample.multimodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.netflix.hystrix.EnableHystrix;
//import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

//@EnableCircuitBreaker
//@EnableHystrix
//@EnableHystrixDashboard
@SpringBootApplication
@EnableEurekaClient
//@EnableBinding(Processor.class)
public class AuditlogApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AuditlogApplication.class, args);
    }
}
