<%@ page import="modele.*" %>
<h1>DISPLAY</h1>
<% Emp employe = (Emp) request.getAttribute("list_emp") ;
out.println(employe.getnom());
out.println(employe.getage()); %>