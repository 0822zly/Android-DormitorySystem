package com.cnki.paotui.data;

import com.cnki.paotui.App;
import com.cnki.paotui.data.model.LoggedInUser;
import com.cnki.paotui.db.User;
import com.cnki.paotui.db.UserDao;
import com.cnki.paotui.utils.JDBC;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
         //   UserDao userDao=new UserDao(App.mIntance);
            User user = JDBC.getInstance().queryuser(username, password);
            if(user!=null) {
                App.user=user;
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                user.getUsername());
                return new Result.Success<>(fakeUser);
            }else {
                return new Result.Error(new IOException("Error logging in", null));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}