package org.example;

import jakarta.annotation.Resource;
import org.example.other.BeanViewer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LocalMcpClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(LocalMcpClientApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner runBeanViewer(BeanViewer beanViewer) {
        return args -> {
            System.out.println("========== 所有 Bean 名称 ==========");
            beanViewer.printAllBeanNames();
            System.out.println("==================================");
        };
    }*/
}
