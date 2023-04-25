<%@ page import="modele.*" %>
<h1>EMP-ALL</h1>
<% Emp employe = (Emp) request.getAttribute("list_emp") ;
out.println(employe.getNom()); %>