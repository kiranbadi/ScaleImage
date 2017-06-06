/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.vasanti.helper.InsertImageInterface;

/**
 *
 * @author Kiran
 */
@WebServlet(name = "GetImagesCount", urlPatterns = {"/GetImagesCount"})
public class GetImagesCount extends HttpServlet {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(GetImagesCount.class.getName());
    private static final long serialVersionUID = 2759700837826226781L;
    JSONObject files = new JSONObject();
    int count = 0;

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
            InsertImageInterface imgcount = new InsertImageInterface();

            try {
                count = imgcount.GetImagesCount(postid);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            files.put("count", count);
            logger.info(files.toString());
            files.toString();
            out.write(files.toString());
            out.close();
        } catch (JSONException ex) {
            try {
                try (PrintWriter out = response.getWriter()) {
                    logger.error("logging jsonexception ", ex);
                    files.put("count", count);
                    files.toString();
                    out.write(files.toString());
                }
            } catch (JSONException ex1) {
                logger.error("logging jsonexception ", ex1);
            }

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
        return "Servlet to get total number of images from database";
    }// </editor-fold>

}
