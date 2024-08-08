package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final FileService fileService;

    public HomeController(UserService userService, NoteService noteService,
                          CredentialService credentialService,
                          EncryptionService encryptionService, FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService =  encryptionService;
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String home(@ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm")CredentialForm credentialForm, Model model, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials",credentialService.getAllCredentialsByUserId(userId));
        model.addAttribute("notes",noteService.getNoteByUserId(userId));
        model.addAttribute("files",fileService.getAllFilesByUser(userId));
        return "home";
    }
}
