/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.dao;

import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
public interface InsertImageDAO {
    void InsertImage(bnimagesbn images);
    void DeleteImage(bnimagesbn images);
}
