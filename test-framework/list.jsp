<%@ page import="modele.*" %>
<%  Emp[] list = new Emp[3];
    list[0] = new Emp(20, "Employe1");
    list[1] = new Emp(30, "Employe2");
    list[2] = new Emp(40, "Employe3");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire</title>
</head>
<body>
    
    <h1>Ajout employe</h1>
    <% for(int i=0; i<list.length; i++){ %>
        <h4><a href="/testfw/emp_by_id.do?id=<%= list[i].getage()%>"> <%= list[i].getnom()%> </a></h4>
    <% } %>

    <div><a href="/testfw/loginadmin.do">Login Admin</a></div>
    <div><a href="/testfw/login.do">Login</a></div>

</body>
</html>