/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author fabio e bernardo
 */

public class LinkException extends Exception{
    /**
     * Excepção do Link
     * @param string 
     */
    public LinkException(String string) {
        super(string);
    }  
}
