package pers.yf.xnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pers.yf.spring.cloud.ext.auth.UserDetail;
import pers.yf.xnote.dao.model.Note;
import pers.yf.xnote.dao.model.NoteDao;

@RestController
@RequestMapping("note")
public class NoteService {

    @Autowired
    private NoteDao noteDao;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Long create(@RequestBody NoteCreateModel model, UserDetail detail) {
        Note note = new Note();
        note.setId(null);
        note.setCreaterId(Long.valueOf(detail.getId()));
        note.setTitle(model.getTitle());
        note.setContent(model.getContent());

        noteDao.insertNote(note);
        return note.getId();
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public boolean delete(@RequestBody Long noteId, UserDetail user) {
        noteDao.deleteNote(noteId, Long.valueOf(user.getId()));
        return true;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public boolean update(@RequestBody Note updateNote, UserDetail user) {
        Note note = new Note();
        note.setTitle(updateNote.getTitle());
        note.setContent(updateNote.getContent());
        return  noteDao.update(note, Long.valueOf(user.getId()))>1;
    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    public Note get(@RequestBody Long id, UserDetail userDetail) {
        Note note = noteDao.getNote(id);
        if (note != null) {
            if (note.getCreaterId() != Long.valueOf(userDetail.getId())) {
                return null;
            }
        }
        return note;
    }
}
