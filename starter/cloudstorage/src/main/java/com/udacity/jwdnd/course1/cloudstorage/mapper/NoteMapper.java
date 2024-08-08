package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle} , #{noteDescription} , #{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "noteId")
    int insert(Note note);

    @Select("SELECT * FROM NOTES")
    List<Note> selectAll();

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> selectByUser(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid=#{id}")
    Note getNoteById(Integer id);

    @Delete("DELETE FROM NOTES WHERE noteid=#{id}")
    void DeleteNoteById(Integer id);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} Where noteid=#{noteId}")
    void updateNote(String noteTitle, String noteDescription, Integer noteId);
}
