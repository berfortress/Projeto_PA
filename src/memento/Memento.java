/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import java.util.List;
import models.Link;
import models.Website;

/**
 *
 * @author fabio e bernardo
 */

public interface Memento {
    public List<Link> MementoLinks();
    public Website MementoWebsite();
    public List<Website> MementoWebsites();
}
