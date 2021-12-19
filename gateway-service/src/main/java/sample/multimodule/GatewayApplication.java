package sample.multimodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

    /*
    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
    }
    */

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GatewayApplication.class, args);
    }
    
}
