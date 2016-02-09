package uk.co.dnlegge;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeGivingVendingMachine implements VendingMachine {

    private Map<Integer, Integer> availableCoins;

    public ChangeGivingVendingMachine() throws IOException {
        final String pathToDir = "./src/main/resources/";

        List<String> lines = Files.readAllLines(Paths.get(pathToDir + "coin-inventory.properties"), StandardCharsets.UTF_8);
        availableCoins = new HashMap<>();
        for (String line : lines) {
            final String[] split = line.split("=");
            availableCoins.put(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }

    @Override
    public Collection<Coin> getOptimalChangeFor(int pence) {
        final ArrayList<Coin> coins = new ArrayList<>();
        int runningTotal = 0;
        Coin thisCoin;

        while ((thisCoin = selectLargestCoinPossible(pence - runningTotal)) != null) {
            coins.add(thisCoin);
            runningTotal += thisCoin.getDenomination();
        }

        return coins;
    }

    @Override
    public Collection<Coin> getChangeFor(int pence) {
        final ArrayList<Coin> coins = new ArrayList<>();
        int runningTotal = 0;
        Coin thisCoin;

        while ((thisCoin = selectLargestAvailableCoinPossible(pence - runningTotal)) != null) {
            coins.add(thisCoin);
            runningTotal += thisCoin.getDenomination();
        }

        return coins;
    }

    private Coin selectLargestCoinPossible(int requiredAmount) {
        final Coin[] values = Coin.values();
        for (Coin coin : values) {
            if (coin.getDenomination() <= requiredAmount) {
                return coin;
            }
        }
        return null;
    }

    private Coin selectLargestAvailableCoinPossible(int requiredAmount) {
        if (requiredAmount <= 0) {
            return null;
        }
        final Coin[] values = Coin.values();
        for (Coin coin : values) {
            if (coin.getDenomination() <= requiredAmount && isAvailable(coin)) {
                return coin;
            }
        }
        throw new RuntimeException("Run out of coinage - can't supply " + requiredAmount + "p");
    }

    private boolean isAvailable(Coin coin) {
        if (availableCoins.containsKey(coin.getDenomination())) {
            final Integer availability = availableCoins.get(coin.getDenomination());
            if (availability > 0) {
                availableCoins.put(coin.getDenomination(), availability - 1);
                return true;
            }
        }
        return false;
    }

}
