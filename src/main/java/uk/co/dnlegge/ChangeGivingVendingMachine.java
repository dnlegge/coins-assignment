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
        int runningTotal = pence;

        while (runningTotal > 0) {
            Coin thisCoin = Coin.ONE_PENNY;

            coins.add(thisCoin);
            runningTotal -= thisCoin.getDenomination();
        }

        return coins;
    }

}
