package uk.co.dnlegge;

public enum Coin {

    ONE_POUND("One pound", 100),
    FIFTY_PENCE("Fifty pence", 50),
    TWENTY_PENCE("Twenty pence", 20),
    TEN_PENCE("Ten pence", 10),
    FIVE_PENCE("Five pence", 5),
    TWO_PENCE("Two pence", 2),
    ONE_PENNY("One penny", 1);

    private final String description;
    private final int denomination;

    Coin(String name, int denomination) {
        this.description = name;
        this.denomination = denomination;
    }

    public static Coin getByDemonination(String denominationString) {
        final int value = Integer.parseInt(denominationString);
        for (Coin coin : values()) {
            if (value == coin.getDenomination()) {
                return coin;
            }
        }
        throw new IllegalArgumentException("No such coin of demonination " + denominationString);
    }

    public String getName() {
        return name();
    }

    public String getDescription() {
        return description;
    }

    public int getDenomination() {
        return denomination;
    }

    public String getDenominationString() {
        return denomination + "p";
    }
}
