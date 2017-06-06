/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.vasanti.helper.InsertImageInterface;
import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
@WebServlet(name = "GetAllImages", urlPatterns = {"/GetAllImages"})
public class GetAllImages extends HttpServlet {

    private static final long serialVersionUID = 2792479043675161292L;

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(GetAllImages.class.getName());
    JSONObject files = new JSONObject();

    // Get these values from Property Files
    String ImagePath = "http://localhost:8080/ScaleImage/images/";
    String ThumbnailPath = "http://localhost:8080/ScaleImage/thumbnails/";

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
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String postid = request.getParameter("postid");
            InsertImageInterface getallimages = new InsertImageInterface();
            ArrayList<bnimagesbn> images = new ArrayList<>();
            ArrayList<bnimagesbn> paths = new ArrayList<>();
            images = (ArrayList<bnimagesbn>) getallimages.GetAllImages(postid);
            logger.info("Images from database is " + Arrays.toString(images.toArray()));
            //    System.out.println(Arrays.toString(list.toArray()));
            for (bnimagesbn image : images) {
                bnimagesbn bean = new bnimagesbn();
                logger.info("Image path is " + ImagePath + "" + image.getCOLIMAGENAME());
                String FinalImagePath = ImagePath + "" + image.getCOLIMAGENAME();
                bean.setCOLIMAGENAME(image.getCOLIMAGENAME());
                bean.setImagepath(FinalImagePath);
                logger.info("Thumbnail path is " + ThumbnailPath + "" + image.getCOLTHUMBNAILNAME());
                String FinalThumbnailPath = ThumbnailPath + "" + image.getCOLTHUMBNAILNAME();
                bean.setCOLTHUMBNAILNAME(image.getCOLTHUMBNAILNAME());
                bean.setThumbnailpath(FinalThumbnailPath);
                paths.add(bean);
                logger.info("Paths list contains " + paths.toString());
            }
            files.put("images", paths);
            files.toString();
            out.write(files.toString());
            logger.info("Images json for GetAllImages is " + files.toString());
        } catch (JSONException ex) {
            logger.error("logging jsonexception ", ex);
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to fetch all Images Information from Database ";
    }// </editor-fold>

}
