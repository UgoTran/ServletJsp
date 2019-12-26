<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>HiveTech</title>
    <link rel="shortcut icon" href="#">
    <script src="/assets/js/handlePhoto.js"></script>
</head>
<body>
<h1>Customer Management</h1>
<a href="/customer/list">List All Customer</a>
<a href="/index.jsp">Go to home page</a>

<%-- check action > fun add--%>
<c:if test="${requestAction == 'add'}">
    <form action="/customer/add" method="post" enctype="multipart/form-data">
</c:if>

<%--        check selectedCustomer is existed > fun edit--%>
<c:if test="${selectedCustomer != null}">
    <form action="/customer/edit" method="post" enctype="multipart/form-data">
</c:if>
    <%--        handle upload profilephoto--%>
    <table id="photoDataUpload">
        <tr>
            <th>
                <input type="file" name="imgUpload" accept="image/*" onchange="loadFile(event)"/>
            </th>
        </tr>
        <tr>
            <td>
                <div style="border: 1px solid deeppink; width: 11.2rem; height: 11.2rem;">
                    <c:choose>
                        <%--                        exist > show customer photo in disk--%>
                        <c:when test="${selectedCustomer.profilePhotoName != null}">
                            <span style="display: none"><c:out value="${selectedCustomer.profilePhotoName}"/></span>
                            <%--                             concat:photoPath & selectedCustomer.profilePhotoName--%>
                            <%--                            result: src= http://localhost:8080/show/profilephoto/e45fa138-3c9d-43f1-a661-8ebe3327343c.jpg
                                                            photoPath: http://localhost:8080/show/profilephoto/
                                                            ust.profilePhotoName: e45fa138-3c9d-43f1-a661-8ebe3327343c.jpg
                            --%>
                            <img src="${photoPath}${selectedCustomer.profilePhotoName}"
                                 id="photoOutput"
                                 alt="${selectedCustomer.customerName} profile photo">
                        </c:when>
                        <%--                        not exist > show default photo in webapp--%>
                        <c:otherwise>
                            <span style="display: none"><c:out value="02-default.jpg"/></span>
                            <img src="/assets/image/02-default.jpg"
                                 id="photoOutput"
                                 alt="${selectedCustomer.customerName} profile photo">
                        </c:otherwise>
                    </c:choose>
                </div>
                <input type="submit" value="Upload Profile Photo"/>
            </td>
        </tr>
    </table >
<%--        Customer details--%>
        <table id="textData">
            <tr>
                <th>ID</th>
                <td>
                     <input type="text" name="customerNumber" size="45" readonly="true" style="border: pink"
                           value="<c:out value="${selectedCustomer.customerNumber}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Full Name</th>
                <td>
                    <input type="text" name="customerName" size="45"
                           value="<c:out value="${selectedCustomer.customerName}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Contact Last Name</th>
                <td>
                    <input type="text" name="contactLastName" size="45"
                           value="<c:out value="${selectedCustomer.contactLastName}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Contact First Name</th>
                <td>
                    <input type="text" name="contactFirstName" size="45"
                           value="<c:out value="${selectedCustomer.contactFirstName}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Phone</th>
                <td>
                    <input type="text" name="phone" size="45"
                           value="<c:out value="${selectedCustomer.phone}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Address line 1</th>
                <td>
                    <input type="text" name="addressLine1" size="45"
                           value="<c:out value="${selectedCustomer.addressLine1}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Address line 2</th>
                <td>
                    <input type="text" name="addressLine2" size="45"
                           value="<c:out value="${selectedCustomer.addressLine2}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>City</th>
                <td>
                    <input type="text" name="city" size="45"
                           value="<c:out value="${selectedCustomer.city}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>State</th>
                <td>
                    <input type="text" name="state" size="45"
                           value="<c:out value="${selectedCustomer.state}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Postal Code</th>
                <td>
                    <input type="text" name="postalCode" size="45"
                           value="<c:out value="${selectedCustomer.postalCode}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Country</th>
                <td>
                    <input type="text" name="country" size="45"
                           value="<c:out value="${selectedCustomer.country}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Sales Refer EMP</th>
                <td>
                    <input type="text" name="salesRepEmployeeNumber" size="45"
                           value="<c:out value="${selectedCustomer.salesRepEmployeeNumber}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Credit Limit</th>
                <td>
                    <input type="text" name="creditLimit" size="45"
                           value="<c:out value="${selectedCustomer.creditLimit}"/>"
                    />
                </td>
            </tr>
            <tr>
                <th>Birthday</th>
                <td>
                    <input type="text" name="birthday" size="45"
                           value="<c:out value="${selectedCustomer.birthday}"/>"
                    />
                </td>
            </tr>
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${requsetAcction == 'add'}">
                            <input type="submit" value="ADD"/>
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="SAVE"/>
                        </c:otherwise>
                    </c:choose>

                    <input type="reset" value="Back"/>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>