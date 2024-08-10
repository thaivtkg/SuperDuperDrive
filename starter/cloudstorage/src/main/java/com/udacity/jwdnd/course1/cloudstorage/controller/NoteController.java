package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

    UserService userService;
    NoteService noteService;
    EncryptionService encryptionService;

    public NoteController(UserService userService, NoteService noteService, EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String noteView(@ModelAttribute NoteForm noteForm, Model model) {
        return "home";
    }

    @PostMapping("add")
    public String addNote(@ModelAttribute NoteForm noteForm, Model model, Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        if(noteForm.getNoteId() == null) {
            noteService.insert(noteForm,userId);
            redirectAttributes.addFlashAttribute("successMessage", "Note added successfully");
        }else {
            Integer noteId = noteForm.getNoteId();
            noteService.update(noteForm.getNoteTitle(),noteForm.getNoteDescription(),noteId);
            redirectAttributes.addFlashAttribute("successMessage", "Note update successfully");
        }
        model.addAttribute("encryptionService", encryptionService);

        model.addAttribute("notes",noteService.getNoteByUserId(userId));
        return "redirect:/home";
    }

    @RequestMapping("/delete/{noteId}")
    public String deleteNote(@ModelAttribute NoteForm noteForm,Model model, @PathVariable Integer noteId,Authentication authentication
    ,RedirectAttributes redirectAttributes) {
        noteService.delete(noteId);
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("notes",noteService.getNoteByUserId(userId));
        redirectAttributes.addFlashAttribute("successMessage", "Delete Note successfully");
        return "redirect:/home";
    }
}
