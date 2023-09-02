package com.example.billchange.service;

import com.example.billchange.exceptions.NoAvailableCoinsForChangeException;
import com.example.billchange.exceptions.RequestInputValidationException;

import java.util.Map;

public interface IBillChangeService {

    /**
     * return available change in coins
     *
     * @param billAmount the bill request
     * @return map of coins in format <coinValue, coinAmount>
     * @throws RequestInputValidationException if request input is wrong
     * @throws NoAvailableCoinsForChangeException  if there's not enough coins
     */
    Map<Double, Integer> getBillChange(Integer billAmount, boolean getMaxNumberOfCoins) throws RequestInputValidationException, NoAvailableCoinsForChangeException;
}
