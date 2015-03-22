package Entity;


import java.util.ArrayList;

/**
 *
 * @author xpqn
 */
public interface CustomerInterface {
    public String getCustomer() ;
    public void addCustomer(String name,String password, String username, String street, String town, Integer zip, Integer tel);
    ArrayList<Customer> theCusArray = new ArrayList<>();
}

