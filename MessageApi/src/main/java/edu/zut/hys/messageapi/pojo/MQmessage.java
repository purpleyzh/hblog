package edu.zut.hys.messageapi.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Author Hys
 * Date 2022/2/18 23:45
 * Project AwakeningEra2
 */
@Data
public class MQmessage {
    String type;
    JSONObject body = new JSONObject();
}
