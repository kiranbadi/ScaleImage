<%-- 
    Document   : TestDB
    Created on : Oct 31, 2015, 9:39:24 AM
    Author     : Kiran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>
            <sql:query var="employees" dataSource="jdbc/TestDB">
select id,age,first,last from employees
</sql:query>
        </h1>
        <h2>Results</h2>

<c:forEach var="row" items="${employees.rows}">
    id ${row.id}<br/>
    age ${row.age}<br/>
    first${row.first}<br/>
    last ${row.last}<br/>
</c:forEach>

    </body>
</html>
