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



public class ListPizzas
{

    /**
     * @return the thePizzas
     */
    public List<PizzaObject> getThePizzas() {
        return thePizzas;
    }

    /**
     * @param thePizzas the thePizzas to set
     */
    public void setThePizzas(List<PizzaObject> thePizzas) {
        this.thePizzas = thePizzas;
    }
    
    public List<PizzaObject>  thePizzas = new ArrayList<>();  // Pizzaobject is ONE pizza. This is an array of pizza objects

    public ListPizzas() {
    }

    public List<PizzaObject> getPizza(String orderBy) throws InterruptedException {
        String dbURL = "jdbc:derby://localhost:1527//home/peter/pizzabase";
        String tableName = "pizza";
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
            ResultSet results = stmt.executeQuery("select * from " + tableName + " order by " + orderBy );

            while(results.next())
            {
                PizzaObject po = new PizzaObject();

                po.setName(results.getString(1));
                po.setDesc(results.getString(2));
                po.setPrice((Double) results.getDouble(3));
                getThePizzas().add(po);
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
            
        return getThePizzas();
    }
}
