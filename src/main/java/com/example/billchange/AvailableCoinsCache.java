package com.example.billchange;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AvailableCoinsCache {

    private static final Map<Double, Integer> availableCoins = new ConcurrentHashMap<>();


    static {
        //todo: use enums
        availableCoins.put(0.01, 100);
        availableCoins.put(0.05, 100);
        availableCoins.put(0.10, 100);
        availableCoins.put(0.25, 100);
    }

    public Integer getCoinQuantity(Double coin) {
        return availableCoins.get(coin);
    }

    public void getCoins(Double coin, Integer quantity) {
        var availableQuantity = getCoinQuantity(coin);
        var newQuantity = availableQuantity - quantity;
        availableCoins.put(coin, newQuantity);
    }

    public void resetCoinsAmountToDefault() {
        availableCoins.put(0.01, 100);
        availableCoins.put(0.05, 100);
        availableCoins.put(0.10, 100);
        availableCoins.put(0.25, 100);
    }
}
