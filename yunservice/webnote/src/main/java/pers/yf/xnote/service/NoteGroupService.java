package pers.yf.xnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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


    @RequestMapping("create")
    public Long create(@RequestBody NoteGroup group, UserDetail userDetail) {
        group.setId(null);
        group.setCreaterId(Long.valueOf(userDetail.getId()));
        group.setCreateTime(new Date());
        noteGroupDao.insertNoteGroup(group);
        return group.getId();
    }

    @RequestMapping("delete")
    public boolean delete(@RequestParam Long groupId, UserDetail userDetail) {
        return noteGroupDao.deleteUserNoteGroup(userDetail.getId(), groupId);
    }


    @RequestMapping("list")
    public List<NoteGroup> list(UserDetail userDetail) {
        return noteGroupDao.getUserNoteGroups(userDetail.getId());
    }
}
