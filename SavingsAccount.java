public class SavingsAccount extends Account implements InterestBearing {
    public static final double MIN_OPENING = 50.0;
    private static final double RATE = 0.00025; // 0.025 % monthly

    @Override
    public boolean withdraw(double amt) {
        AlertUtil.error("Savings account does not allow withdrawals.");
        return false;
    }

    @Override
    public void applyInterest() {
        double interest = getBalance() * RATE;
        if (interest > 0) deposit(interest);
    }
}