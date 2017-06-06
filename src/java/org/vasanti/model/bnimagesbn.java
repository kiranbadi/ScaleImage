/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.model;

/**
 *
 * @author Kiran
 */
public class bnimagesbn {

    @Override
    public String toString() {
        return "bnimagesbn{COLPOSTID=" + COLPOSTID + ", COLIMAGENAME=" + COLIMAGENAME + ", COLTHUMBNAILNAME=" + COLTHUMBNAILNAME + '}';
    }
   
 

    private String COLPOSTID;
    private String COLIMAGENAME;
    private String COLTHUMBNAILNAME;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getThumbnailpath() {
        return thumbnailpath;
    }

    public void setThumbnailpath(String thumbnailpath) {
        this.thumbnailpath = thumbnailpath;
    }
    private String imagepath;
    private String thumbnailpath;


    /**
     * @return the COLPOSTID
     */
    public String getCOLPOSTID() {
        return COLPOSTID;
    }

    /**
     * @param COLPOSTID the COLPOSTID to set
     */
    public void setCOLPOSTID(String COLPOSTID) {
        this.COLPOSTID = COLPOSTID;
    }

    /**
     * @return the COLIMAGENAME
     */
    public String getCOLIMAGENAME() {
        return COLIMAGENAME;
    }

    /**
     * @param COLIMAGENAME the COLIMAGENAME to set
     */
    public void setCOLIMAGENAME(String COLIMAGENAME) {
        this.COLIMAGENAME = COLIMAGENAME;
    }

    /**
     * @return the COLTHUMBNAILNAME
     */
    public String getCOLTHUMBNAILNAME() {
        return COLTHUMBNAILNAME;
    }

    /**
     * @param COLTHUMBNAILNAME the COLTHUMBNAILNAME to set
     */
    public void setCOLTHUMBNAILNAME(String COLTHUMBNAILNAME) {
        this.COLTHUMBNAILNAME = COLTHUMBNAILNAME;
    }
}
