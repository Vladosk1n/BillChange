package com.example.billchange;

import com.example.billchange.controller.BillChangeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BillChangeApplicationTests {

    @Autowired
    private BillChangeController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
