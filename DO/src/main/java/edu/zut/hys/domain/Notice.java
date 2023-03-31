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
 * @TableName notice
 */
@TableName(value ="notice")
@Data
public class Notice implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long noticeid;

    /**
     * 已读、未读
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
     * 系统/管理员
     */
    private String sendtype;

    /**
     * 
     */
    private Long touserid;

    /**
     * 点赞、评论、未读私信
     */
    private String bodytype;

    /**
     * 内容
     */
    private String body;

    public Notice() {
    }

    public Notice(Long userid, String sendtype, Long touserid, String bodytype, String body) {
        this.status = "1";
        this.createtime = new Date(System.currentTimeMillis());
        this.userid = userid;
        this.sendtype = sendtype;
        this.touserid = touserid;
        this.bodytype = bodytype;
        this.body = body;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        Notice other = (Notice) that;
        return (this.getNoticeid() == null ? other.getNoticeid() == null : this.getNoticeid().equals(other.getNoticeid()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getSendtype() == null ? other.getSendtype() == null : this.getSendtype().equals(other.getSendtype()))
            && (this.getTouserid() == null ? other.getTouserid() == null : this.getTouserid().equals(other.getTouserid()))
            && (this.getBodytype() == null ? other.getBodytype() == null : this.getBodytype().equals(other.getBodytype()))
            && (this.getBody() == null ? other.getBody() == null : this.getBody().equals(other.getBody()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNoticeid() == null) ? 0 : getNoticeid().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getSendtype() == null) ? 0 : getSendtype().hashCode());
        result = prime * result + ((getTouserid() == null) ? 0 : getTouserid().hashCode());
        result = prime * result + ((getBodytype() == null) ? 0 : getBodytype().hashCode());
        result = prime * result + ((getBody() == null) ? 0 : getBody().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", noticeid=").append(noticeid);
        sb.append(", status=").append(status);
        sb.append(", createtime=").append(createtime);
        sb.append(", userid=").append(userid);
        sb.append(", sendtype=").append(sendtype);
        sb.append(", touserid=").append(touserid);
        sb.append(", bodytype=").append(bodytype);
        sb.append(", body=").append(body);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}