/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Ahmad
 */
public class TicketFactory {
 public static Ticket createTicket(String ticketType, String travelCategory, int userId, int tripId) {
        double baseFare = FareConfig.getInstance().getBaseFare(ticketType, travelCategory);
        
        Ticket ticket = new Ticket(ticketType, travelCategory, baseFare, userId, tripId);
        
        FareCalculator calculator = new FareCalculator();
        
        calculator.addStrategy(new RegularFareStrategy());
        
        

        double finalFare = calculator.calculateFare(baseFare);
        ticket.setFinalFare(finalFare);
        calculator.clearStrategies();
        
        return ticket;
    }
}
