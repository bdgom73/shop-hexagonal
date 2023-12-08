package com.shop.application.service;

public interface FileService {

    String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception;

    void deleteFile(String filePath);
}
