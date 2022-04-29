package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FilesService filesService;
    private final UserService usersService;
    private final NotesService notesService;
    private final CredentialsService credentialsService;
    private final EncryptionService encryptionService;

    public HomeController (FilesService filesService, UserService userService, NotesService notesService, CredentialsService credentialsService, EncryptionService encryptionService){
        this.filesService = filesService;
        this.usersService = userService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newFile") Files newFile,
            @ModelAttribute("newNote") Notes newNote, @ModelAttribute("newCredential") Credentials newCredential,
            Model model) {
        Integer userid = getUserid(authentication);
        model.addAttribute("files", this.filesService.getFilesList(userid));
        model.addAttribute("notes", notesService.getAllNotes(userid));
        model.addAttribute("credentials", credentialsService.getAllCredential(userid));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @PostMapping
    public String newFile(
            Authentication authentication, @ModelAttribute("newFile") Files newFile, Model model) throws IOException {
        String username = authentication.getName();
        Users users = usersService.getUser(username);
        Integer userid = users.getUserid();
        String[] fileListings = filesService.getFilesList(userid);
        MultipartFile multipartFile = newFile.getFile();
        String filename = multipartFile.getOriginalFilename();
        boolean fileIsDuplicate = false;
        for (String fileListing : fileListings) {
            if (fileListing.equals(filename)) {
                fileIsDuplicate = true;
                break;
            }
        }

        if (multipartFile.getSize() > 1000000) {
                model.addAttribute("result", "error");
            } else if (!fileIsDuplicate) {
            filesService.uploadFile(multipartFile, username);
            model.addAttribute("result", "success");
        } else {
            model.addAttribute("result","notsaved");
        }

        model.addAttribute("files", filesService.getFilesList(userid));
        return "result";
    }

        @GetMapping(
                value = "/get-file/{filename}",
                produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
        )

        public @ResponseBody
        byte[] getFile (@PathVariable String filename){
            return filesService.getFiles(filename).getFiledata();
        }

        @GetMapping(value = "/delete-file/{fileName}")
        public String deleteFile (
                Authentication authentication, @PathVariable String fileName, @ModelAttribute("newFile") Files newFile,
                @ModelAttribute("newNote") Notes newNote, @ModelAttribute("newCredential") Credentials newCredential,
                Model model){
            filesService.deleteFiles(fileName);
            Integer userid = getUserid(authentication);
            model.addAttribute("files", filesService.getFilesList(userid));
            model.addAttribute("result", "success");
            return "result";
        }

        private Integer getUserid (Authentication authentication){
            String username = authentication.getName();
            Users users = usersService.getUser(username);
            return users.getUserid();
        }


    }