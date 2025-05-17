<%-- 
    Document   : homepage
    Created on : 16 Apr 2025, 10:12:08 PM
    Author     : Ahmad
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Models.FareConfig" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Online Bus Ticketing - Home</title>
  <link rel="icon" href="images/bus_logo.jpg" type="image/x-icon">
  <link rel="stylesheet" href="CSS/header.css">
  <link rel="stylesheet" href="CSS/homepage.css">
  <link rel="stylesheet" href="CSS/footer.css">
  
  <style>
    .hero-section {
      width: 100%;
      height: 337px;
      background: url('<c:url value="/images/bus2.jpg"/>') no-repeat center center;
      background-size: cover;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      text-shadow: 0 0 5px rgba(0,0,0,0.7);
    }
    .inline-form { display: inline; margin-left: 0.5rem; }
  </style>
</head>
<body>
  <jsp:useBean id="fareConfig" class="Models.FareConfig" scope="application" />
  <jsp:include page="Header.jsp" />

  <div class="hero-section">
    <h2>Welcome to Our Online Bus Ticketing System</h2>
  </div>

  <jsp:include page="tripSearch.jsp" />

  <section class="trips-section">
    <h3>Available Trips</h3>
    <div class="trips-container">
      <c:forEach var="trip" items="${allTrips}">
        <div class="trip-card">
          <h4>${trip.origin} → ${trip.destination}</h4>
          <p>
            ${trip.travelType} Travel |
            Departs ${trip.departureTime} |
            Arrives ${trip.arrivalTime}
          </p>
          <p>Seats left: ${trip.availableSeats}</p>

          <a href="${pageContext.request.contextPath}/FareEstimationController?tripId=${trip.tripId}"
             class="btn">Estimate Fare</a>

          <form action="${pageContext.request.contextPath}/TicketController"
                method="post" class="inline-form">
            <input type="hidden" name="action"     value="purchase"/>
            <input type="hidden" name="tripId"      value="${trip.tripId}"/>
            <input type="hidden" name="ticketType"  value="One-Trip"/>
            <input type="hidden" name="travelType"  value="${trip.travelType}"/>
            <input type="hidden" name="finalFare"
                   value="${fareConfig.getBaseFare('One-Trip', trip.travelType)}"/>
            <button type="submit" class="btn secondary">Buy One-Trip</button>
          </form>
        </div>
      </c:forEach>

      <c:if test="${empty allTrips}">
        <p>No trips available at the moment.</p>
      </c:if>
    </div>
  </section>

  <jsp:include page="Footer.jsp" />
</body>
</html>
