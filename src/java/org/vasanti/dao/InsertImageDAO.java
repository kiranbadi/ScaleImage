/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.dao;

import java.util.List;
import org.vasanti.model.bnawsbn;
import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
public interface InsertImageDAO {

    int InsertImage(bnimagesbn images);

    int DeleteImage(bnimagesbn images);

    int GetImagesCount(String postid);

    List<bnimagesbn> GetAllImages(String postid);
    
    boolean InsertAWSForm(bnawsbn aws);
}
