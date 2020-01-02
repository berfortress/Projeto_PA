/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import models.Website;

/**
 *
 * @author fabio e bernardo
 */
public interface DAO {

    public void saveWC(Website wc);

    public Website loadWC(String pageTitleName);
}
