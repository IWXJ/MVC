package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author peter
 */

public class DeletePizza
{
    
    public DeletePizza() {
    }

    public String deletePizza(String name) {
        String dbURL = "jdbc:derby://localhost:1527//home/peter/pizzabase";
        String tableName = "pizza";
        // jdbc Connection
        Connection conn = null;
        Statement stmt = null;
        String result = "OK";
        
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (ClassNotFoundException cnfe) {
            System.err.println("Derby driver not found.");
            result = cnfe.getMessage();
        }
        catch (InstantiationException | IllegalAccessException | SQLException except)
        {
            result = except.getMessage();
        }


        try
        {
            stmt = conn.createStatement();
            stmt.execute("delete from " + tableName + " where name = '" + name + "'");
            stmt.close();
            }
            
            catch(SQLException sql)
            {
                result = sql.getMessage();
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
                result = sql.getMessage();
            }
    return result;
    }
}

