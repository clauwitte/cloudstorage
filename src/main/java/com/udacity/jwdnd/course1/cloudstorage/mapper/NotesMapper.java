package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface NotesMapper {
 @Select ("SELECT * FROM NOTES WHERE userid = #{userid}")
 List<Notes> getUsersNotes(Integer userid);

 @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
 @Options(useGeneratedKeys = true, keyProperty = "noteid")
 int insert(Notes notes);

 @Select("SELECT * FROM NOTES")
 List<Notes> getAllNotes();

 @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
 Notes getNotes(Integer noteid);

 @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
 void deleteNotes(Integer noteid);

 @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
 void updateNotes(Notes notes);
}