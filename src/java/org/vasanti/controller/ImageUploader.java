/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.json.JSONException;
import org.json.JSONObject;
import org.vasanti.helper.InsertImageInterface;
import org.vasanti.model.bnimagesbn;

/**
 *
 * @author Kiran
 */
@WebServlet(name = "ImageUploader", urlPatterns = {"/ImageUploader"})
public class ImageUploader extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ImageUploader.class.getName());
    private static final long serialVersionUID = 1105393417586405734L;
    private File ImagefileUploadPath;
    private File ThumbnailFileUploadPath;

    boolean status = false;
    int count = 0;

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
     * @throws org.json.JSONException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        JSONObject files = new JSONObject();
        try (PrintWriter out = response.getWriter()) {
            boolean isGet = "GET".equals(request.getMethod());
            if (isGet) {
                // Method is get , send the error message in json format
                String error = "Request Method not Supported.";
                files.put("error", error);
                files.put("status", status);
                out.write(files.toString());
                out.close();
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
     * @throws org.json.JSONException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            logger.error("Got the JsonException", ex);
        }
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
        PrintWriter writer = response.getWriter();
        String path = "";
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }
        bnimagesbn images = new bnimagesbn();
        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        response.setContentType("application/json");
        JSONObject files = new JSONObject();
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    FileItem colpostid = (FileItem) items.get(0);
                    String COLPOSTID = colpostid.getString();
                    if (COLPOSTID != null) {
                        images.setCOLPOSTID(COLPOSTID);
                        logger.info("COLPOSTID from View  is = " + COLPOSTID);
                    } else if (COLPOSTID == null) {
                        status = false;
                        String error = "Listing Id is empty";
                        files.put("status", status);
                        files.put("error", error);
                    }
                } else if (!item.isFormField()) {
                    String ImageName = "";
                    String name = item.getName();
                    String contentType = item.getContentType();
                    logger.info("Content Type  of file is " + contentType);
                    long size = item.getSize();
                    logger.info("Size of file is " + size);
                    String filetype = name.substring(name.lastIndexOf("."));
                    SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
                    String randomNum = Integer.toString(prng.nextInt());
                    MessageDigest sha = MessageDigest.getInstance("SHA-1");
                    byte[] result = sha.digest(randomNum.getBytes());
                    ImageName = hexEncode(result) + filetype;
                    logger.info(" ImageName1 is " + ImageName);
                    if (name != null) {
                        if ((size < 9048576) && (("image/jpeg".equals(contentType)) || ("image/jpg".equals(contentType)) || ("image/gif".equals(contentType)) || ("image/png".equals(contentType)) || ("image/bmp".equals(contentType)))) {
                            images.setCOLIMAGENAME(ImageName);
                        }
                    } else if (name == null) {
                        // Update the error status since file name is null
                        status = false;
                        String error = "Image name is empty ";
                        files.put("status", status);
                        files.put("error", error);
                    }

                    File file = new File(ImagefileUploadPath, ImageName);
                    item.write(file);
                    path = file.getCanonicalPath();
                    logger.info(" ImageName1 CanonicalPath is " + path);
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read((new File(path)));
                    } catch (IOException ex) {
                        logger.error("Logging IO Exception while creating thumbnail", ex);
                    }
                    BufferedImage thumbImg = Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 150, 150, Scalr.OP_ANTIALIAS);
                    File thumbnailfile = new File(ThumbnailFileUploadPath, ImageName);
                    images.setCOLTHUMBNAILNAME(ImageName);
                    ImageIO.write(thumbImg, "jpg", thumbnailfile);
                    files.put("name", ImageName);
                    InsertImageInterface insert = new InsertImageInterface();
                    count = insert.InsertImage(images);
                    if (count > 0) {
                        status = true;
                        files.put("status", status);
                        files.put("count", count);
                    }
                    logger.info(files.toString());
                }
            }
        } catch (FileUploadException ex) {
            try {
                logger.error("Got the FileUpload Exception", ex);
                String error = "Some error has occurred";
                files.put("error", error);
            } catch (JSONException ex1) {
                logger.error("Got the JsonException", ex1);
            }
        } catch (Exception ex) {
            logger.error("Got the Exception", ex);
        } finally {
            try {
                files.put("status", status);
                writer.write(files.toString());
                writer.close();
            } catch (JSONException ex) {
                logger.error("Got the JSONException", ex);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to upload images and get image count from the database";
    }// </editor-fold>

    static private String hexEncode(byte[] aInput) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }
        return result.toString();
    }

    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("jpg")) {
                mimetype = "image/jpg";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("jpeg")) {
                mimetype = "image/jpeg";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("gif")) {
                mimetype = "image/gif";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }

}
