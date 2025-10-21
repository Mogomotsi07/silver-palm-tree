public class CompanyCustomer extends Customer {
    private static final long serialVersionUID = 1L;
    private final String regNumber;
    private final String businessType;
    private final String incorporationDate;
    private final String businessPhone;
    private final String businessEmail;
    private final String signatoryName;
    private final String signatoryId;
    private final String signatoryRole;

    public CompanyCustomer(String id, String name, String address,
                           String phone, String email, String user, String pass,
                           String regNumber, String businessType, String incorporationDate,
                           String businessPhone, String businessEmail,
                           String signatoryName, String signatoryId, String signatoryRole) {
        super(id, name, address, phone, email, user, pass);
        this.regNumber = regNumber;
        this.businessType = businessType;
        this.incorporationDate = incorporationDate;
        this.businessPhone = businessPhone;
        this.businessEmail = businessEmail;
        this.signatoryName = signatoryName;
        this.signatoryId = signatoryId;
        this.signatoryRole = signatoryRole;
    }

    public String getRegNumber()          { return regNumber; }
    public String getBusinessType()       { return businessType; }
    public String getIncorporationDate()  { return incorporationDate; }
    public String getBusinessPhone()      { return businessPhone; }
    public String getBusinessEmail()      { return businessEmail; }
    public String getSignatoryName()      { return signatoryName; }
    public String getSignatoryId()        { return signatoryId; }
    public String getSignatoryRole()      { return signatoryRole; }
}