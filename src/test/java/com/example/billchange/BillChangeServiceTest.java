package com.example.billchange;


import com.example.billchange.exceptions.NoAvailableCoinsForChangeException;
import com.example.billchange.exceptions.RequestInputValidationException;
import com.example.billchange.service.IBillChangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BillChangeServiceTest {

    AvailableCoinsCache cache = new AvailableCoinsCache();

    @Autowired
    private IBillChangeService billChangeService;

    @BeforeEach
   //run it before each test to restart cache data used
    void refreshDefaultSetting() {
        cache.resetCoinsAmountToDefault();
    }

    @Test
    void returnNewChangeWhenBillAmountIsIncorrectExceptionCase() throws RequestInputValidationException {
        Integer billAmount = 21;
        boolean getMaxNumberOfCoins = false;

        Throwable exception = assertThrows(RequestInputValidationException.class, () -> billChangeService.getBillChange(billAmount, getMaxNumberOfCoins));
        assertEquals("Bill amount is incorrect.", exception.getMessage());
    }

    @Test
    void returnNewChangeWhenBillWhenThereAreNotEnoughCoinsExceptionCase() throws NoAvailableCoinsForChangeException {
        Integer billAmount = 100;
        boolean getMaxNumberOfCoins = false;

        Throwable exception = assertThrows(NoAvailableCoinsForChangeException.class, () -> billChangeService.getBillChange(billAmount, getMaxNumberOfCoins));
        assertEquals("Not enough coins for the change.", exception.getMessage());
    }

    @Test
    void returnNewChangeCalculateCorrectlyTheSmallestAmountDefault() throws NoAvailableCoinsForChangeException {
        Integer billAmount = 1;
        boolean getMaxNumberOfCoins = false;

        Map<Double, Integer> expected = new HashMap<>();
        expected.put(0.25, 4);

        Map<Double, Integer> billChangeMap = billChangeService.getBillChange(billAmount, getMaxNumberOfCoins);

        assertFalse(billChangeMap.isEmpty());
        assertEquals(expected, billChangeMap); //result is 0.25 x 4
    }

    @Test
    void returnNewChangeCalculateCorrectlyLeftOverAmountDefault() throws NoAvailableCoinsForChangeException {
        Integer billAmount = 20;
        boolean getMaxNumberOfCoins = false;
        Map<Double, Integer> expected = new HashMap<>();
        expected.put(0.25, 80);

        Map<Double, Integer> billChangeMap = billChangeService.getBillChange(billAmount, getMaxNumberOfCoins);
        assertEquals(expected, billChangeMap); //0.25x80 =20

        billChangeService.getBillChange(billAmount, getMaxNumberOfCoins); // call one more - now we have used 40 out of 41 available.
        expected.clear();

        Integer newBillAmount = 1;
        expected.put(0.01, 100);
        //only 100 of 0.01 should be left
        assertEquals(expected, billChangeService.getBillChange(newBillAmount, getMaxNumberOfCoins));
    }

    @Test
    void returnNewChangeCalculateCorrectlyMaxAmountOfCoins() throws NoAvailableCoinsForChangeException {
        Integer billAmount = 1;
        boolean getMaxNumberOfCoins = true;
        Map<Double, Integer> expected = new HashMap<>();
        expected.put(0.01, 100);

        Map<Double, Integer> billChangeMap = billChangeService.getBillChange(billAmount, getMaxNumberOfCoins);
        assertEquals(expected, billChangeMap); //reverse order - taking the smallest amount first

        expected.clear();
        expected.put(0.05, 20);
        //all 0.01 were already used so now only 0.05 coin should be used
        assertEquals(expected, billChangeService.getBillChange(billAmount, getMaxNumberOfCoins));
    }

}
