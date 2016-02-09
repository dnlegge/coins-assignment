package uk.co.dnlegge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class ChangeGivingVendingMachineTest {

    private ChangeGivingVendingMachine beingTested;

    @Before
    public void setUp() throws Exception {
        beingTested = new ChangeGivingVendingMachine();
    }

    @Test
    public void testGetCorrectChange() throws Exception {

        final int testValue = 5;
        final Collection<Coin> result = beingTested.getOptimalChangeFor(testValue);

        assertNotNull(result);
        assertNotEquals(0, result.size());

        int total = getTotalAndPrintContents(result);

        assertEquals(testValue, total);

    }

    @Test
    public void testGetOptimalChange() throws Exception {

        final int testValue = 48;
        final Collection<Coin> result = beingTested.getOptimalChangeFor(testValue);

        assertNotNull(result);
        assertNotEquals(0, result.size());

        int total = getTotalAndPrintContents(result);

        assertEquals(testValue, total);
        // 20p, 20p, 5p, 2p, 1p
        assertEquals(5, result.size());

    }

    @Test
    public void testGetOptimalChangeForLargeAmount() throws Exception {

        final int testValue = 799;
        final Collection<Coin> result = beingTested.getOptimalChangeFor(testValue);

        assertNotNull(result);
        assertNotEquals(0, result.size());

        int total = getTotalAndPrintContents(result);

        assertEquals(testValue, total);
        assertEquals(13, result.size());

    }

    @Test
    public void testGetOptimalChangeForDuffAmount() throws Exception {


        beingTested = new ChangeGivingVendingMachine();

        final int testValue = -4;
        final Collection<Coin> result = beingTested.getOptimalChangeFor(testValue);

        assertNotNull(result);
        assertEquals(0, result.size());

    }

    private int getTotalAndPrintContents(Collection<Coin> result) {
        int total = 0;
        String coinsString = "";
        for (Coin coin : result) {
            total += coin.getDenomination();
            coinsString += coin.getDenominationString() + " ";
        }
        System.out.println(coinsString);
        return total;
    }


}