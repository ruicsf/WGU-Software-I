package Model;
/**
 * Class for  Outsourced parts*/
public class Outsourced extends Part{

    private String companyName;
    /** Constructor for Outsourced class
     * @param companyName string type used for company name*/
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**CompanyName getter*/
    public String getCompanyName() {
        return companyName;
    }
    /**CompanyName setter*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
