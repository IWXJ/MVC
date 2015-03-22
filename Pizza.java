package Entity;

/**
 *
 * @author peter
 */


import java.util.ArrayList;

/**
 * Pizza entity. Implements interface from interface class PizzaInterface.
 * @author Peter O'Neil
 */

public class Pizza implements PizzaInterface {
     String name;
     String desc;
     Double price;


          // constructor
    public Pizza(String name, String desc, Double price){         // instantiate the object with all variables
        name = name;
        desc = desc;
        price = price;
    }
    
      
    public String getPizza(String name) {
        return "{ " + this.name + ", " + this.desc + "," + this.price + "}";
    }
    
    public void setPizza(String name, String desc, Double price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public boolean equals(Pizza p) {
        return ( (p.name == this.name) 
                && (p.desc == this.desc) 
                && (p.price == this.price) ) ;
    };
    
    public void addPizza(String name, String desc, Double price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        theArray.add(this);
    }

   
 //   return sum/theArray.size();

    @Override
    public String getPizzas() {
        return null;
    }

    @Override
    public void setPizza(String name) {
        
    }
}
