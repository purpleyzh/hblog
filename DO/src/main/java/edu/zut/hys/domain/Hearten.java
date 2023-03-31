package edu.zut.hys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName hearten
 */
@TableName(value ="hearten")
@Data
public class Hearten implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long heartenid;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private Date createtime;

    /**
     * 
     */
    private Long userid;

    /**
     * 文章或评论
     */
    private String relationtype;

    /**
     * 的id
     */
    private Long relationid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Hearten() {}

    public Hearten(Long userid, String relationtype, Long relationid) {
        this.status = "1";
        this.createtime = new Date(System.currentTimeMillis());
        this.userid = userid;
        this.relationtype = relationtype;
        this.relationid = relationid;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Hearten other = (Hearten) that;
        return (this.getHeartenid() == null ? other.getHeartenid() == null : this.getHeartenid().equals(other.getHeartenid()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getRelationtype() == null ? other.getRelationtype() == null : this.getRelationtype().equals(other.getRelationtype()))
            && (this.getRelationid() == null ? other.getRelationid() == null : this.getRelationid().equals(other.getRelationid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHeartenid() == null) ? 0 : getHeartenid().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getRelationtype() == null) ? 0 : getRelationtype().hashCode());
        result = prime * result + ((getRelationid() == null) ? 0 : getRelationid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", heartenid=").append(heartenid);
        sb.append(", status=").append(status);
        sb.append(", createtime=").append(createtime);
        sb.append(", userid=").append(userid);
        sb.append(", relationtype=").append(relationtype);
        sb.append(", relationid=").append(relationid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}