public class IndividualCustomer extends Customer {
    private static final long serialVersionUID = 1L;
    private final String idNumber;
    private final String dateOfBirth;

    public IndividualCustomer(String id, String name, String address,
                              String phone, String email, String user,
                              String pass, String idNumber, String dob) {
        super(id, name, address, phone, email, user, pass);
        this.idNumber = idNumber;
        this.dateOfBirth = dob;
    }
}