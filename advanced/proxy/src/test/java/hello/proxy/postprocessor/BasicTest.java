package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class BasicTest {

    @Test
    void basicConfig() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);
        A a = applicationContext.getBean("beanA", A.class);
        a.helloA();

        assertThatThrownBy(() -> applicationContext.getBean(B.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);

    }


    @Configuration
    static class BasicConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }
    }

    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }
}
