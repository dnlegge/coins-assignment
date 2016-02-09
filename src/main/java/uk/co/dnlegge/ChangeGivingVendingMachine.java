package uk.co.dnlegge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ChangeGivingVendingMachine implements VendingMachine {

    @Override
    public Collection<Coin> getOptimalChangeFor(int pence) {
        if (pence < 1) {
            return Collections.emptyList();
        }
        final ArrayList<Coin> coins = new ArrayList<>();
        int runningTotal = 0;

        while (runningTotal < pence) {
            Coin thisCoin = selectLargestCoinPossible(pence - runningTotal);

            coins.add(thisCoin);
            runningTotal += thisCoin.getDenomination();
        }

        return coins;
    }

    private Coin selectLargestCoinPossible(int runningTotal) {
        final Coin[] values = Coin.values();
        for (Coin coin : values) {
            if (coin.getDenomination() <= runningTotal) {
                return coin;
            }
        }
        throw new RuntimeException("No acceptable coin found");
    }

}
