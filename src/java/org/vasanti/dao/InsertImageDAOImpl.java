/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
public class InsertImageDAOImpl implements InsertImageDAO {
    
    static final Logger logger = LogManager.getLogger(InsertImageDAOImpl.class.getName());
    java.sql.Connection conn = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    
    public InsertImageDAOImpl() {
        
    }
    
    @Override
    public void InsertImage(bnimagesbn images) {
        String INSERTIMAGE = "INSERT INTO images"
                + "(COLPOSTID,COLIMAGENAME,COLTHUMBNAILNAME) VALUES"
                + "(?,?,?)";
        
        try {
            conn = CreateConnection.getDSConnection();
            logger.info(INSERTIMAGE);
            PreparedStatement ps = conn.prepareStatement(INSERTIMAGE);
            ps.setString(1, images.getCOLPOSTID());
            ps.setString(2, images.getCOLIMAGENAME());
            ps.setString(3, images.getCOLTHUMBNAILNAME());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Insert failed for inserting images names in database", ex);
        }
    }
    
    @Override
    public void DeleteImage(bnimagesbn images) {
        String DELETEIMAGE = "DELETE FROM images WHERE COLIMAGENAME = ? and COLPOSTID = ? ";
        try {
            conn = CreateConnection.getDSConnection();
            PreparedStatement ps = conn.prepareStatement(DELETEIMAGE);
            ps.setString(1, images.getCOLPOSTID());
            ps.setString(2, images.getCOLIMAGENAME());
            logger.info(ps);
            int result = ps.executeUpdate();
            logger.info("Result of Delete Query" + result);
        } catch (SQLException ex) {
            logger.error("Delete failed for deleting images names in database", ex);
        }
    }
}
