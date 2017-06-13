/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vasanti.helper.InsertImageInterface;
import org.vasanti.model.bnawsbn;

/**
 *
 * @author Kiran
 */
@WebServlet(name = "AWSController", urlPatterns = {"/AWSController"})
public class AWSController extends HttpServlet {

    private static final long serialVersionUID = -6533554543079854868L;
    static final Logger logger = LogManager.getLogger(AWSController.class.getName());

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
        try (PrintWriter out = response.getWriter()) {
            bnawsbn aws = new bnawsbn();
            String hiddenfield = request.getParameter("hiddenfield");
            aws.setTXTHIDDEN(hiddenfield);
            logger.info("hiddenfield is " + hiddenfield);
            String txtname = request.getParameter("txtname");
            aws.setCOLTXTNAME(txtname);
            logger.info("txtname is " + txtname);
            String txtemail = request.getParameter("txtemail");
            aws.setCOLTXTEMAIL(txtemail);
            logger.info("txtemail is " + txtemail);
            String txtdate = request.getParameter("txtdate");
            aws.setCOLTXTDATE(txtdate);
            logger.info("txtdate is " + txtdate);
            String txtdatetime = request.getParameter("txtdatetime");
             aws.setCOLTXTDATETIME(txtdatetime);
            logger.info("txtdatetime is  " + txtdatetime);
            String txtcolor = request.getParameter("txtcolor");
            aws.setCOLTXTCOLOR(txtcolor);
            logger.info("txtcolor is " + txtcolor);
            String txtmonth = request.getParameter("txtmonth");
            aws.setCOLTXTMONTH(txtmonth);
            logger.info("txtmonth is " + txtmonth);
            int txtnumber = Integer.parseInt(request.getParameter("txtnumber"));            
            aws.setCOLTXTNUMBER(txtnumber);
            logger.info("txtnumber is " + txtnumber);
            String txtweek = request.getParameter("txtweek");
            aws.setCOLTXTWEEK(txtweek);
            logger.info("txtweek is " + txtweek);
            String txturl = request.getParameter("txturl");
            aws.setCOLTXTURL(txturl);
            logger.info("txturl is " + txturl);
            String txttime = request.getParameter("txttime");
            aws.setCOLTXTTIME(txttime);
            logger.info("txttime is " + txttime);
            String txttel = request.getParameter("txttel");
            aws.setCOLTXTTEL(txttel);
            logger.info("txttel is " + txttel);
            String txtsearch = request.getParameter("txtsearch");
            aws.setCOLTXTSEARCH(txtsearch);
            logger.info("txtsearch is " + txtsearch);
            String txtrange = request.getParameter("txtrange");
            aws.setCOLTXTRANGE(txtrange);
            logger.info("txtrange is " + txtrange);
            String txtpassword = request.getParameter("txtpassword");
            aws.setCOLTXTPASSWORD(txtpassword);
            logger.info("txtpassword is " + txtpassword);
            String txttextarea = request.getParameter("txttextarea");
            aws.setCOLTXTTEXTAREA(txttextarea);
            logger.info("txttextarea is " + txttextarea);
            String[] checkbox = request.getParameterValues("checkbox");
            aws.setCOLTXTCHECKBOX(Arrays.toString(checkbox));
            for (String values : checkbox) {
                logger.info("checkbox values are " + values);
            }
            String radio = request.getParameter("radio");
            aws.setCOLTXTRADIO(radio);
            logger.info("radio value is " + radio);
            String txtsingleselect = request.getParameter("txtsingleselect");
            aws.setCOLTXTSINGLESELECT(txtsingleselect);
            logger.info("singleselect is " + txtsingleselect);
            String[] txtmultiselect = request.getParameterValues("txtmultiselect");
            aws.setCOLTEXTMULTISELECT(Arrays.toString(txtmultiselect));
            for (String multiselect : txtmultiselect) {
                logger.info("multiselect values are " + multiselect);
            }
            InsertImageInterface helper = new InsertImageInterface();
            helper.InsertAWSForm(aws);
            RequestDispatcher rd = request.getRequestDispatcher("/TestDB.jsp");
            rd.forward(request, response);

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
        return "Servlet to validate the AWS Form POC and insert data into mysql database ";
    }// </editor-fold>

}
