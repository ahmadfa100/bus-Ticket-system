<%-- 
    Document   : tripSearch
    Created on : 16 Apr 2025, 2:33:00 PM
    Author     : Ahmad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Models.FareConfig" %>
<jsp:useBean id="fareConfig" class="Models.FareConfig" scope="application" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search & View Trips</title>
    <!-- Consolidated CSS: search form & trip cards -->
    <link rel="stylesheet" href="<c:url value='/CSS/tripSearch.css'/>">
  <link rel="stylesheet" href="CSS/tripCard.css">
</head>
<body>
    <%--<jsp:include page="Header.jsp" />--%>

    <div class="container">
        <h1>Search Trips</h1>
        <form class="search-form" action="TripController" method="GET">
            <input type="hidden" name="action" value="search">
            <input type="text" name="fromLocation" placeholder="From" required>
            <input type="text" name="toLocation" placeholder="To" required>
            <input type="date" name="travelDate" required>
            <select name="travelType" required>
                <option value="City">City</option>
                <option value="Inter-city">Inter-city</option>
            </select>
            <button type="submit" class="search-button">Search Trips</button>
        </form>

        <c:if test="${not empty tripList}">
            <h2>Available Trips</h2>
            <div class="trips-container">
                <c:forEach var="trip" items="${tripList}">
                    <div class="trip-card">
                        <div class="trip-info">
                            <div class="route">
                                <span class="label">From:</span> <span class="value">${trip.origin}</span>
                                <span class="arrow">→</span>
                                <span class="label">To:</span> <span class="value">${trip.destination}</span>
                            </div>
                            <div class="times">
                                <div><span class="label">Departure:</span> <span class="value">${trip.departureTime}</span></div>
                                <div><span class="label">Arrival:</span> <span class="value">${trip.arrivalTime}</span></div>
                            </div>
                        </div>
              <div class="trip-actions">
    <form action="${pageContext.request.contextPath}/FareEstimationController" method="get">
      <input type="hidden" name="tripId" value="${trip.tripId}" />
      <button type="submit" class="btn estimate">Estimate Fare</button>
    </form>

    <form action="${pageContext.request.contextPath}/TicketController" method="post">
      <input type="hidden" name="action"      value="purchase" />
      <input type="hidden" name="tripId"       value="${trip.tripId}" />
      <input type="hidden" name="ticketType"   value="One-Trip" />
      <input type="hidden" name="travelType"   value="${trip.travelType}" />
      <input type="hidden" name="finalFare"
             value="${fareConfig.getBaseFare('One-Trip', trip.travelType)}" />
      <button type="submit" class="btn buy">Buy One-Trip</button>

                            </form>
      
                        </div>
                    </div>
                </c:forEach>
            </div>
       </c:if>
<c:if test="${empty sessionScope.currentUser}">
  <a href="login.jsp" class="btn buy">Login to Purchase</a>
</c:if>
    </div>

    <%--<jsp:include page="Footer.jsp" />--%>
</body>
</html>