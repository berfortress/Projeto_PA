/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author fabio
 */
public class WcDAOFactory {
    public static wcDAO createWcDAO(String type, String basePath){
        switch(type){
            case "serialization": return new wcSerializationDAO(basePath);
            case "onejson": return new wcJsonDAO(basePath);
            default: throw new IllegalArgumentException("Este tipo não existe");
        }
    }
}
