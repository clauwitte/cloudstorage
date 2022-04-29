package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class FilesService {
    private final FilesMapper filesMapper;
    private final UsersMapper usersMapper;

    public FilesService (FilesMapper filesMapper, UsersMapper usersMapper){
        this.filesMapper = filesMapper;
        this.usersMapper = usersMapper;
    }

    public String[] getFilesList(Integer userid){
        return filesMapper.getFilesList(userid);
    }

    public void uploadFile(MultipartFile multipartFile, String username) throws IOException {
        InputStream fileInputStream = multipartFile.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int nRead;
        byte[] filedata = new byte[1024];
        while ((nRead = fileInputStream.read(filedata, 0, filedata.length)) != -1) {
            byteArrayOutputStream.write(filedata, 0, nRead);
        }

        byteArrayOutputStream.flush();
        byte[] fileData = byteArrayOutputStream.toByteArray();

        String filename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userId = usersMapper.getUser(username).getUserid();
        Files files = new Files(0, filename, contentType, fileSize, userId, fileData);
        filesMapper.insert(files);
    }

    public Files getFiles(String filename) {
        return filesMapper.getFiles(filename);
    }

    public void deleteFiles(String filename) {
        filesMapper.deleteFiles(filename);
    }

}
