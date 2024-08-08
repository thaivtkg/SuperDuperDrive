package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    CredentialMapper credentialMapper;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Credential getCredentialById(int credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    public List<Credential> getAllCredentialsByUserId(int userId) {
        return credentialMapper.getCredentialsByUserId(userId);
    }

    public int addCredential(CredentialForm credentialForm,int userId) {
        Credential newCredential = new Credential();
        newCredential.setUsername(credentialForm.getUsername());
        newCredential.setUserId(userId);
        newCredential.setPassword(credentialForm.getPassword());
        newCredential.setUrl(credentialForm.getUrl());
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String key = Base64.getEncoder().encodeToString(salt);
        newCredential.setKey(key);
        String hashedPassword = encryptionService.encryptValue(newCredential.getPassword(), key);
        newCredential.setPassword(hashedPassword);
        return credentialMapper.insert(newCredential);
    }

    public void updateCredential(Integer credentialId, String newUserName, String url, String key, String password) {
        credentialMapper.updateCredential(credentialId, newUserName, url, key, password);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredentialById(credentialId);
    }

}
