package pers.yf.xnote.dao.model;


import org.springframework.stereotype.Repository;
import pers.yf.spring.cloud.ext.auth.UserDetail;
import pers.yf.spring.jdbc.lib.SimpleJdbc;

import java.util.Date;
import java.util.List;

@Repository
public class NoteDao extends SimpleJdbc {

    public List<Note> queryGroupNote(Long groupId, Long userId) {
        return (List<Note>) super.list(Note.class, "select *from note where group_id=? and creater_id=?", groupId, userId);
    }

    public int updateTitle(Note note, String userId) {
        //super.updateNotNullWithKey(note);
        return super.update("update note set title=? where id=? and creater_id=?", note.getTitle(), note.getId(), userId);
    }

    public boolean updateContent(Note note, String userId) {
        //super.updateNotNullWithKey(note);
        return super.update("update note set content=? where id=? and creater_id=?", note.getContent(), note.getId(), userId) == 1;
    }

    public void createNote(Note note, UserDetail detail) {
        note.setCreaterId(Long.valueOf(detail.getId()));
        note.setCreateTime(new Date());
        Number key = super.insert(note);
        note.setId(key.longValue());
    }

    public void deleteNote(Long noteId, Long userId) {
        super.delete("delete from note where id=? and creater_id=?", noteId, userId);
    }

    public Note getNote(Long noteId) {
        return super.singleResult(Note.class, noteId);
    }

    public List<Note> getGroupNotes(Long groupId, String userId) {
        return super.list(Note.class, "select *from note where creater_id=? and group_id=?", userId, groupId);
    }
}
