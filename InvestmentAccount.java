public class InvestmentAccount extends Account implements Withdrawable, InterestBearing {
    public static final double MIN_OPENING = 500.0;
    private static final double RATE = 0.00075; // 0.075 % monthly

    @Override
    public boolean withdraw(double amt) {
        return super.withdraw(amt);
    }

    @Override
    public void applyInterest() {
        double interest = getBalance() * RATE;
        if (interest > 0) deposit(interest);
    }
}