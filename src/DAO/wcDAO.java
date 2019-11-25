/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import models.PageTitle;

/**
 *
 * @author fabio
 */
public interface wcDAO {

    public void saveWC(PageTitle wc);

    public PageTitle loadWC(String pageTitleName);
}
