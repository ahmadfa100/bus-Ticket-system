<%-- 
    Document   : ticketHistory
    Created on : 14 Apr 2025, 3:54:17 PM
    Author     : Ahmad
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Your Ticket Orders</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/ticketHistory.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/footer.css" />
</head>
<body>
  <jsp:include page="Header.jsp" />

  <div class="history-container">
    <h2>Your Ticket Orders</h2>

    <c:if test="${empty tickets}">
      <p>You haven’t purchased any tickets yet. 
         <a href="homepage.jsp">Search trips</a> to buy one.</p>
    </c:if>

    <c:if test="${not empty tickets}">
      <div class="cards-container">
        <c:forEach var="t" items="${tickets}">
          <div class="ticket-card">
            <h3>Ticket #<c:out value="${t.ticketId}"/></h3>
            <p><strong>Trip ID:</strong> <c:out value="${t.tripId}"/></p>
            <p><strong>Type:</strong>      <c:out value="${t.ticketType}"/></p>
            <p><strong>Category:</strong>  <c:out value="${t.travelCategory}"/></p>
            <p><strong>Base Fare:</strong> $<c:out value="${t.baseFare}"/></p>
            <p><strong>Final Fare:</strong> $<c:out value="${t.finalFare}"/></p>
            <p><strong>Purchased:</strong> <c:out value="${t.purchaseDate}"/></p>
          </div>
        </c:forEach>
      </div>
    </c:if>
  </div>

  <jsp:include page="Footer.jsp" />
</body>
</html>
