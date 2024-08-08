package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("credential")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final NoteService noteService;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, NoteService noteService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("add")
    public String getCredentialPage(Model model, @ModelAttribute("noteForm") NoteForm noteForm,
                                    Authentication authentication, @ModelAttribute("credentialForm") CredentialForm credentialForm) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        if(credentialForm.getCredentialId()== null){
            credentialService.addCredential(credentialForm,userId);
        }else {
            String newPassword = encryptionService.encryptValue(credentialForm.getPassword(), credentialForm.getKey());
            credentialService.updateCredential(credentialForm.getCredentialId(),credentialForm.getUsername(),credentialForm.getUrl(),credentialForm.getKey(),newPassword);
        }
        model.addAttribute("successMessage", "Credential add successfully");
        return "result";
    }

    @RequestMapping("delete/{credentialId}")
    public String deleteCredential(Model model, @PathVariable("credentialId") Integer credentialId,Authentication authentication) {
        credentialService.deleteCredential(credentialId);
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials",credentialService.getAllCredentialsByUserId(userId));
        model.addAttribute("notes",noteService.getNoteByUserId(userId));
        return "redirect:/home";
    }
}
