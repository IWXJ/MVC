
package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author peter
 */

public class CreateCustomer
{
    private static final String dbURL = "jdbc:derby://localhost:1527//home/peter/pizzabase";
    private static final String tableName = "customer";
    private static Connection conn = null;
    private static Statement stmt = null;
    Boolean validates = false;
    String status=null;

    
    public String addCustomer (String userid, String password, String username, String street, String town, Integer zipcode, Integer tel) {
    
        String result="OK";
        
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
    
        /*
        Create a user
        Paramaters IN = userid, password, username, street, town, zipcode, telephone_number
        Parameters OUT = OK | INVALID_PW_ZIP_TEL | DUPLICATE
        */
   
        if (!
                (
                validatePassword(password) && 
                validateZip(zipcode.toString()) && 
                validateTel(tel.toString())
                )
            )
            {
              status="INVALID_PW_ZIP_TEL"; 
            }
        
        try
        {
            stmt = conn.createStatement();
            stmt.execute("insert into " + tableName + " values ('" + userid + "','" + password + "','" + username + "','" + street + "','" + town + "'," + zipcode + "," + tel + ")");
            stmt.close();
        }
        catch(SQLException sql)
        {
            //If the customer name already exists:
            if (sql.getMessage().contains("duplicate key"))
            {
                result = "DUPLICATE";
            }
            else result=sql.getMessage();
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

        return result;

    }

    public Boolean validatePassword(String pwd)
    {
    // String passwd = "aeiouy"; 
        /*
        Following password rules apply:
        
        (?=.*[0-9]) Minimum one digit
        (?=.*[a-z]) Minimum one lowercase character
        (?=.*[A-Z]) Minimum one uppercase character
        (?=.*[@#$%^&+=_]) Minimum one special character 
        (?=\\S+$) no whitespace allowed
        .{8,12} Minimum eight characters, maximum 12 characters
        
        */
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,12}";
        return (pwd.matches(pattern));
    }
    
    private static Boolean validateZip(String zip)
    {
        /*
        Following password rules apply:
        
        (?=[0-9]+$) only digits permitted
        (?=\\S+$) no whitespace allowed
        .{4,4} Minimum four digits, maximum 4 digits
        
        */
        String pattern = "(?=[0-9]+$)(?=\\S+$).{4,4}";
        return (zip.matches(pattern));
    }

    private static Boolean validateTel(String tel)
    {
        /*
        Following password rules apply:
        
        (?=[0-9]+$) only digits permitted
        (?=\\S+$) no whitespace allowed
        .{8,8} Minimum eight digits, maximum eight digits
        
        */
        String pattern = "(?=[0-9]+$)(?=\\S+$).{8,8}";
        return (tel.matches(pattern));
    }

}
