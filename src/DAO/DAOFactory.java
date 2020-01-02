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
public class DAOFactory {
    public static DAO createDAO(String type, String basePath){
        switch(type){
            case "serialization": return new DAOSerialization(basePath);
            case "onejson": return new DAOJson(basePath);
            case "file": return new DAOFile(basePath);
            default: throw new IllegalArgumentException("Este tipo não existe");
        }
    }
}
