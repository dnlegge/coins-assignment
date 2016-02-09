package uk.co.dnlegge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;

public class ChangeGivingVendingMachineTest {

    @Test
    public void testGetCorrectChange() throws Exception {

        final ChangeGivingVendingMachine beingTested = new ChangeGivingVendingMachine();

        final int testValue = 5;
        final Collection<Coin> result = beingTested.getOptimalChangeFor(testValue);

        assertNotNull(result);
        assertNotEquals(0, result.size());

        int total = getTotalAndPrintContents(result);

        assertEquals(testValue, total);

    }

    @Test
    @Ignore
    public void testGetOptimalChange() throws Exception {

        final ChangeGivingVendingMachine beingTested = new ChangeGivingVendingMachine();

        final int testValue = 48;
        final Collection<Coin> result = beingTested.getOptimalChangeFor(testValue);

        assertNotNull(result);
        assertNotEquals(0, result.size());

        int total = getTotalAndPrintContents(result);

        assertEquals(testValue, total);
        // 20p, 20p, 5p, 2p, 1p
        assertEquals(5, result.size());

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