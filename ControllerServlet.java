package controller;

import Entity.ListBasket;
import Entity.AddToBasket;
import Entity.Login;
import Entity.ListPizzas;
import Entity.ConfirmOrder;
import Entity.CreateCustomer;
import Entity.PizzaObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author OBS
 */
@WebServlet(name="ControllerServlet",
            loadOnStartup = 1,
            urlPatterns = {"/cart.jsp",
                           "/checkout.jsp",
                           "/index.jsp",
                           "/menu.jsp",
                           "/login.jsp",
                           "/menuadmin.jsp",
                           "/registeruser.jsp",
                           "/login.do",
                           "/registerUser.do",
                           "/order.do",
                           "/deleteFromList.do",
                           "/addItemToList.do",
                           "/menuSort.do"
            })
public class ControllerServlet extends HttpServlet {
        PrintWriter pw;
        String userPath;
        
        String pageIndex = "/index.jsp";
        String pageMenu = "/menu.jsp";
        String pageCart = "/cart.jsp";
        String pageLogin = "/login.jsp";
        String pageMenuAdmin = "/menuadmin.jsp";
        String pageRegisterUser = "/registeruser.jsp";
          
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        userPath = request.getRequestURI().substring(request.getContextPath().length());
        
        switch (userPath) {
            case "/cart.jsp":           showCart(request, response); break;
            case "/checkout.jsp":       showCheckout(request, response); break;
            case "/menu.jsp":           showMenu(request, response); break;
            case "/menuadmin.jsp":      showMenuadmin(request, response); break;
            case "/registeruser.jsp":   break;
            case "/login.jsp":          showLogin(request, response); break;
            case "/login.do":
                doLoginUser(request, response);
                break;
            case "/order.do":           doAddOrder(request, response); break;
            case "/deleteFromList.do":  
                doDeleteItemFromList(request, response); 
                break;
            case "/registerUser.do":    
                doRegisterUser(request, response); 
                break;
            case "/addItemToList.do":   
                doAddItemToList(request, response); 
                break;
            case "/menuSort.do":        doSortMenu(request, response); break;
            case "/index.jsp":
            default:                    userPath = "/index.jsp"; break;
        }
        String url = "/WEB-INF/jsp" + userPath;

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void showCart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        if (sessionId == null) {
            userPath = pageLogin;
            return;
        } 
           
        Object returnObject = null;

        ListBasket lb = new ListBasket();
        try {
            returnObject = lb.getBasket(sessionId);

        }  catch (InterruptedException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       
// TO DO: 
// Kode til at håndtere bladring
        
        session.setAttribute("cartList", returnObject);
        userPath = pageCart;
    }    

    private void showCheckout(HttpServletRequest request, HttpServletResponse response) {
        Object returnObject = null;
        String session_id = request.getRequestedSessionId();

        ConfirmOrder co = new ConfirmOrder(session_id);
        try {
            returnObject = co.add();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

// TO DO: 
// Kode til at håndtere bladring
        HttpSession session = request.getSession();
        session.setAttribute("cartList", returnObject);
        userPath = pageCart;
    }    

    private void showMenu(HttpServletRequest request, HttpServletResponse response) {
        String name = null;
        String desc = null;
        Double price = null;   
        String sortOrder = "";
        Object pizzaList = null;

        if(request.getParameter("sortOrder") != null) {
            sortOrder = request.getParameter("sortOrder");
        } else {
            sortOrder = "name";
        }

        ListPizzas lp = new ListPizzas();
        try {
            pizzaList = lp.getPizza(sortOrder);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
// TO DO: 
// Kode til at håndtere bladring
        HttpSession session = request.getSession();
        session.setAttribute("pizzaList", pizzaList);
        userPath = pageMenu;
    }    

    private void showMenuadmin(HttpServletRequest request, HttpServletResponse response) {
        showMenu(request, response);
    }    

    private void showLogin(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginError");
    }    

    private void doAddOrder(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        userPath = pageMenu;
        
        String sessionId = (String) session.getAttribute("sessionId");
        if (sessionId == null) {
            userPath = pageLogin;
            return;
        } 
        
        String user = (String) session.getAttribute("userId");
        if (user == null) {
            user = "Dummy";
        }
        Integer quantity = 1;
        String name = request.getParameter("item");
        Double pr = Double.parseDouble(request.getParameter("price"));
        Integer price = pr.intValue();
        
        AddToBasket ab = new AddToBasket();
        Object result = ab.add(user, sessionId, name, quantity, price);
        if (result == "ERROR") {
            ab.deleteOrder(sessionId);
            userPath = pageLogin;
        }
        
        session.setAttribute("sessionId", sessionId);
    }
    
    private void doDeleteItemFromList(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String itemName = request.getParameter("item");
        if (itemName == null || itemName == "") {
            return;
        }

//        for (Iterator<PizzaObject> iterator = pizzaList.iterator(); iterator.hasNext();) {
//            PizzaObject next = iterator.next();
//            if (next.getName().equalsIgnoreCase(itemName)) {
//                pizzaList.remove(next);
//            }
//        }
        
//        session.setAttribute("pizzaList", pizzaList);
        userPath = pageMenuAdmin;
    }
    
    private void doRegisterUser(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        Integer zipcode = Integer.parseInt(request.getParameter("zipcode"));
        String city = request.getParameter("city");
        Integer phone = Integer.parseInt(request.getParameter("phone"));

        CreateCustomer cust = new CreateCustomer();
                
        Object result = cust.addCustomer(email, password, name, address, city, zipcode, phone);
        if (result == "OK") {
            userPath = pageMenu;
        } else if (result == "DUPLICATE") {
            userPath = pageLogin;
        }
        else {
            userPath = pageRegisterUser;
        }
    }
    
    private void doLoginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Object returnObject = null;
            
        String userid = request.getParameter("email");
        String password = request.getParameter("password");
        Login login = new Login();
        try {
            returnObject = login.logintoApplication(userid,password);
        } catch (SQLException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if ( returnObject=="OK" ) {
            String session_Id = session.getId();
            session.setAttribute("sessionId", session_Id);
            session.setAttribute("userId", userid);
            showMenu(request, response);
            session.removeAttribute("loginError");
        } else {
            returnObject = "loginError";
            session.setAttribute("loginError", returnObject);
            userPath = pageLogin;
        }
    }
    
    private void doAddItemToList(HttpServletRequest request, HttpServletResponse response) {
//        String itemName = request.getParameter("name");
//        String itemDescription = request.getParameter("description");
//        Double itemPrice = 0.00;
//        
//        try {
//            itemPrice = Double.parseDouble(request.getParameter("price"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//        
//        if (!(itemName == null || itemName == "" || itemDescription == null || itemDescription == "")) {
//            return;
//        }
//        
//        PizzaObject p = new PizzaObject(itemName, itemDescription, itemPrice);
//        pizzaList.add(p);
//
//        HttpSession session = request.getSession();
//        session.setAttribute("pizzaList", pizzaList);
//        userPath = pageMenuAdmin;
    }
    
    private void doSortMenu(HttpServletRequest request, HttpServletResponse response) {
        showMenu(request, response);
    }
 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
