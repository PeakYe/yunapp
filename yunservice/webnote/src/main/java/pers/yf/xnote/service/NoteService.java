package pers.yf.xnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.yf.spring.cloud.ext.auth.UserDetail;
import pers.yf.xnote.dao.model.Note;
import pers.yf.xnote.dao.model.NoteDao;
import pers.yf.xnote.service.model.RenameModel;

import java.util.List;

@RestController
@RequestMapping("note")
public class NoteService {

    @Autowired
    private NoteDao noteDao;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Long save(@RequestBody NoteCreateModel model, UserDetail detail) {
        Note note = new Note();
        note.setId(model.getId());
        note.setCreaterId(Long.valueOf(detail.getId()));
        note.setTitle(model.getTitle());
        note.setContent(model.getContent());
        note.setGroupId(model.getGroupId());
        if (model.getId() != null) {
            noteDao.updateContent(note, detail.getId());
        } else {
            noteDao.createNote(note, detail);
        }

        return note.getId();
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public boolean delete(@RequestParam Long id, UserDetail user) {
        noteDao.deleteNote(id, Long.valueOf(user.getId()));
        return true;
    }

//    @RequestMapping(value = "update", method = RequestMethod.POST)
//    public boolean update(@RequestBody Note updateNote, UserDetail user) {
//        Note note = new Note();
//        note.setId(updateNote.getId());
//        note.setTitle(updateNote.getTitle());
//        note.setContent(updateNote.getContent());
//        return noteDao.updateContent() > 1;
//    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    public Note get(@RequestParam Long id, UserDetail userDetail) {
        Note note = noteDao.getNote(id);
        if (note != null) {
            if (note.getCreaterId() != Long.valueOf(userDetail.getId())) {
                return null;
            }
        }
        return note;
    }

    @RequestMapping(value = "groupNotes", method = RequestMethod.POST)
    public List<Note> groupNotes(@RequestParam Long id, UserDetail userDetail) {
        List<Note> notes = noteDao.getGroupNotes(id, userDetail.getId());
        return notes;
    }

    @RequestMapping(value = "rename", method = RequestMethod.POST)
    public Boolean rename(@RequestBody RenameModel rnote, UserDetail userDetail) {
        return noteDao.updateTitle(rnote.getTitle(), rnote.getId(), userDetail.getId());
    }

}
