
package Entity;

/**
 *
 * @author peter
 */
public class BasketObject {
    
      
    String username ;
    String name ;
    Integer quantity;  
    Integer price;

        @Override
        public String toString()
        {
            return "A pizza : " + this.getName() + " " + this.getOrder()  + " " + Integer.valueOf(this.getQuantity()) + " " + Integer.valueOf(this.getPrice());
        }

        /**
         * @return the name
         */
        public String getName() {
            return username;
        }

      
        public void setName(String username) {
            this.username = username;
        }


        public String getOrder() {
            return name;
        }

        public void setOrder(String order) {
            this.name = order;
        }
                
        public Integer getQuantity() {
            return quantity;
        }
        
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getPrice() {
            return price;
        }
        
        public void setPrice(Integer price) {
            this.price = price;
        }

}
