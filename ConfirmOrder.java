package Entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 *
 * @author peter
 */
public class ConfirmOrder {

    String result = "OK";
    String session;
    String time = null;
    String customer;
    String pizzaOrder;
    int quantity;

    
    public ConfirmOrder(String session_id) {
        // take values from basket table and place in order table
        session = session_id;
        
    }
    
    public ConfirmOrder(String session_id, String time) {
        // take values from basket table and place in order table

        time = time;
        session = session_id;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()+10*60*1000)); // now + 10 mins
        String requestedDate = ((time == null) ? timeStamp : time );  // assign a time if one is not passed in
        
    }
    
    //Object add(Integer session_id, String time) throws SQLException {
    public Object add() throws SQLException {
        // writes into BASKET table the provisional order including session_id (used to find order in DB and confirm it at checkout)
        String dbURL = "jdbc:derby://localhost:1527//home/peter/pizzabase";
        String tableName1 = "basket";
        String tableName2 = "orders";

        // jdbc Connection
        Connection conn = null;
        Statement stmtRead = null;
        Statement stmtWrite = null;
        Statement stmtDelete = null;
        
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

        stmtRead = conn.createStatement();
        ResultSet results = stmtRead.executeQuery("select customer, pizzaorder, quantity from " + tableName1 + " where session = "+ session);
        stmtWrite = conn.createStatement();

        while(results.next())
        {
            customer = results.getString(1);
            pizzaOrder = results.getString(2);
            quantity = results.getInt(3);

            stmtWrite.execute("insert into " + tableName2 + " values ('" + customer + "','" + pizzaOrder + "'," + quantity + ", TIMESTAMP('" + time +"'))");
        }
        results.close();
        stmtRead.close();
        stmtWrite.close();
        stmtDelete = conn.createStatement();
        stmtDelete.execute("delete from " + tableName1 + " where session = "+ session);

        try
        {
            if (stmtRead != null)
            {
                stmtRead.close();
            }
            if (stmtWrite != null)
            {
                stmtWrite.close();
            }
                        if (stmtDelete != null)
            {
                stmtDelete.close();
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
    
    
        
    
    
