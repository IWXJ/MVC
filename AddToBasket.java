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
public class AddToBasket {
    String dbURL = "jdbc:derby://localhost:1527//home/peter/pizzabase";
    Connection conn = null;
    Statement stmt = null;
    String result = "";

    public Object add(String userid, String session_id, String name, Integer quantity, Integer price) {
        // writes into BASKET table the provisional order including session_id (used to find order in DB and confirm it at checkout)
        String tableName = "basket";
        String sql_stmt = "";
        System.out.println(""+userid+session_id+name+quantity+price);
// Get item quantity previously ordered
        result = getOrderQuantity(session_id, name);
// If "OK", then order is new
        if(result == "OK") {
            quantity = 1;
            sql_stmt = "insert into " + tableName + " values ('" + userid + "','" + session_id + "','" + name + "'," + quantity + "," + price + ")";         
        } 
// Else the order exists
        else if (Integer.parseInt(result) > 1) {
            Integer q = Integer.parseInt(result);
            q++;
            sql_stmt = "update " + tableName + " set quantity = " + q + "where PIZZAORDER = '" + name + "' and SESSION = '" + session_id + "'";
        } 
        else {
            result = "ERROR";
            return result;
        }
         
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
            stmt.execute(sql_stmt);
            stmt.execute("commit;");
            
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
    
    private String getOrderQuantity(String sessionId, String pizzaorder) {
        String tableName = "basket";
        conn = getConnection(dbURL);

        try
        {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select quantity from " + tableName + " where pizzaorder = '" + pizzaorder + "' and session = '" + sessionId + "'");
            if (stmt.getFetchSize() > 1) {
                result = "DUPLICATE_ROW";
            } else if (stmt.getFetchSize() == 0) {
                result = "OK";
            } else {
                while(rs.next())
                {
                    result = rs.getString(1);
                }
            }

            rs.close();
            stmt.close();
        } catch(SQLException sql) {
            sql.printStackTrace();
        }
        
        closeConnection(stmt, conn);
        return result;
    }
    
    public Object deleteOrder(String sessionId) {
        String tableName = "basket";
        conn = getConnection(dbURL);

        try
        {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("delete * from " + tableName + " where session = " + sessionId);

            result = "OK";
            
            rs.close();
            stmt.close();
        }

        catch(SQLException sql)
        {
            sql.printStackTrace();
        }
        
        closeConnection(stmt, conn);
        return result;
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
    
