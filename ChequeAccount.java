public class ChequeAccount extends Account implements Withdrawable {
    private final String employer;
    private final String employerAddress;

    public ChequeAccount(String emp, String empAddr) {
        this.employer = emp;
        this.employerAddress = empAddr;
    }

    @Override
    public boolean withdraw(double amt) {
        return super.withdraw(amt);
    }

    @Override
    public void applyInterest() { /* none */ }

    public String getEmployer() { return employer; }
    public String getEmployerAddress() { return employerAddress; }
}