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

public class AddPizza
{
    String dbURL = "jdbc:derby://localhost:1527//home/peter/pizzabase";
    String tableName = "pizza";
    // jdbc Connection
    Connection conn = null;
    Statement stmt = null;
    
    public AddPizza() {
    }

    public String addPizza(String name, String desc, Double price) {
        String result = "OK";
        
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
            stmt.execute("insert into " + tableName + " values ('" + name + "','" + desc + "'," + price +")");
            stmt.close();
            }
            
            catch(SQLException sql)
            {
            //If the pizza name already exists:
                if (sql.getMessage().contains("duplicate key"))
                {
                    result = "DUPLICATE";
                }   
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
    
    public void deletePizza(String name) {
        tableName = "pizza";
        conn = getConnection(dbURL);

        try
        {
            stmt = conn.createStatement();
            int executeUpdate = stmt.executeUpdate("delete from " + tableName + " where name = '" + name + "'");
            stmt.close();
        } catch(SQLException sql) {
            sql.printStackTrace();
        }
        
        closeConnection(stmt, conn);
    }
        
    private Connection getConnection(String dbURL) {
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
    return conn;
    }
    
    private void closeConnection(Statement stmt, Connection conn) {
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
    }

}
