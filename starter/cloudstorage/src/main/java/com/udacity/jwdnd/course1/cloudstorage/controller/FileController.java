package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("file")
public class FileController {

    FileService fileService;
    UserService userService;
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("upload")
    public String upload(@RequestParam("fileUpload") MultipartFile multipartFile,RedirectAttributes redirAttrs, Model model, Authentication authentication) throws IOException {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        File file = new File();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setUserId(userId);
        file.setFileSize(String.format("%d KB", multipartFile.getSize()));
        file.setContentType(multipartFile.getContentType());
        file.setFileData(multipartFile.getInputStream().readAllBytes());
        if (fileService.existFile(file.getFileName())){
            redirAttrs.addFlashAttribute("errorMessage", "File already exists");
        }
        else if(multipartFile.getSize()==0){
            redirAttrs.addFlashAttribute("errorMessage", "File is empty");
        }else {
            fileService.storeFile(file);
            redirAttrs.addFlashAttribute("successMessage", "Saved file successfully");
        }

        return "redirect:/home";
    }

    @ResponseBody
    @GetMapping("/view/{fileId}")
    public ResponseEntity<InputStreamSource> getFile(@PathVariable Integer fileId) {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\""+file.getFileName()+"\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @ResponseBody
    @GetMapping(value = "/download/{fileId}")
    public ResponseEntity<byte[]> download(@PathVariable Integer fileId) {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getFileName()+"\"")
                .body(file.getFileData());
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,RedirectAttributes redirAttrs) {
        fileService.deleteFile(id);
        redirAttrs.addFlashAttribute("successMessage", "File deleted successfully");
        return "redirect:/home";
    }
}
