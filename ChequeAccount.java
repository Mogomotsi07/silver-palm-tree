public class ChequeAccount extends Account {
    private final String employer;
    private final String employerAddress;

    public ChequeAccount(String employer, String employerAddress) {
        this.employer = employer;
        this.employerAddress = employerAddress;
    }
    @Override
    public void applyInterest() { /* none */ }
}