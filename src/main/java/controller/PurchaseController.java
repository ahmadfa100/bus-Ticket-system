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
import Models.TicketPurchase;
import Models.User;
import Models.Trip;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@WebServlet(name = "PurchaseController", urlPatterns = {"/PurchaseController"})
public class PurchaseController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        User user = (User) session.getAttribute("currentUser");

        int tripId = Integer.parseInt(request.getParameter("tripId"));
        String ticketType = request.getParameter("ticketType");
        String travelCat = request.getParameter("travelType");
        double finalFare = Double.parseDouble(request.getParameter("finalFare"));

        double baseFare = FareConfig.getInstance().getBaseFare(ticketType, travelCat);
        Ticket ticket = new Ticket(ticketType, travelCat, baseFare,
                                   user.getUserId(), tripId);
        ticket.setFinalFare(finalFare);
        int newTicketId = new TicketDAO(getServletContext()).createTicket(ticket);

        TicketPurchase purchase = new TicketPurchase(
            user.getUserId(), newTicketId,
            LocalDateTime.now(), finalFare
        );
        new TicketPurchaseDAO(getServletContext()).recordPurchase(purchase);

        TripDAO tripDao = new TripDAO(getServletContext());
        Trip trip = tripDao.getTripById(tripId);
        tripDao.updateSeats(tripId, trip.getAvailableSeats() - 1);

        List<Ticket> history = new TicketDAO(getServletContext())
                                  .getTicketsByUser(user.getUserId());
        request.setAttribute("tickets", history);
        request.getRequestDispatcher("/ticketHistory.jsp")
               .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/homepage.jsp");
    }
}
