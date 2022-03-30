package com.geekbrains.server.authorization;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class InMemoryAuthServiceImpl implements AuthService {
    private final Map<String, UserData> users;
    private static Connection connection;
    private static Statement stm;

    public InMemoryAuthServiceImpl() {
        users = new HashMap<>();
        /*users.put("login1", new UserData("login1", "password1", "first_user"));
        users.put("login2", new UserData("login2", "password2", "second_user"));
        users.put("login3", new UserData("login3", "password3", "third_user"));*/
        try (ResultSet rs = stm.executeQuery("SELECT * FROM users;")){
            while (rs.next()){
                users.put(rs.getString(2), new UserData(rs.getString(2), rs.getString(4), rs.getString(3)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void start() throws SQLException{

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ChatDB", "postgres", "J5e6DH2585");

        System.out.println("Сервис аутентификации инициализирован");
    }

    @Override
    public synchronized String getNickNameByLoginAndPassword(String login, String password) {
        UserData user = users.get(login);
        // Ищем пользователя по логину и паролю, если нашли то возвращаем никнэйм
        if (user != null && user.getPassword().equals(password)) {
            return user.getNickName();
        }

        return null;
    }

    @Override
    public void end() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    System.out.println("Сервис аутентификации отключен");
    }
}
