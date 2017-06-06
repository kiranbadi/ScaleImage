/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public int InsertImage(bnimagesbn images) {
        String INSERTIMAGE = "INSERT INTO images"
                + "(COLPOSTID,COLIMAGENAME,COLTHUMBNAILNAME) VALUES"
                + "(?,?,?)";
        String GETIMAGECOUNT_AFTER_INSERT = "SELECT COUNT(*) AS Count FROM images WHERE COLPOSTID = ? ";
        Boolean status = false;
        int count = 0;
        try {
            conn = CreateConnection.getDSConnection();
            logger.info(INSERTIMAGE);
            PreparedStatement ps = conn.prepareStatement(INSERTIMAGE);
            ps.setString(1, images.getCOLPOSTID());
            ps.setString(2, images.getCOLIMAGENAME());
            ps.setString(3, images.getCOLTHUMBNAILNAME());
            int result = ps.executeUpdate();
            if (result > 0) {
                status = true;
            }
            if (status) {
                // return the count of images inserted
                ps = conn.prepareStatement(GETIMAGECOUNT_AFTER_INSERT);
                ps.setString(1, images.getCOLPOSTID());
                rs = ps.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("Count");
                }
            }
        } catch (SQLException ex) {
            logger.error("Insert failed for inserting images names in database", ex);
        }
        logger.info("count is " + count + " and status is " + status);
        return count;
    }

    @Override
    public int DeleteImage(bnimagesbn images) {
        String DELETEIMAGE = "DELETE FROM images WHERE COLIMAGENAME = ? and COLPOSTID = ? ";
        //     String DELETEIMAGE = "DELETE FROM images WHERE COLIMAGENAME = ?;";
        String GETIMAGECOUNT_AFTER_INSERT = "SELECT COUNT(*) AS Count FROM images WHERE COLPOSTID = ? ";
        Boolean status = false;
        int count = 0;
        try {
            conn = CreateConnection.getDSConnection();
            PreparedStatement ps = conn.prepareStatement(DELETEIMAGE);
            ps.setString(1, images.getCOLIMAGENAME());
            ps.setString(2, images.getCOLPOSTID());
            logger.info(ps);
            int result = ps.executeUpdate();
            if (result > 0) {
                status = true;
            }
            if (status) {
                // return the count of images inserted
                ps = conn.prepareStatement(GETIMAGECOUNT_AFTER_INSERT);
                ps.setString(1, images.getCOLPOSTID());
                rs = ps.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("Count");
                }
            }
            logger.info("Result of Delete Query " + result);
            logger.info("count is " + count + " and status is " + status);
        } catch (SQLException ex) {
            logger.error("Delete failed for deleting images names in database", ex);
        }
        return count;
    }

    @Override
    public int GetImagesCount(String postid) {
        int count = 0;
        try {
            String GETIMAGECOUNT = "SELECT COUNT(*) AS Count FROM images WHERE COLPOSTID = ? ";
            conn = CreateConnection.getDSConnection();
            PreparedStatement ps = conn.prepareStatement(GETIMAGECOUNT);
            ps.setString(1, postid);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("Count");
            }
        } catch (SQLException ex) {
            logger.error("Getting Images count from Database ", ex);
        }
        logger.info("COUNT OF IMAGES FROM DATABASE IS " + count);
        return count;
    }

    @Override
    public List<bnimagesbn> GetAllImages(String postid) {
        String GET_ALL_IMAGES = "SELECT *  FROM images WHERE COLPOSTID = ? ";
        ArrayList<bnimagesbn> images = new ArrayList<>();
        try {
            conn = CreateConnection.getDSConnection();
            PreparedStatement ps = conn.prepareStatement(GET_ALL_IMAGES);
            ps.setString(1, postid);
            rs = ps.executeQuery();
            while (rs.next()) {
                bnimagesbn imagelist = new bnimagesbn();
//                imagelist.setID(rs.getInt("COLID"));
                imagelist.setCOLIMAGENAME(rs.getString("COLIMAGENAME"));
                imagelist.setCOLTHUMBNAILNAME(rs.getString("COLTHUMBNAILNAME"));
                images.add(imagelist);
             }
        } catch (Exception ex) {
            logger.error("Logging Exception ex", ex);
        }

        return images;

    }
}
