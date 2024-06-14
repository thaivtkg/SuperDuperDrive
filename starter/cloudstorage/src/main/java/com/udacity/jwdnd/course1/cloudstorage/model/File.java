package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {

    private int fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private int userId;
    private Byte[] fileData;

    public File() {}

    public File(int fileId, String fileName, String contentType, int userId, String fileSize, Byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.userId = userId;
        this.fileSize = fileSize;
        this.fileData = fileData;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Byte[] getFiledata() {
        return fileData;
    }

    public void setFiledata(Byte[] fileData) {
        this.fileData = fileData;
    }
}
