package uk.co.dnlegge;

import java.util.ArrayList;
import java.util.Collection;

public class ChangeGivingVendingMachine implements VendingMachine {

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

}
