package pers.yf.xnote.dao.model;

import pers.yf.spring.jdbc.lib.annotation.Column;
import pers.yf.spring.jdbc.lib.annotation.Id;
import pers.yf.spring.jdbc.lib.annotation.Table;

import java.util.Date;

@Table("note")
public class Note {

    @Id("id")
    private Long id;

    @Column("create_time")
    private Date createTime;

    @Column("creater_id")
    private Long createrId;

    @Column("title")
    private String title;

    @Column("group_id")
    private Long groupId;

    @Column("type")
    private Integer type;

    @Column("label")
    private String label;

    @Column("praises")
    private Integer praises;

    @Column("opposes")
    private Integer opposes;

    @Column("comments")
    private Integer comments;

    @Column("deleted")
    private Integer deleted;

    @Column("content")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public Integer getPraises() {
        return praises;
    }

    public void setPraises(Integer praises) {
        this.praises = praises;
    }

    public Integer getOpposes() {
        return opposes;
    }

    public void setOpposes(Integer opposes) {
        this.opposes = opposes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}