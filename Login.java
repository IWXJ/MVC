package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author peter
 */
public class Login {

    public String logintoApplication(String userid, String password) throws SQLException {
        //String dbURL = "jdbc:derby:http://localhost:1527/home/peter/pizzabase";
        String dbURL = "jdbc:derby://localhost:1527//home/peter/pizzabase";
        String tableName = "customer";
        // jdbc Connection
        Connection conn = null;
        Statement stmt = null;
        String result = null;

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
            ResultSet results = stmt.executeQuery("select * from " + tableName + " where id = '" + userid + "' and password = '" + password + "'");

            if (results.next()) {
                result = "OK";
            } else result = "INVALID";


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
            
        return result;
        }
    }
