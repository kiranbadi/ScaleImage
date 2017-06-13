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
import org.vasanti.model.bnawsbn;
import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
public class InsertImageDAOImpl implements InsertImageDAO {

    static final Logger logger = LogManager.getLogger(InsertImageDAOImpl.class.getName());
    private static final String INSERTAWS_SQL = "INSERT INTO awsform (COLTXTHIDDEN,COLTXTNAME,COLTXTEMAIL,COLTXTDATE,COLTXTDATETIME,COLTXTCOLOR,COLTXTMONTH,COLTXTNUMBER,COLTXTURL,COLTXTTIME,COLTXTTEL,COLTXTSEARCH,COLTXTRANGE,\n"
            + "COLTXTPASSWORD,COLTXTTEXTAREA,COLTXTCHECKBOX,COLTXTRADIO,COLTXTSINGLESELECT,COLTEXTMULTISELECT,COLTXTWEEK) VALUES\n"
            + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            psmt = conn.prepareStatement(INSERTIMAGE);
            psmt.setString(1, images.getCOLPOSTID());
            psmt.setString(2, images.getCOLIMAGENAME());
            psmt.setString(3, images.getCOLTHUMBNAILNAME());
            int result = psmt.executeUpdate();
            if (result > 0) {
                status = true;
            }
            if (status) {
                // return the count of images inserted
                psmt = conn.prepareStatement(GETIMAGECOUNT_AFTER_INSERT);
                psmt.setString(1, images.getCOLPOSTID());
                rs = psmt.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("Count");
                }
            }
        } catch (SQLException ex) {
            logger.error("Insert failed for inserting images names in database", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    logger.error("Logging SQL Exception ", ex);
                }
                if (psmt != null) {
                    try {
                        psmt.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
            }
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
            psmt = conn.prepareStatement(DELETEIMAGE);
            psmt.setString(1, images.getCOLIMAGENAME());
            psmt.setString(2, images.getCOLPOSTID());
            logger.info(psmt);
            int result = psmt.executeUpdate();
            if (result > 0) {
                status = true;
            }
            if (status) {
                // return the count of images inserted
                psmt = conn.prepareStatement(GETIMAGECOUNT_AFTER_INSERT);
                psmt.setString(1, images.getCOLPOSTID());
                rs = psmt.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("Count");
                }
            }
            logger.info("Result of Delete Query " + result);
            logger.info("count is " + count + " and status is " + status);
        } catch (SQLException ex) {
            logger.error("Delete failed for deleting images names in database", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    logger.error("Logging SQL Exception ", ex);
                }
                if (psmt != null) {
                    try {
                        psmt.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
            }
        }
        return count;
    }

    @Override
    public int GetImagesCount(String postid) {
        int count = 0;
        try {
            String GETIMAGECOUNT = "SELECT COUNT(*) AS Count FROM images WHERE COLPOSTID = ? ";
            conn = CreateConnection.getDSConnection();
            psmt = conn.prepareStatement(GETIMAGECOUNT);
            psmt.setString(1, postid);
            rs = psmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("Count");
            }
        } catch (SQLException ex) {
            logger.error("Getting Images count from Database ", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    logger.error("Logging SQL Exception ", ex);
                }
                if (psmt != null) {
                    try {
                        psmt.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
            }
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
            psmt = conn.prepareStatement(GET_ALL_IMAGES);
            psmt.setString(1, postid);
            rs = psmt.executeQuery();
            while (rs.next()) {
                bnimagesbn imagelist = new bnimagesbn();
//                imagelist.setID(rs.getInt("COLID"));
                imagelist.setCOLIMAGENAME(rs.getString("COLIMAGENAME"));
                imagelist.setCOLTHUMBNAILNAME(rs.getString("COLTHUMBNAILNAME"));
                images.add(imagelist);
            }
        } catch (Exception ex) {
            logger.error("Logging Exception ex", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    logger.error("Logging SQL Exception ", ex);
                }
                if (psmt != null) {
                    try {
                        psmt.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
            }
        }

        return images;

    }

    @Override
    public boolean InsertAWSForm(bnawsbn aws) {
        boolean status = false;
        try {
            conn = CreateConnection.getDSConnection();

            psmt = conn.prepareStatement(INSERTAWS_SQL);
            psmt.setString(1, aws.getTXTHIDDEN());
            psmt.setString(2, aws.getCOLTXTNAME());
            psmt.setString(3, aws.getCOLTXTEMAIL());
            psmt.setString(4, aws.getCOLTXTDATE());
            psmt.setString(5, aws.getCOLTXTDATETIME());
            psmt.setString(6, aws.getCOLTXTCOLOR());
            psmt.setString(7, aws.getCOLTXTMONTH());
            psmt.setInt(8, aws.getCOLTXTNUMBER());
            psmt.setString(9, aws.getCOLTXTURL());
            psmt.setString(10, aws.getCOLTXTTIME());
            psmt.setString(11, aws.getCOLTXTTEL());
            psmt.setString(12, aws.getCOLTXTSEARCH());
            psmt.setString(13, aws.getCOLTXTRANGE());
            psmt.setString(14, aws.getCOLTXTPASSWORD());
            psmt.setString(15, aws.getCOLTXTTEXTAREA());
            psmt.setString(16, aws.getCOLTXTCHECKBOX());
            psmt.setString(17, aws.getCOLTXTRADIO());
            psmt.setString(18, aws.getCOLTXTSINGLESELECT());
            psmt.setString(19, aws.getCOLTEXTMULTISELECT());
            psmt.setString(20,aws.getCOLTXTWEEK());
            int result = psmt.executeUpdate();
            if (result > 1) {
                status = true;
            }
        } catch (SQLException ex) {
            logger.error("Logging SQLException ", ex);
        } finally {
      
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    logger.error("Logging SQL Exception ", ex);
                }
                if (psmt != null) {
                    try {
                        psmt.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        logger.error("Logging SQL Exception ", ex);
                    }
                }
            }
        }

        return status;
    }

}
