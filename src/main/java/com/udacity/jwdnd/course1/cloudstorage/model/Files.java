package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

public class Files {
    private Integer fileid;
    private String filename;
    private  String contenttype;
    private String filesize;
    private Integer userid;
    private byte[] filedata;

    private MultipartFile files;

    public Files (Integer fileid, String filename, String contenttype, String filesize, Integer userid, byte[]filedata){
        this.fileid = fileid;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public Integer getFileid(){
        return fileid;
    }
    public void setFileid(Integer filedid) {
        this.userid = filedid;
    }

    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype(){
        return contenttype;
    }
    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }
    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid){
        this.userid = userid;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public MultipartFile getFile() {
        return files;
    }

    public void setFile(MultipartFile files) {
        this.files = files;
    }

}

