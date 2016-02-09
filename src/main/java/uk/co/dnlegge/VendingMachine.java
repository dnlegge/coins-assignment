package uk.co.dnlegge;

import java.util.Collection;

public interface VendingMachine {

    Collection<Coin> getOptimalChangeFor(int pence);

    Collection<Coin> getChangeFor(int pence);
}
