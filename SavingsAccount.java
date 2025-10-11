public class SavingsAccount extends Account {
    private static final double RATE = 0.00025;

    @Override
    public void applyInterest() {
        double interest = balance * RATE;
        if (interest > 0) deposit(interest);
    }
    
}