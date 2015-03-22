<%-- 
    Document   : menu
    Created on : 10-03-2015, 17:53:52
    Author     : OBS
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<jsp:useBean id= "pizzas" scope= "request" class= "Pizzas"/>
<jsp:useBean id="pizza" class="Pizza" scope="request"/>--%>

<%! int i = 0;%>
<%@ page language="java" contentType="text/html; charset=windows-1252"
    pageEncoding="windows-1252"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <div class="rightMenu">
        <p><h1>Menu</h1>
        <p>Select from the list below.
        <p>	
            <table border="1" cellpadding="10" cellspacing="20">
                <tr>
                    <th>Order</th>
                    <th><a href="/LaPizzeria/menu.jsp?sortOrder=name">Item</a></th>
                    <th><a href="/LaPizzeria/menu.jsp?sortOrder=price">Price</a></th>
                </tr>
                <tr>
                    <td>${item}</td>
                    <td></td>
                    <td></td>
                </tr>
                <!--<select name="pizza">-->
                    <c:forEach items="${pizzaList}" var="pizza">
                        <tr>
                            <td>
                                <form method="POST" action="order.do?item=${pizza.name}&price=${pizza.price}">
                                <input type="submit" value="Order"></form>
                            </td>
                            <td>${pizza.desc}</td>
                            <td>${pizza.price}</td>
                        </tr>							
                    </c:forEach>
                <!--</select>-->
                    
            </table>
            <c:if test="${currentPage != 1}">
                <a href="menu.do?page=${currentPage - 1}">Previous</a>
            </c:if>
            <table cellpadding="10" cellspacing="20">
                <tr>
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="/LaPizzeria/menu.jsp?page=${i}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </table>
            <c:if test="${currentPage lt noOfPages}">
                <a href="/LaPizzeria/menu.jsp?page=${currentPage + 1}">Next</a>
            </c:if>
    </div>
