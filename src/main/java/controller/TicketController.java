/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.TicketDAO;
import DAO.TicketPurchaseDAO;
import DAO.TripDAO;
import Models.FareConfig;
import Models.Ticket;
import Models.TicketFactory;
import Models.TicketPurchase;
import Models.Trip;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name="TicketController", urlPatterns={"/TicketController"})
public class TicketController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("history".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("currentUser") == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            User user = (User) session.getAttribute("currentUser");
            List<Ticket> history = new TicketDAO(getServletContext())
                                       .getTicketsByUser(user.getUserId());
            request.setAttribute("tickets", history);
            request.getRequestDispatcher("/ticketHistory.jsp")
                   .forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/homepage.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("estimate".equalsIgnoreCase(action)) {
            handleEstimate(request, response);
        }
        else if ("purchase".equalsIgnoreCase(action)) {
            handlePurchase(request, response);
        }
        else {
            doGet(request, response);
        }
    }

    private void handleEstimate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        User currentUser = (User) session.getAttribute("currentUser");

        String tripIdStr = request.getParameter("tripId");
        if (tripIdStr == null || tripIdStr.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "tripId is required");
            return;
        }
        int tripId = Integer.parseInt(tripIdStr);

        TripDAO tripDao = new TripDAO(getServletContext());
        Trip trip = tripDao.getTripById(tripId);
        if (trip == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trip not found");
            return;
        }

        String ticketType     = request.getParameter("ticketType");
        String travelCategory = request.getParameter("travelType");
        Ticket ticket = TicketFactory.createTicket(
                ticketType, travelCategory,
                currentUser.getUserId(), tripId
        );

        request.setAttribute("trip", trip);
        request.setAttribute("ticket", ticket);
        request.setAttribute("estimatedFare", ticket.getFinalFare());
        request.getRequestDispatcher("/fareEstimation.jsp")
               .forward(request, response);
    }

    private void handlePurchase(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        User user = (User) session.getAttribute("currentUser");

        int tripId        = Integer.parseInt(request.getParameter("tripId"));
        String ticketType = request.getParameter("ticketType");
        String travelCat  = request.getParameter("travelType");
        double finalFare  = Double.parseDouble(request.getParameter("finalFare"));

        double baseFare = FareConfig.getInstance().getBaseFare(ticketType, travelCat);
        Ticket ticket = new Ticket(ticketType, travelCat, baseFare,
                                   user.getUserId(), tripId);
        ticket.setFinalFare(finalFare);
        int newTicketId = new TicketDAO(getServletContext()).createTicket(ticket);

        TicketPurchase purchase = new TicketPurchase(
            user.getUserId(), newTicketId, LocalDateTime.now(), finalFare
        );
        new TicketPurchaseDAO(getServletContext()).recordPurchase(purchase);

        TripDAO tdao = new TripDAO(getServletContext());
        Trip trip = tdao.getTripById(tripId);
        tdao.updateSeats(tripId, trip.getAvailableSeats() - 1);

        List<Ticket> history = new TicketDAO(getServletContext())
                                  .getTicketsByUser(user.getUserId());
        request.setAttribute("tickets", history);
        request.getRequestDispatcher("/ticketHistory.jsp")
               .forward(request, response);
    }
}
