package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("credential")
public class CredentialsController {
    private final CredentialsService credentialsService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialsController(CredentialsService credentialsService, UserService userService, EncryptionService encryptionService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getCredentials(Authentication authentication, Credentials credentials, Model model){
        Integer userId = getUserId(authentication);
        model.addAttribute("credentials", this.credentialsService.getAllCredential(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        Users users = userService.getUser(userName);
        return users.getUserid();
    }

    @PostMapping("add-credential")
    public String addCredential(Authentication authentication, @ModelAttribute("newCredential") Credentials newCredential, Model model) {
        Integer userId = getUserId(authentication);
        String password = newCredential.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        String url = newCredential.getUrl();
        String username = newCredential.getUsername();
        newCredential.setUrl(url);
        newCredential.setUsername(username);
        newCredential.setPassword(encryptedPassword);
        newCredential.setKey(encodedKey);

        if (newCredential.getCredentialid() != null) {
            this.credentialsService.updateCredentials(newCredential);
        } else {
            newCredential.setUserid(userId);
        this.credentialsService.addCredentials(newCredential);
    }

        model.addAttribute("credentials", this.credentialsService.getAllCredential(userId));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping(value = "/get-credential/{credentialId}")
    public Credentials getCredential(@PathVariable Integer credentialId) {
        return credentialsService.getCredential(credentialId);
    }


    @GetMapping(value = "/delete-credential/{credentialid}")
    public String deleteNote(Authentication authentication, @PathVariable Integer credentialid, @ModelAttribute("newFile") Files newFile,
                             @ModelAttribute("newNote") Notes newNote, @ModelAttribute("newCredential") Credentials newCredential,
                             Model model)  {
        credentialsService.deleteCredential(credentialid);
        Integer userId = getUserId(authentication);
        model.addAttribute("credentials", credentialsService.getAllCredential(userId));
        model.addAttribute("result", "success");
        return "result";
    }

}
