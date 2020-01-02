/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author fabio e bernardo
 */
public enum Criteria {
    
    DISTANCE, 
    COST;

    public String getUnit() {
        switch(this) {
            case COST: return "€";
            case DISTANCE: return "Links";
        }
        return "Unknown";
    }
}
