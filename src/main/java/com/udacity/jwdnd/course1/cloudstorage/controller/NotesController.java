package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("notes")
public class NotesController {
    private final NotesService notesService;
    private final UserService userService;

    public NotesController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @GetMapping
    public String getNotes(Authentication authentication, Notes notes, Model model){
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", this.notesService.getAllNotes(userId));
        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        Users users = userService.getUser(userName);
        return users.getUserid();
    }

    @PostMapping ("add-note")

    public String addNotes( Authentication authentication, @ModelAttribute("newNote") Notes newNote, Model model){

        Integer userId = getUserId(authentication);
        if (newNote.getNoteid()!= null) {
            this.notesService.updateNotes(newNote);
        } else {
            newNote.setUserid(userId);
            this.notesService.addNote(newNote);
           newNote.setNotetitle("");
           newNote.setNotedescription("");
        }

        model.addAttribute("notes", this.notesService.getAllNotes(userId));
        model.addAttribute("result", "success");
        return "result";

    }

    @GetMapping(value = "/delete-note/{noteid}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteid, @ModelAttribute("newFile") Files newFile,
                             @ModelAttribute("newNote") Notes newNote, @ModelAttribute("newCredential") Credentials newCredential,
                             Model model)  {
        notesService.deleteNotes(noteid);
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", notesService.getAllNotes(userId));
        model.addAttribute("result", "success");
        return "result";
    }

}
