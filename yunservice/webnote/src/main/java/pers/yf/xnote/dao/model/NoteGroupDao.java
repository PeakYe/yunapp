package pers.yf.xnote.dao.model;

import org.springframework.stereotype.Repository;
import pers.yf.spring.jdbc.lib.SimpleJdbc;
import pers.yf.spring.jdbc.lib.annotation.RepEntity;

import java.util.List;

@Repository
@RepEntity(NoteGroup.class)
public class NoteGroupDao extends SimpleJdbc {

    public void insertNoteGroup(NoteGroup group) {
        group.setId((Long) super.insert(group));
    }

    public List<NoteGroup> getUserNoteGroups(String userId) {
        return (List<NoteGroup>) super.list("select * from note_group where creater_id=?", new Object[]{userId},NoteGroup.class);
    }

    public boolean deleteUserNoteGroup(String userId, Long groupId) {
        int i=jdbcTemplate.update("delete from note_group where id=? and creater_id=? ",new Object[]{groupId,userId});
        return i > 0;
    }
}
