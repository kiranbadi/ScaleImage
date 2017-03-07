/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.helper;

import org.vasanti.dao.ImageDAOFactory;
import org.vasanti.dao.InsertImageDAO;
import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
public class InsertImageInterface {
    
    public void InsertImage(bnimagesbn images) {
        InsertImageDAO imgdao = ImageDAOFactory.InsertImage();
        try {
            imgdao.InsertImage(images);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void DeleteImage(bnimagesbn images) {
        InsertImageDAO imgdao = ImageDAOFactory.DeleteImage();
        try {
            imgdao.DeleteImage(images);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
}
