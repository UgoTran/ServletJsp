package com.hivetech.servletjsp.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/upload/photo/*")
public class UploadPhotoServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UploadPhotoServlet.class.getName());
    String image_name = "";
    ResourceBundle props = null;
    String filePath = "";
    private static final int BUFSIZE = 100;
    private ServletContext servletContext;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doShowImageOnPage("d:/pic2.jpg", request, response);
    }

    public void sendImage(ServletContext servletContext,
                          HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        this.servletContext = servletContext;
        String reqUrl = request.getRequestURL().toString();
        StringTokenizer tokens = new StringTokenizer(reqUrl, "/");
        int noOfTokens = tokens.countTokens();
        String tokensString[] = new String[noOfTokens];
        int count = 0;
        while (tokens.hasMoreElements()) {
            tokensString[count++] = (String) tokens.nextToken();
        }
        String folderName = tokensString[noOfTokens - 2];
        image_name = tokensString[noOfTokens - 1];
        filePath = "/" + folderName + "/" + image_name;
        String fullFilePath = "d:/pic2.jpg";
        LOGGER.log(Level.INFO, fullFilePath);
 doShowImageOnPage(fullFilePath, request, response);
//        doDownload(fullFilePath, request, response);
    }
    private void doShowImageOnPage(String fullFilePath,
                                   HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //reset response to get Photo
        response.reset();
        response.setHeader("Content-Disposition", "inline");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
//        response.setContentType("image/tiff");
        byte[] image = getImage(fullFilePath);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(image);
        outputStream.close();
    }

    private void doDownload(String filePath, HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        File fileName = new File(filePath);
        int length = 0;
        ServletOutputStream outputStream = response.getOutputStream();
 ServletContext context = getServletConfig().getServletContext();
//        ServletContext context = servletContext;
        String mimetype = context.getMimeType(filePath);
        response.setContentType((mimetype != null) ? mimetype
                : "application/octet-stream");
        response.setContentLength((int) fileName.length());
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + image_name + "\"");
        byte[] bbuf = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(fileName));
        while ((in != null) && ((length = in.read(bbuf)) != -1)) {
            outputStream.write(bbuf, 0, length);
        }
        in.close();
        outputStream.flush();
        outputStream.close();
    }
    private byte[] getImage(String filename) {
        byte[] result = null;
        String fileLocation = filename;
        File f = new File(fileLocation);
        result = new byte[(int)f.length()];
        try {
            FileInputStream in = new FileInputStream(fileLocation);
            in.read(result);
        }
        catch(Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());

        }
        return result;
    }
}
