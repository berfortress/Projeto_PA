/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import java.util.Stack;

/**
 *
 * @author fabio e bernardo
 */

public class CareTaker {
    
    public Stack<Memento> objMementos;

    /**
     * Construtor da classe CareTaker
     */
    public CareTaker() {
        objMementos = new Stack<>();
    }
    
    /**
     * Método que guarda um WebLink na stack
     * @param web 
     */
    public void saveState(WebLink web){
        Memento objMemento = web.createMemento();
        objMementos.push(objMemento);
    }
    
    /**
     * Método que remove um Weblink da stack
     * @param web 
     */
    public void restoreState(WebLink web){
        if(objMementos.isEmpty()) return;
        Memento objMemento = objMementos.pop(); 
        web.setMemento(objMemento);
    }
}
