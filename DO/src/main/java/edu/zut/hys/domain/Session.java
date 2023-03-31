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
 * @TableName session
 */
@TableName(value ="session")
@Data
public class Session implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long sessionid;

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
     * 私聊、群聊
     */
    private String sessiontype;

    /**
     * 
     */
    private String sessionname;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Session() {
    }

    public Session(Long userid, String sessiontype, String sessionname) {
        this.status = "1";
        this.createtime = new Date(System.currentTimeMillis());
        this.userid = userid;
        this.sessiontype = sessiontype;
        this.sessionname = sessionname;
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
        Session other = (Session) that;
        return (this.getSessionid() == null ? other.getSessionid() == null : this.getSessionid().equals(other.getSessionid()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getSessiontype() == null ? other.getSessiontype() == null : this.getSessiontype().equals(other.getSessiontype()))
            && (this.getSessionname() == null ? other.getSessionname() == null : this.getSessionname().equals(other.getSessionname()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSessionid() == null) ? 0 : getSessionid().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getSessiontype() == null) ? 0 : getSessiontype().hashCode());
        result = prime * result + ((getSessionname() == null) ? 0 : getSessionname().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sessionid=").append(sessionid);
        sb.append(", status=").append(status);
        sb.append(", createtime=").append(createtime);
        sb.append(", userid=").append(userid);
        sb.append(", sessiontype=").append(sessiontype);
        sb.append(", sessionname=").append(sessionname);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}