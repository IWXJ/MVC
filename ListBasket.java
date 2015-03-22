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



public class ListBasket
{

    List<BasketObject>  thePizzas = new ArrayList<>();  // Pizzaobject is ONE pizza. This is an array of pizza objects

    public ListBasket() {
    }

    public List<BasketObject> getBasket(String sessionId) throws InterruptedException {
        String dbURL = "jdbc:derby://localhost:1527//home/peter/pizzabase";
        String tableName = "basket";
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
            ResultSet results = stmt.executeQuery("select customer, pizzaorder, quantity, price from " + tableName + " where SESSION = '" + sessionId + "'");

            while(results.next())
            {
                BasketObject bo = new BasketObject();

                bo.setName(results.getString(1));
                
                bo.setOrder(results.getString(2));
                
                bo.setQuantity(Integer.valueOf(results.getString(3)));
                bo.setPrice(Integer.valueOf(results.getString(4)));
                
                thePizzas.add(bo);
                
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
            
        return thePizzas;
    }
}

