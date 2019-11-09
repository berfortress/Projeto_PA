/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author berna
 */
public enum TypeModel {
    AUTOMATIC,
    ITERATIVE;
    
    @Override
        public String toString(){
            switch(this){
                case AUTOMATIC:
                    return "Modelo Automático";
                case ITERATIVE:
                    return "Modelo Iterativo";
            }
            return "Unknown";
        }
}
