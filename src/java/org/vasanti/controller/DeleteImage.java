/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.vasanti.helper.InsertImageInterface;
import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
@WebServlet(name = "DeleteImage", urlPatterns = {"/DeleteImage"})
public class DeleteImage extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(DeleteImage.class.getName());
    private static final long serialVersionUID = -7942723764413327509L;
    private File ImagefileUploadPath;
    private File ThumbnailFileUploadPath;
    boolean imagefileStatus;
    boolean thumbfileStatus;
    int count = 0;
    boolean status = false;

    @Override
    public void init(ServletConfig config) {
        try {
            super.init(config);
            ImagefileUploadPath = new File(getServletContext().getInitParameter("images"));
            ThumbnailFileUploadPath = new File(getServletContext().getInitParameter("thumbnails"));
        } catch (ServletException ex) {
            logger.error("Got the ServletException", ex);
        }

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = "";
        Boolean status = false;
        try (PrintWriter writer = response.getWriter()) {
            if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
                bnimagesbn images = new bnimagesbn();
                JSONObject files = new JSONObject();
                String COLPOSTID = request.getParameter("COLPOSTID");
                String delfile = request.getParameter("delfile");
                logger.info("Imagename for Delete View is = " + delfile);
                logger.info("COLPOSTID from Delete View  is = " + COLPOSTID);
                if (COLPOSTID != null && delfile != null) {
                    images.setCOLPOSTID(COLPOSTID);
                    images.setCOLIMAGENAME(delfile);
                    File file = new File(ImagefileUploadPath, delfile);
                    File Thumbfile = new File(ThumbnailFileUploadPath, delfile);
                    logger.info(file);
                    path = file.getCanonicalPath();
                    logger.info("Path CanonicalPath is " + path);
                    if (file.exists() && Thumbfile.exists()) {
                        try {
                            imagefileStatus = file.delete();
                            thumbfileStatus = Thumbfile.delete();
                            files.put("name", delfile);
                            files.put("imagestatus", imagefileStatus);
                            files.put("thumbstatus", thumbfileStatus);
                            InsertImageInterface delete = new InsertImageInterface();
                            count = delete.DeleteImage(images);
                            if(count >= 0){
                                status = true;
                                files.put("count",count);
                                files.put("status", status);
                            }
                            
                            logger.info(files.toString());
                            writer.write(files.toString());
                            writer.close();
                        } catch (JSONException ex) {
                            logger.error("Failed to delete image and add image name to json map", ex);
                        } finally {
                            writer.write(files.toString());
                            writer.close();
                        }
                    } else {
                        files.put("count",count);
                        files.put("name", delfile);
                        files.put("imagestatus", false);
                        files.put("thumbstatus", false);
                        files.put("status", status);
                        logger.info(files.toString());
                        writer.write(files.toString());
                        writer.close();
                    }
                } else if (COLPOSTID == null || delfile == null) {
                    files.put("Message", "Id or Image was null");
                    files.put("status", status);
                    writer.write(files.toString());
                    writer.close();
                }

            }
        } catch (JSONException ex) {
            logger.error("Failed to delete image and add image name to json map", ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    
     /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to delete images from the server and get the count of images from database for postid ";
    }// </editor-fold>

}
