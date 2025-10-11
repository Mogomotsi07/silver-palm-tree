public class CompanyCustomer extends Customer {
    private static final long serialVersionUID = 1L;
    private final String registrationNumber;
    private final String contactPerson;
    private final String companyType;

    public CompanyCustomer(String id, String name, String address,
                           String phone, String email, String user,
                           String pass, String regNum, String contact,
                           String type) {
        super(id, name, address, phone, email, user, pass);
        this.registrationNumber = regNum;
        this.contactPerson = contact;
        this.companyType = type;
    }
    
}