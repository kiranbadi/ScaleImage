/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.json.JSONException;
import org.json.JSONObject;
import org.vasanti.helper.InsertImageInterface;
import org.vasanti.model.bnimagesbn;

@WebServlet(name = "DropZoneFileUpload", urlPatterns = {"/DropZoneFileUpload"})
public class DropZoneFileUpload extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(DropZoneFileUpload.class.getName());
    private static final long serialVersionUID = -5760835390791686979L;
    private File ImagefileUploadPath;
    private File ThumbnailFileUploadPath;

    boolean status = false;
    int count = 0;

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     *
     */
    /**
     *
     * @param config
     * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
            File file = new File(ImagefileUploadPath, request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();
                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } //        else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
        //            File file = new File(request.getServletContext().getRealPath("/") + "imgs/" + request.getParameter("delfile"));
        //            if (file.exists()) {
        //                file.delete(); // TODO:check and report success
        //            }
        //       } 
        else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            File file = new File(ImagefileUploadPath, request.getParameter("getthumb"));
            if (file.exists()) {
                String mimetype = getMimeType(file);
                if (mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("jpg") || mimetype.endsWith("gif")) {
                    BufferedImage im = ImageIO.read(file);
                    if (im != null) {
                        BufferedImage thumb = Scalr.resize(im, 75);
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        if (mimetype.endsWith("png")) {
                            ImageIO.write(thumb, "PNG", os);
                            response.setContentType("image/png");
                        } else if (mimetype.endsWith("jpeg")) {
                            ImageIO.write(thumb, "jpg", os);
                            response.setContentType("image/jpeg");
                        } else if (mimetype.endsWith("jpg")) {
                            ImageIO.write(thumb, "jpg", os);
                            response.setContentType("image/jpeg");
                        } else {
                            ImageIO.write(thumb, "GIF", os);
                            response.setContentType("image/gif");
                        }
                        ServletOutputStream srvos = response.getOutputStream();
                        response.setContentLength(os.size());
                        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
                        os.writeTo(srvos);
                        srvos.flush();
                        srvos.close();
                    }
                }
            } // TODO: check and report success
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     *
     */
    @SuppressWarnings("unchecked")
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
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/services/error-page.jsp");
                        rd.forward(request, response);
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
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/adultservices/error-page.jsp");
                        rd.forward(request, response);
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
                    BufferedImage thumbImg = Scalr.resize(img, Method.QUALITY, Mode.AUTOMATIC, 150, 150, Scalr.OP_ANTIALIAS);
                    File thumbnailfile = new File(ThumbnailFileUploadPath, ImageName);
                    images.setCOLTHUMBNAILNAME(ImageName);
                    ImageIO.write(thumbImg, "jpg", thumbnailfile);

                    files.put("name", ImageName);
//                    jsono.put("size", item.getSize());
//                    jsono.put("url", "UploadServlet?getfile=" + ImageName);
//                    jsono.put("thumbnail_url", "UploadServlet?getthumb=" + ImageName);
//                    jsono.put("delete_url", "UploadServlet?delfile=" + ImageName);
//                    jsono.put("delete_type", "GET");                    
                    InsertImageInterface insert = new InsertImageInterface();
                    count = insert.InsertImage(images);
                    files.put("status", status);
                    logger.info(files.toString());
                }
            }
        } catch (FileUploadException ex) {
            logger.error("Got the FileUpload Exception", ex);
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

    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }

    /**
     * The byte[] returned by MessageDigest does not have a nice textual
     * representation, so some form of encoding is usually performed.
     *
     * This implementation follows the example of David Flanagan's book "Java In
     * A Nutshell", and converts a byte array into a String of hex characters.
     *
     * Another popular alternative is to use a "Base64" encoding.
     */
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(ImagefileUploadPath, request.getParameter("delfile"));
            bnimagesbn images = new bnimagesbn();
            JSONObject files = new JSONObject();
            String COLPOSTID = request.getParameter("COLPOSTID");
            String delfile = request.getParameter("delfile");
            logger.info("Imagename for Delete View is = " + delfile);
            logger.info("COLPOSTID from Delete View  is = " + COLPOSTID);
            if (COLPOSTID != null) {
                images.setCOLPOSTID(COLPOSTID);
                logger.info("COLPOSTID from View for delete is = " + COLPOSTID);
            } else if (COLPOSTID == null) {
                try {
                    files.put("error", "Listing Id is null");
                    files.put("status", false);
                    writer.write(files.toString());
                    writer.close();
                } catch (JSONException ex) {
                    logger.error("Logging JSONException ", ex);
                }
            }
            if (delfile != null) {
                images.setCOLIMAGENAME(delfile);
                logger.info("Imagename for delete from View is = " + delfile);
                if (file.exists()) {
                    try {
                        file.delete();
                        files.put("name", delfile);
                        files.put("status", true);
                        InsertImageInterface delete = new InsertImageInterface();
                        delete.DeleteImage(images);
                        writer.write(files.toString());
                        writer.close();
                    } catch (JSONException ex) {
                        logger.error("Failed to delete image and add image name to json map", ex);
                    } finally {
                        writer.write(files.toString());
                        writer.close();
                    }
                }
            } else if (delfile == null) {
                try {
                    files.put("error", "Listing Id is null");
                    files.put("status", false);
                    writer.write(files.toString());
                    writer.close();
                } catch (JSONException ex) {
                    logger.error("Logging JSONException ", ex);
                }

            }

        }

    }
}
