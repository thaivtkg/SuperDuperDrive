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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String addCredential(Model model, @ModelAttribute("noteForm") NoteForm noteForm,
                                    Authentication authentication, @ModelAttribute("credentialForm") CredentialForm credentialForm,
                                RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        if(credentialForm.getCredentialId()== null){
            credentialService.addCredential(credentialForm,userId);
            redirectAttributes.addFlashAttribute("successMessage", "Credential add successfully");
        }else {
            String newPassword = encryptionService.encryptValue(credentialForm.getPassword(), credentialForm.getKey());
            credentialService.updateCredential(credentialForm.getCredentialId(),credentialForm.getUsername(),credentialForm.getUrl(),credentialForm.getKey(),newPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Update credential successfully");
        }

        return "redirect:/home";
    }

    @RequestMapping("delete/{credentialId}")
    public String deleteCredential(Model model, @PathVariable("credentialId") Integer credentialId,Authentication authentication,RedirectAttributes redirectAttributes) {
        credentialService.deleteCredential(credentialId);
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        redirectAttributes.addFlashAttribute("successMessage", "Delete credential successfully");
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials",credentialService.getAllCredentialsByUserId(userId));
        model.addAttribute("notes",noteService.getNoteByUserId(userId));
        return "redirect:/home";
    }
}
