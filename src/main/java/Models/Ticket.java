/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Ahmad
 */
public class Ticket {
    private int ticketId;
    private String ticketType;
    private String travelCategory; 
    private double baseFare;
    private double finalFare;
    private String purchaseDate;
    private int userId;
    private int tripId;
    
    
    public Ticket(String ticketType, String travelCategory, double baseFare, int userId, int tripId) {
        this.ticketType = ticketType;
        this.travelCategory = travelCategory;
        this.baseFare = baseFare;
        this.userId = userId;
        this.tripId = tripId;
    }
    
    public void setFinalFare(double finalFare) {
        this.finalFare = finalFare;
    }
    
    public double getFinalFare() {
        return finalFare;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public void setTravelCategory(String travelCategory) {
        this.travelCategory = travelCategory;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getTravelCategory() {
        return travelCategory;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getTripId() {
        return tripId;
    }
    
    
    
}
