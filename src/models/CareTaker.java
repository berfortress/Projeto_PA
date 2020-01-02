/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Stack;

/**
 *
 * @author fabio e bernardo
 */

public class CareTaker {
    
    public Stack<Memento> objMementos;

    public CareTaker() {
        objMementos = new Stack<>();
    }
    
    public void saveState(WebLink web){
        Memento objMemento = web.createMemento();
        objMementos.push(objMemento);
    }
    
    public void restoreState(WebLink web){
        if(objMementos.isEmpty()) return;
        Memento objMemento = objMementos.pop(); 
        web.setMemento(objMemento);
    }
}
