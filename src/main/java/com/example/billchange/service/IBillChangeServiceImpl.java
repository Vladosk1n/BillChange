package com.example.billchange.service;

import com.example.billchange.AvailableCoinsCache;
import com.example.billchange.exceptions.NoAvailableCoinsForChangeException;
import com.example.billchange.exceptions.RequestInputValidationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class IBillChangeServiceImpl implements IBillChangeService {

    public static final double[] COIN_VALUES = {0.25, 0.10, 0.05, 0.01};
    private final AvailableCoinsCache availableCoins;

    static Logger log = Logger.getLogger(IBillChangeServiceImpl.class.getName());

    public IBillChangeServiceImpl(AvailableCoinsCache availableCoins) {
        this.availableCoins = availableCoins;
    }

    @Override
    public HashMap<Double, Integer> getBillChange(Integer billAmount, boolean getMaxNumberOfCoins) throws RequestInputValidationException, NoAvailableCoinsForChangeException {
        if (!validateBill(billAmount)) {
            //validating request input
            log.warning("The request validation has failed.");
            throw new RequestInputValidationException();
        }

        Map<Double, Integer> billChange = getChange(billAmount, getMaxNumberOfCoins);
        if (billChange == null) {
            log.warning("There are not enough available coins.");
            throw new NoAvailableCoinsForChangeException();
        }

        return (HashMap<Double, Integer>) billChange;
    }


    public Map<Double, Integer> getChange(int billAmount, boolean getMaxNumberOfCoins) {
        Map<Double, Integer> change = new HashMap<>();
        double[] coins = getMaxNumberOfCoins ? Arrays.stream(COIN_VALUES).sorted().toArray() : COIN_VALUES;

        for (double coinValue : coins) {
            int availableCoinQuantity = availableCoins.getCoinQuantity(coinValue);
            log.info("Gathering available coin quantity: " + availableCoinQuantity + "coins available for " + coinValue);
            //if some coins of the type available
            if (availableCoinQuantity > 0) {
                int numberOfCoinsRequired = Math.min(availableCoinQuantity, (int) (billAmount / coinValue));
                if (numberOfCoinsRequired > 0) {
                    //get needed number of coins of the type
                    availableCoins.getCoins(coinValue, numberOfCoinsRequired);
                    change.put(coinValue, numberOfCoinsRequired);
                    billAmount -= (int) (numberOfCoinsRequired * (coinValue));
                }
            }
        }
        if (billAmount == 0) {
            log.info("Processing change is completed.");
            return change;
        } else {
            // Cannot return exact change - not enough coins
            return null;
        }
    }

    private boolean validateBill(Integer billAmount) {
        switch (billAmount) {
            case 1, 2, 5, 10, 20, 50, 100 -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}
