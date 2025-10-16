public class ChequeAccount extends Account implements Withdrawable {
    private final String employer;
    private final String employerAddress;

    public ChequeAccount(String employer, String employerAddress) {
        this.employer = employer;
        this.employerAddress = employerAddress;
    }

    @Override
    public boolean withdraw(double amount) {
        return super.withdraw(amount);
    }

    @Override
    public void applyInterest() {

    }
    // no interest
}