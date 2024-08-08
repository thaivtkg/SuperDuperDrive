package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;

    public CredentialForm(Integer credentialId, String url, String username, String key, String password) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
    }

    public CredentialForm(String url, String username, String key, String password) {
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
    }

    public CredentialForm() {
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

