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

public class WebLinkException extends Exception {
    /**
     * Excepção do WebLink
     * @param string 
     */
    public WebLinkException(String string) {
        super(string);
    }
    
}
