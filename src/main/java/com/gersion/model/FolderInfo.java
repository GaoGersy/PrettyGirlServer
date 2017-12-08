package com.gersion.model;

public class FolderInfo {
    private Integer folderId;

    private String folderName;

    public FolderInfo(Integer folderId, String folderName) {
        this.folderId = folderId;
        this.folderName = folderName;
    }

    public FolderInfo() {
        super();
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    }
}