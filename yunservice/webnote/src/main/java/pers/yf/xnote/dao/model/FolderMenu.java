package pers.yf.xnote.dao.model;

import pers.yf.spring.jdbc.lib.annotation.Column;

public class FolderMenu {

    @Column("file_id")
    private String fileId;
    @Column("file_name")
    private String fileName;
    @Column("file_type")
    private String fileType;
    @Column("parent_id")
    private String parentId;

    @Column("true_id")
    private String trueId;

    public String getTrueId() {
        return trueId;
    }

    public void setTrueId(String trueId) {
        this.trueId = trueId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
