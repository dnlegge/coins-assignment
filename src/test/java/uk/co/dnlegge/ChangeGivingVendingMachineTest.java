package uk.co.dnlegge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class ChangeGivingVendingMachineTest {

    private static final String PATH_TO_CLEAN = "./src/test/resources/";
    private static final String PATH_TO_TARGET = "./target/";

    private static final String COIN_INVENTORY_PROPERTIES = "coin-inventory.properties";

    private ChangeGivingVendingMachine beingTested;

    @Before
    public void setUp() throws Exception {
        final File srcFile = new File(PATH_TO_CLEAN, COIN_INVENTORY_PROPERTIES);
        final File destFile = new File(PATH_TO_TARGET, COIN_INVENTORY_PROPERTIES);
        FileUtils.copyFile(srcFile, destFile);
        beingTested = new ChangeGivingVendingMachine(PATH_TO_TARGET);
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

    @Test
    public void testGetChangeWithNo20ps() throws Exception {

        final int testValue = 44;
        final Collection<Coin> result = beingTested.getChangeFor(testValue);

        assertNotNull(result);
        assertNotEquals(0, result.size());

        int total = getTotalAndPrintContents(result);

        assertEquals(testValue, total);
        //actual 10p 10p 10p 10p 2p 2p
        assertEquals(6, result.size());

    }

    @Test
    public void testGetChangeForLargeAmount() throws Exception {

        final int testValue = 799;
        final Collection<Coin> result = beingTested.getChangeFor(testValue);

        assertNotNull(result);
        assertNotEquals(0, result.size());

        int total = getTotalAndPrintContents(result);

        assertEquals(testValue, total);
        //actual 100p 100p 100p 100p 100p 100p 100p 50p 10p 10p 10p 10p 5p 2p 2p
        assertEquals(15, result.size());

    }

    @Test
    public void testGetChangeForLargerAmount() throws Exception {

        final int testValue = 1599;
        final Collection<Coin> result = beingTested.getChangeFor(testValue);

        assertNotNull(result);
        assertNotEquals(0, result.size());

        int total = getTotalAndPrintContents(result);

        assertEquals(testValue, total);
        //actual 100p 100p 100p 100p 100p 100p 100p 100p 100p 100p 100p 50p 50p 50p 50p 50p 50p 50p 50p 50p 10p 10p 10p 10p 5p 2p 2p
        assertEquals(27, result.size());

        final File expected = new File(PATH_TO_CLEAN, "expected-" + COIN_INVENTORY_PROPERTIES);
        final File actual = new File(PATH_TO_TARGET, COIN_INVENTORY_PROPERTIES);

        assertTrue(FileUtils.contentEquals(expected, actual));

    }

    @Test(expected = RuntimeException.class)
    public void testNotEnoughCoinsInTheWorld() throws Exception {

        final int testValue = 15999754;
        beingTested.getChangeFor(testValue);

        fail("Should have run out of coins");
    }

}