/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.helper;

import java.util.ArrayList;
import java.util.List;
import org.vasanti.dao.ImageDAOFactory;
import org.vasanti.dao.InsertImageDAO;
import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
public class InsertImageInterface {

    public int InsertImage(bnimagesbn images) {
        InsertImageDAO imgdao = ImageDAOFactory.InsertImage();
        int count = 0;
        try {
            count = imgdao.InsertImage(images);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public int DeleteImage(bnimagesbn images) {
        int count = 0;
        InsertImageDAO imgdao = ImageDAOFactory.DeleteImage();
        try {
            count = imgdao.DeleteImage(images);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public int GetImagesCount(String postid) {
        int count = 0;
        InsertImageDAO imgcount = ImageDAOFactory.GetImagesCount(postid);
        count = imgcount.GetImagesCount(postid);
        return count;
    }

    public List<bnimagesbn> GetAllImages(String postid) {
        ArrayList<bnimagesbn> images = new ArrayList<>();
        InsertImageDAO getallimages = ImageDAOFactory.GetAllImages(postid);
        try {
            images = (ArrayList<bnimagesbn>) getallimages.GetAllImages(postid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return images;
    }
}
