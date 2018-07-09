package pers.yf.xnote.dao.model;

import pers.yf.spring.jdbc.lib.annotation.Column;
import pers.yf.spring.jdbc.lib.annotation.Table;

import java.util.Date;

@Table("note_group")
public class NoteGroup {
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("creater_id")
    private Long createrId;
    @Column("create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
