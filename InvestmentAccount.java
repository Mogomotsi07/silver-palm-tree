public class InvestmentAccount extends Account implements Withdrawable, InterestBearing {
    private static final double RATE = 0.00075;

    @Override
    public boolean withdraw(double amount) {
        return super.withdraw(amount);   // use base logic
    }

    @Override
    public void applyInterest() {
        double interest = getBalance() * RATE;
        if (interest > 0) deposit(interest);
    }

}