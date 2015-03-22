package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter
 */
public class ListCustomers {

    private List<CustomerObject> theCustomers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    class CustomerObject {
        String name;
        String password;
        String username;
        String street;
        String town;
        Integer zip; 
        Integer tel;     

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
                
        private void setName(String name) {
            this.name = name;
        }

        private void setPassword(String password) {
            this.password = password;
        }

        private void setUsername(String username) {
            this.username = username;
        }

        private void setStreet(String street) {
            this.street = street;
        }

        private void setTown(String town) {
            this.town = town;
        }

        private void setZip(String zip) {
            this.zip = Integer.valueOf(zip);
        }

        private void setTel(String tel) {
            this.tel = Integer.valueOf(tel);
        }



 
    }
    public List<CustomerObject> getTheCustomers() {
        return theCustomers;
    }
    
    List<CustomerObject>  theCustomers = new ArrayList<>();  // Customer object is ONE customer. This is an array of customer objects


    public List<CustomerObject> getCustomer() throws InterruptedException {
        String dbURL = "jdbc:derby://localhost:1527//home/peter/pizzabase";
        String tableName = "customer";
        // jdbc Connection
        Connection conn = null;
        Statement stmt = null;

        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (ClassNotFoundException cnfe) {
            System.err.println("Derby driver not found.");
        }
        catch (InstantiationException | IllegalAccessException | SQLException except)
        {
            except.printStackTrace();
        }


        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName );

            while(results.next())
            {
                
                CustomerObject co = new CustomerObject();

                co.setName(results.getString(1));
                co.setPassword(results.getString(2));
                co.setUsername(results.getString(3));
                co.setStreet(results.getString(4));
                co.setTown(results.getString(5));
                co.setZip(results.getString(6));
                co.setTel(results.getString(7));
                getTheCustomers().add(co);
            }


            results.close();
            stmt.close();
            }
            catch (SQLException sql)
            {
                sql.printStackTrace();
            }

            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
                if (conn != null)
                {
                    DriverManager.getConnection(dbURL);
                    conn.close();
                }           
            }
            catch (SQLException sql)
            {
                sql.printStackTrace();
            }
            
        return getTheCustomers();
    }
}

