package pers.yf.xnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.yf.spring.cloud.ext.auth.UserDetail;
import pers.yf.xnote.dao.model.NoteGroup;
import pers.yf.xnote.dao.model.NoteGroupDao;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/group")
public class NoteGroupService {

    @Autowired
    NoteGroupDao noteGroupDao;


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Long create(@RequestBody NoteGroup group, UserDetail userDetail) {
        group.setId(null);
        group.setCreaterId(Long.valueOf(userDetail.getId()));
        group.setCreateTime(new Date());
        noteGroupDao.insertNoteGroup(group);
        return group.getId();
    }

    @RequestMapping(value="delete", method = RequestMethod.POST)
    public boolean delete(@RequestParam Long groupId, UserDetail userDetail) {
        return noteGroupDao.deleteUserNoteGroup(userDetail.getId(), groupId);
    }


    @RequestMapping("list")
    public List<NoteGroup> list(UserDetail userDetail) {
        return noteGroupDao.getUserNoteGroups(userDetail.getId());
    }
}
