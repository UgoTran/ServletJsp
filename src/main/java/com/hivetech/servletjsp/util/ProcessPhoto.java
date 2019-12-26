package com.hivetech.servletjsp.util;

import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessPhoto {

    private static final Logger LOGGER = Logger.getLogger(ProcessPhoto.class.getName());
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static final String diskNameOutput = "D:/";
    private static final String folderPathOutput = "classicmodels/customer/profile-photo/";
    private static int maxImgSize = 10_000;

    public static String saveImg(InputStream servletInputStream, String photoNameInput) throws IOException, NoSuchAlgorithmException {

        String tempFileName = UUID.randomUUID().toString();
        String fileExtension = "." + FilenameUtils.getExtension(photoNameInput);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String savingPath = diskNameOutput + folderPathOutput;
        String originalFilePath = savingPath + tempFileName;

        inputStream = servletInputStream;
        outputStream = new FileOutputStream(originalFilePath + fileExtension);
        inputStream.transferTo(outputStream);

        inputStream.close();
        outputStream.close();

        String md5FromFile = genMD5FromFile(originalFilePath + fileExtension);

        File originFile = new File(originalFilePath + fileExtension);
        File fileRenamed = new File(savingPath + md5FromFile + fileExtension);
        if (!fileRenamed.exists())
            originFile.renameTo(fileRenamed);
        else
            originFile.delete();

        return md5FromFile + fileExtension;
    }

    public static String genMD5FromFile(String tempFileName)
            throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(Paths.get(tempFileName)));
        byte[] digest = md.digest();
        String myChecksum = bytesToHex(digest).toUpperCase();

        return myChecksum;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void showProfilePhoto(HttpServletRequest req, HttpServletResponse res, String photoName) throws IOException {
        OutputStream outputStream = res.getOutputStream();
        outputStream.write(readImage(photoName));
        outputStream.close();
    }

    private static byte[] readImage(String photoName) throws IOException {
        byte[] result = null;
        String fileLocation = diskNameOutput + folderPathOutput + photoName;
        File f = new File(fileLocation);
        result = new byte[(int) f.length()];
        FileInputStream in = null;
        try {
            in = new FileInputStream(fileLocation);
            in.read(result);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        } finally {
            if (in != null)
                in.close();
        }
        return result;
    }
}
