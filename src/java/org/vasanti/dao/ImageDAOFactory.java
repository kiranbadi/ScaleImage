/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.dao;

/**
 *
 * @author Kiran
 */
public class ImageDAOFactory {
    public static InsertImageDAO InsertImage(){
        return new  InsertImageDAOImpl();
    }
    public static InsertImageDAO DeleteImage(){
        return new  InsertImageDAOImpl();
    }
}
