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

class LoggerException extends RuntimeException {

    /**
     * Excepção do Logger
     * @param message 
     */
    public LoggerException(String message) {
        super(message);
    }
    
}