public class SavingsAccount extends Account implements InterestBearing {

    private static final double RATE = 0.00025;
    public  static final double MIN_OPENING = 50.0;

    /*  Block every withdraw attempt  */
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