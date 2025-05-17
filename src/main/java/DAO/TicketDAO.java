/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Ahmad
 */

import jakarta.servlet.ServletContext;
import Models.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private DBConnectionManager dbManager;

    public TicketDAO(ServletContext context) {
        dbManager = new DBConnectionManager(context);
    }

    public int createTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (ticket_type, travel_category, base_fare, final_fare, purchase_date, user_id, trip_id) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ticket.getTicketType());
            ps.setString(2, ticket.getTravelCategory());
            ps.setDouble(3, ticket.getBaseFare());
            ps.setDouble(4, ticket.getFinalFare());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, ticket.getUserId());
            ps.setInt(7, ticket.getTripId());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Ticket getTicketById(int ticketId) {
        Ticket t = null;
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ticketId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                t = new Ticket(
                    rs.getString("ticket_type"),
                    rs.getString("travel_category"),
                    rs.getDouble("base_fare"),
                    rs.getInt("user_id"),
                    rs.getInt("trip_id")
                );
                t.setTicketId(rs.getInt("ticket_id"));
                t.setFinalFare(rs.getDouble("final_fare"));
                t.setPurchaseDate(rs.getTimestamp("purchase_date").toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    public List<Ticket> getTicketsByUser(int userId) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE user_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(
                    rs.getString("ticket_type"),
                    rs.getString("travel_category"),
                    rs.getDouble("base_fare"),
                    rs.getInt("user_id"),
                    rs.getInt("trip_id")
                );
                t.setTicketId(rs.getInt("ticket_id"));
                t.setFinalFare(rs.getDouble("final_fare"));
                t.setPurchaseDate(rs.getTimestamp("purchase_date").toString());
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
