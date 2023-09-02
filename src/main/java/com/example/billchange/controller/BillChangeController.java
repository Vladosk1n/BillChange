package com.example.billchange.controller;

import com.example.billchange.service.IBillChangeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/bill-service/v1")
public class BillChangeController {

    private final IBillChangeService billChangeService;

    public BillChangeController(IBillChangeService billChangeService) {
        this.billChangeService = billChangeService;
    }

    @GetMapping("/request-change")
    public ResponseEntity<Map<Double, Integer>> requestCoinsChange(@RequestParam Integer billAmount, @RequestParam Boolean getMaxNumberOfCoins) {

        return ResponseEntity.ok((billChangeService.getBillChange(billAmount, getMaxNumberOfCoins)));
    }

}
