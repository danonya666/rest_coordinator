package DataBase;

import java.sql.*;
import java.util.logging.*;

public class JdbcInitialization {
    private Connection connection;
    private String url;
    private String name;
    private String password;

    public JdbcInitialization(){
        connection = null;
        //URL к базе состоит из протокола:подпротокола://[хоста]:[порта_СУБД]/[БД] и других_сведений
        url = "localhost\\SQLEXPRESS";
        //Имя пользователя БД
        name = "user";
        //Пароль
        password = "123456";
    }

    public void initialize(){


    }
}
