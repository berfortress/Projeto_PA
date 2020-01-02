/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author fabio e bernardo
 */

 public interface Originator {
    
    public Memento createMemento();
    public void setMemento(Memento savedState);
    
}
