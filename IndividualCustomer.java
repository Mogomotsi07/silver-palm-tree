public class IndividualCustomer extends Customer {
    private static final long serialVersionUID = 1L;
    private final String idNumber;
    private final String dob;
    private final String gender;
    private final String nationality;
    private final String occupation;
    private final String mobile;

    public IndividualCustomer(String id, String name, String address,
                              String phone, String email, String user, String pass,
                              String idNumber, String dob, String gender,
                              String nationality, String occupation, String mobile) {
        super(id, name, address, phone, email, user, pass);
        this.idNumber = idNumber;
        this.dob = dob;
        this.gender = gender;
        this.nationality = nationality;
        this.occupation = occupation;
        this.mobile = mobile;
    }

    public String getIdNumber()    { return idNumber; }
    public String getDob()         { return dob; }
    public String getGender()      { return gender; }
    public String getNationality() { return nationality; }
    public String getOccupation()  { return occupation; }
    public String getMobile()      { return mobile; }
}