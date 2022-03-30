package com.geekbrains.server.authorization;

import java.sql.SQLException;

public interface AuthService {
    void start() throws SQLException;
    String getNickNameByLoginAndPassword(String login, String password);
    void end();
}
