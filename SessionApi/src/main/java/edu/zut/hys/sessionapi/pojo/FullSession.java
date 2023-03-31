package edu.zut.hys.sessionapi.pojo;

import edu.zut.hys.domain.Message;
import edu.zut.hys.domain.Session;
import edu.zut.hys.domain.UserSession;
import lombok.Data;

import java.util.List;

/**
 * Author Hys
 * Date 2022/2/19 2:06
 * Project AwakeningEra2
 */
@Data
public class FullSession {
    Session session;
    List<Message> messages;
    List<UserSession> userSessions;

    public FullSession(Session session, List<Message> messages, List<UserSession> userSessions) {
        this.session = session;
        this.messages = messages;
        this.userSessions = userSessions;
    }
}
