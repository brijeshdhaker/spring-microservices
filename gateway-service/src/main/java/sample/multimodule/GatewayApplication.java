package sample.multimodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableEurekaClient
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

    /*
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/employee/**")
                        .uri("lb://USER-SERVICE"))

                .route(r -> r.path("/consumer/**")
                        .uri("lb://AUDITLOG-SERVICE"))
                .build();
    }

    */
    
}
