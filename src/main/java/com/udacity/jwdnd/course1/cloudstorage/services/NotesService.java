package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    private final UsersMapper usersMapper;
    private final NotesMapper notesMapper;

    public NotesService(UsersMapper usersMapper, NotesMapper notesMapper) {
        this.usersMapper = usersMapper;
        this.notesMapper = notesMapper;
    }

    public void addNote(Notes notes) {
        notesMapper.insert(notes);
    }

    public List<Notes> getAllNotes(Integer userid){
        return notesMapper.getUsersNotes(userid);
    }
        public Notes getNotes(Integer noteid) {
        return notesMapper.getNotes(noteid);
    }

    public void deleteNotes(Integer noteid) {
        notesMapper.deleteNotes(noteid);
    }

    public void updateNotes(Notes notes) {
        notesMapper.updateNotes(notes);
    }
}
