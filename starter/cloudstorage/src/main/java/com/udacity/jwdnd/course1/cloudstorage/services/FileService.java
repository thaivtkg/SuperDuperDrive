package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFile(int id) {
        return fileMapper.getFileById(id);
    }

    public List<File> getAllFilesByUser(Integer userId) {
        return fileMapper.getFileByUserId(userId);
    }

    public void deleteFile(Integer id) {
        fileMapper.deleteFile(id);
    }

    public int storeFile(File file){
        return fileMapper.insert(file);
    }

    public boolean existFile(String fileName) {
        return fileMapper.findByName(fileName) != null;
    }

}
