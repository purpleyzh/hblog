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
 * @TableName appfile
 */
@TableName(value ="appfile")
@Data
public class Appfile implements Serializable {
    /**
     * 文件id
     */
    @TableId(type = IdType.AUTO)
    private Long fileid;

    /**
     * 状态
     */
    private String status;

    /**
     * 上传人
     */
    private Long userid;

    /**
     * 上传时间
     */
    private Date createtime;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 拓展名
     */
    private String extendname;

    /**
     * 私信、头像、封面、插图
     */
    private String relationtype;

    /**
     * 文章id
     */
    private Long relationid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Appfile(){}

    public Appfile(Long userid, Long relationid, String fileName, String relationtype){
        this.setCreatetime(new Date(System.currentTimeMillis()));
        this.setStatus("1");
        this.setUserid(userid);
        this.setFilename(fileName);
        this.setExtendname(fileName.substring(fileName.lastIndexOf(".")));
        this.setRelationid(relationid);
        this.setRelationtype(relationtype);
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
        Appfile other = (Appfile) that;
        return (this.getFileid() == null ? other.getFileid() == null : this.getFileid().equals(other.getFileid()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getFilename() == null ? other.getFilename() == null : this.getFilename().equals(other.getFilename()))
            && (this.getExtendname() == null ? other.getExtendname() == null : this.getExtendname().equals(other.getExtendname()))
            && (this.getRelationtype() == null ? other.getRelationtype() == null : this.getRelationtype().equals(other.getRelationtype()))
            && (this.getRelationid() == null ? other.getRelationid() == null : this.getRelationid().equals(other.getRelationid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFileid() == null) ? 0 : getFileid().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getFilename() == null) ? 0 : getFilename().hashCode());
        result = prime * result + ((getExtendname() == null) ? 0 : getExtendname().hashCode());
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
        sb.append(", fileid=").append(fileid);
        sb.append(", status=").append(status);
        sb.append(", userid=").append(userid);
        sb.append(", createtime=").append(createtime);
        sb.append(", filename=").append(filename);
        sb.append(", extendname=").append(extendname);
        sb.append(", relationtype=").append(relationtype);
        sb.append(", relationid=").append(relationid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}