package hello.proxy.config;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppV2Config {

    @Bean
    public OrderControllerV2 orderController2() {
        return new OrderControllerV2(orderService2());
    }

    @Bean
    public OrderServiceV2 orderService2() {
        return new OrderServiceV2(orderRepository2());
    }

    @Bean
    public OrderRepositoryV2 orderRepository2() {
        return new OrderRepositoryV2();
    }
}
