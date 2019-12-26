package com.hivetech.servletjsp.service;

public interface UploadPhotoService<E> {
    boolean editPhotoPath(E e);
    String getPhotoPath(String elementId);
}
