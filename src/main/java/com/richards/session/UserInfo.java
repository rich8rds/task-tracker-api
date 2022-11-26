package com.richards.session;

import com.richards.entity.User;
import com.richards.exceptions.SessionIdNotFoundException;

import javax.servlet.http.HttpSession;

public class UserInfo {
    public static Long getUserSessionId(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("userDetails");
        if(user == null) throw new SessionIdNotFoundException("You are not logged in yet!");
        return user.getId();
    }
}
