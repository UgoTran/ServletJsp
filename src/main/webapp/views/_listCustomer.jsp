<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Customer Management Application</title>
</head>
<body>
<header>

</header>
<%-- Goto Add customer site--%>
<a href="/customer/add">Add new customer</a>

<table border="2">
    <tr>
        <th>ID</th>
        <th>Full Name</th>
        <th>Last Name</th>
        <th>First Name</th>
        <th>Phone</th>
        <th>Address line 1</th>
        <th>Address line 2</th>
        <th>City</th>
        <th>State</th>
        <th>Postal Code</th>
        <th>Country</th>
        <th>Refer EMP</th>
        <th>Credit Limit</th>
        <th>Birthday</th>
        <th>ProfilePhoto</th>
        <th>Action</th>
    </tr>

    <c:forEach var="cust" items="${listCustomer}">
        <tr>
            <td><c:out value="${cust.customerNumber}"/></td>
            <td>
                <a href="info?customerNumber=<c:out value='${cust.customerNumber}' />">
                    <c:out value="${cust.customerName}"/>
                </a>
            </td>
            <td><c:out value="${cust.contactLastName}"/></td>
            <td><c:out value="${cust.contactFirstName}"/></td>
            <td><c:out value="${cust.phone}"/></td>
            <td><c:out value="${cust.addressLine1}"/></td>
            <td><c:out value="${cust.addressLine2}"/></td>
            <td><c:out value="${cust.city}"/></td>
            <td><c:out value="${cust.state}"/></td>
            <td><c:out value="${cust.postalCode}"/></td>
            <td><c:out value="${cust.country}"/></td>
            <td><c:out value="${cust.salesRepEmployeeNumber}"/></td>
            <td><c:out value="${cust.creditLimit}"/></td>
            <td><c:out value="${cust.birthday}"/></td>
            <td>
                <div style="border: 2px solid hotpink; width: 45px; height: 45px;">
<%--                    check customerPhoto exist--%>
                    <c:choose>
<%--                        exist > show customer photo in disk--%>
                        <c:when test="${cust.profilePhotoName != null}">
                            <span style="display: none"><c:out value="${cust.profilePhotoName}"/></span>
<%--                             concat:photoPath & cust.profilePhotoName--%>
<%--                            result: src= http://localhost:8080/show/profilephoto/e45fa138-3c9d-43f1-a661-8ebe3327343c.jpg
                                photoPath: http://localhost:8080/show/profilephoto/
                                ust.profilePhotoName: e45fa138-3c9d-43f1-a661-8ebe3327343c.jpg
--%>
                            <img src="${photoPath}${cust.profilePhotoName}"
                                 id="photoOutput"
                                 alt="${cust.customerName} profile photo"
                                 height="44" width="44">
                        </c:when>
<%--                        not exist > show default photo in webapp--%>
                        <c:otherwise>
                            <span style="display: none"><c:out value="02-default.jpg"/></span>
                            <img src="/assets/image/02-default.jpg"
                                 id="photoOutput"
                                 alt="${cust.customerName} profile photo"
                                 height="44" width="44">
                        </c:otherwise>
                    </c:choose>
                </div>

            </td>
            <td>
<%--                goto customer edit site--%>
                <a href="info?customerNumber=<c:out value='${cust.customerNumber}' />">Edit</a>
<%--                do delete customer by id--%>
                <a href="delete?customerNumber=<c:out value='${cust.customerNumber}' />">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<%--<jsp:include page="../common/footer.jsp"></jsp:include>--%>
</body>
</html>
