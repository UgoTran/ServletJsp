package com.hivetech.servletjsp.util.enumdata;

public enum FileType {
    PNG("png"), JPEG("jpeg");

    private String fileType;

    private FileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }
}
