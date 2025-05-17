/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ahmad
 */
public class FareConfig {

    private static FareConfig instance;

   
    private Map<String, Double> baseFares;

    public FareConfig() {
        baseFares = new HashMap<>();
        loadConfigurations();
    }

    public static FareConfig getInstance() {
        if (instance == null) {
            instance = new FareConfig();
        }
        return instance;
    }

 
    private void loadConfigurations() {
        baseFares.put("One-Trip|City", 50.0);
        baseFares.put("One-Trip|Inter-city", 100.0);
        baseFares.put("Daily Pass|City", 200.0);
        baseFares.put("Daily Pass|Inter-city", 300.0);
        baseFares.put("Weekly Pass|City", 1200.0);
        baseFares.put("Weekly Pass|Inter-city", 1800.0);
        baseFares.put("Monthly Pass|City", 4000.0);
        baseFares.put("Monthly Pass|Inter-city", 6000.0);
    }


    public double getBaseFare(String ticketType, String travelCategory) {
        String key = ticketType + "|" + travelCategory;
        return baseFares.getOrDefault(key, 0.0);
    }

  
}