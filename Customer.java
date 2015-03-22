package Entity;

/**
 *
 * @author peter
 */


import java.util.ArrayList;
import java.util.List;

/**
 * Pizza entity. Implements interface from interface class CustomerInterface.
 * @author Peter O'Neil
 */

public class Customer implements CustomerInterface {
     String name;
     String password;
     String username;
     String street;
     String town;
     Integer zip; 
     Integer tel;
    
    public Customer(){
        
    }

          // constructor
    public Customer(String name, String password, String username, String street, String town, Integer zip, Integer tel){         // instantiate the object with all variables
        name = name;
        password = password;
        username = username;
        street = street;
        town = town;
        zip = zip;
        tel = tel;
    }
    
    
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    
    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }
    
    public Integer getZip() {
        return zip;
    }

    public Integer getTel() {
        return tel;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }
    
//        /**
//     * @return the thePizzas
//     */
//    public List<ListCustomers.CustomerObject> getTheCustomers() {
//        return theCustomers;
//    }
    
    @Override
    public String toString()
    {
        return "A customer : " + this.getName() + ", " 
                + this.getPassword() + ", " 
                + this.getUsername() + ", "
                + this.getStreet() + ", "
                + this.getTown() + ", "
                + this.getZip() + ", "
                + this.getTel() + ".";
    }
    
    
    public String getCustomer(String name) {
        return "{ " + this.name + ", " 
                    + this.password + ", " 
                    + this.username + ", "
                    + this.street + ", "
                    + this.town + ", "
                    + this.zip + ", "
                    + this.tel +
                "}";
    }
    
    public boolean equals(Customer c) {
        return ( (c.name == this.name) 
                && (c.password == this.password) 
                && (c.street == this.street)
                && (c.town == this.town)
                && (c.zip == this.zip)
                && (c.tel == this.tel)) ;
    };

    public String getCustomer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addCustomer(String name, String password, String username, String street, String town, Integer zip, Integer tel) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.street = street;
        this.town = town;
        this.zip = zip;
        this.tel = tel;
        theCusArray.add(this);
    }


}

