package uk.co.dnlegge;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class ChangeGivingVendingMachine implements VendingMachine {

    private static final String COIN_INVENTORY_PROPERTIES = "coin-inventory.properties";
    private final String pathToInventory;

    private Map<Coin, Integer> availableCoins;

    public ChangeGivingVendingMachine(String pathToInventory) throws IOException {
        this.pathToInventory = pathToInventory;

        List<String> lines = Files.readAllLines(Paths.get(pathToInventory + File.separator + COIN_INVENTORY_PROPERTIES), StandardCharsets.UTF_8);
        this.availableCoins = new HashMap<>();
        for (String line : lines) {
            final String[] split = line.split("=");
            availableCoins.put(Coin.getByDemonination(split[0]), Integer.parseInt(split[1]));
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

    private Coin selectLargestCoinPossible(int requiredAmount) {
        final Coin[] values = Coin.values();
        for (Coin coin : values) {
            if (coin.getDenomination() <= requiredAmount) {
                return coin;
            }
        }
        return null;
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

        writeOutAvailableCoinage();

        return coins;
    }

    private void writeOutAvailableCoinage() {
        String fileContents = generateFileContents();

        overwriteExistingFile(fileContents);
    }

    private void overwriteExistingFile(String fileContents) {
        try {

            File file = new File(pathToInventory, COIN_INVENTORY_PROPERTIES);
            file.delete();

            FileUtils.writeStringToFile(file, fileContents);
        } catch (IOException e) {
            throw new RuntimeException("Error writing inventory file out: " + e.getMessage(), e);
        }
    }

    private String generateFileContents() {
        StringBuilder fileContents = new StringBuilder();
        for (Coin coin : Coin.values()) {
            if (availableCoins.containsKey(coin)) {
                fileContents.append(coin.getDenomination())
                        .append("=")
                        .append(availableCoins.get(coin))
                        .append("\n");
            }

        }
        return fileContents.toString();
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
        if (availableCoins.containsKey(coin)) {
            final Integer availability = availableCoins.get(coin);
            if (availability > 0) {
                availableCoins.put(coin, availability - 1);
                return true;
            }
        }
        return false;
    }

}
