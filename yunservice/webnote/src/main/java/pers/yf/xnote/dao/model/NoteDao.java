package pers.yf.xnote.dao.model;


import org.springframework.stereotype.Repository;
import pers.yf.spring.jdbc.lib.SimpleJdbc;

import java.util.List;

@Repository
public class NoteDao extends SimpleJdbc {

    public List<Note> queryGroupNote(Long groupId, Long userId) {
        return (List<Note>) super.list(Note.class, "select *from blog where group_id=? and creater_id=?", groupId, userId);
    }

    public int update(Note note, Long userId) {
        //super.updateNotNullWithKey(note);
        return super.update("update note set title=? and content=? where id=? and creater_id=?", note.getTitle(), note.getContent(), note.getId(), userId);
    }

    public void insertNote(Note note) {
        Number key = super.insert(note);
        note.setId(key.longValue());
    }

    public void deleteNote(Long noteId, Long userId) {
        super.delete("delete from blog where id=? and creater_id=?", noteId, userId);
    }

    public Note getNote(Long noteId) {
        return super.singleResult(Note.class, noteId);
    }
}
