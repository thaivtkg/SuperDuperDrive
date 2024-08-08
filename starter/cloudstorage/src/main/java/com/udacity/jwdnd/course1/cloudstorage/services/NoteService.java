package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    FileMapper fileMapper;

    NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper, FileMapper fileMapper) {
        this.noteMapper = noteMapper;
        this.fileMapper = fileMapper;
    }


    public void insert(NoteForm noteform, Integer userId) {
        Note note = new Note();
        note.setNoteTitle(noteform.getNoteTitle());
        note.setNoteDescription(noteform.getNoteDescription());
        note.setUserId(userId);
        noteMapper.insert(note);
    }

    public List<Note> selectAll() {
        return noteMapper.selectAll();
    }

    public void update(String noteTitle,String noteDescription,Integer noteId) {
        noteMapper.updateNote(noteTitle,noteDescription,noteId);
    }

    public void delete(int noteId) {
        noteMapper.DeleteNoteById(noteId);
    }

    public List<Note> getNoteByUserId(int userId) {
        return noteMapper.selectByUser(userId);
    }

    public Note getNoteById(int noteId) {
        return noteMapper.getNoteById(noteId);
    }


}