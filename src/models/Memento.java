/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;

/**
 *
 * @author fabio e bernardo
 */

public interface Memento {
    public List<Link> MementoLinks();
    public Website MementoWebsite();
    public List<Website> MementoWebsites();
}
