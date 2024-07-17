package com.ASM.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ASM.demo.util.DataGenerator;
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ASM.demo.repository")
public class DemoApplication {
 	public static void main(String[] args) {
    //SpringApplication.run(DemoApplication.class, args);
    ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        // Populate database with sample data using DataGenerator
        DataGenerator dataGenerator = context.getBean(DataGenerator.class);
        dataGenerator.generateData();
  }
}
