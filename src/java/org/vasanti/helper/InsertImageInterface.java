/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.helper;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vasanti.dao.ImageDAOFactory;
import org.vasanti.dao.InsertImageDAO;
import org.vasanti.model.bnawsbn;
import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
public class InsertImageInterface {

    static final Logger logger = LogManager.getLogger(InsertImageInterface.class.getName());

    public int InsertImage(bnimagesbn images) {
        InsertImageDAO imgdao = ImageDAOFactory.InsertImage();
        int count = 0;
        try {
            count = imgdao.InsertImage(images);
        } catch (Exception ex) {
            logger.error("Logging Exception ", ex);
        }
        return count;
    }

    public int DeleteImage(bnimagesbn images) {
        int count = 0;
        InsertImageDAO imgdao = ImageDAOFactory.DeleteImage();
        try {
            count = imgdao.DeleteImage(images);
        } catch (Exception ex) {
            logger.error("Logging Exception ", ex);
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
            logger.error("Logging Exception ", ex);
        }
        return images;
    }

    public boolean InsertAWSForm(bnawsbn aws) {
        boolean status = false;
        InsertImageDAO awsdao = ImageDAOFactory.InsertAWSForm(aws);
        status = awsdao.InsertAWSForm(aws);
        return status;
    }
}
