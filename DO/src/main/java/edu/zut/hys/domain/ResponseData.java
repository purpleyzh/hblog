package edu.zut.hys.domain;

import com.alibaba.fastjson.JSONObject;

/**
 * Author Hys
 * Date 2022/1/1 22:04
 * Project skate-backend
 */

public class ResponseData {
    private Integer code = null;
    private JSONObject data = new JSONObject();

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
