package pers.yf.xnote.dao.model;


import pers.yf.spring.jdbc.lib.SimpleJdbc;

import java.util.List;

public class NoteDao extends SimpleJdbc{

    public List<Note> queryGroupNote(Long groupId, Long userId) {
        return (List<Note>) super.list(Note.class, "select *from blog where group_id=? and creater_id=?", groupId, userId);
    }

    public void insertNote(Note note) {
        Number key = super.insert(note);
        note.setId(key.longValue());
    }

    public void deleteNote(Long noteId, Long userId) {
        super.delete("delete from blog where id=? and creater_id=?", noteId, userId);
    }

}
