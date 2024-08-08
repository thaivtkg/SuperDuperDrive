package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file) ;

    @Select("SELECT * FROM FILES")
    List<File> findAll() ;

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File findByName(String fileName) ;

    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    List<File> getFileByUserId(int userId) ;

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    void deleteFile(Integer fileId) ;

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFileById(int fileId) ;

}
