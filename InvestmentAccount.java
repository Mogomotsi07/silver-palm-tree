public class InvestmentAccount extends Account {
    private static final double RATE = 0.00075; // 0.075 % monthly

    @Override
    public void applyInterest() {
        throw new UnsupportedOperationException("Unimplemented method 'applyInterest'");
    }
}