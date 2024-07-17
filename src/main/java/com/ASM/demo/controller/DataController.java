package com.ASM.demo.controller;

import com.ASM.demo.util.DataGenerator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private final DataGenerator dataGenerator;

    public DataController(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @GetMapping("/data")
    public String generateData() {
        dataGenerator.generateData();
        return "Data generation initiated!";
    }

}
