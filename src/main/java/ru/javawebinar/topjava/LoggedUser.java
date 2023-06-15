package ru.javawebinar.topjava;

public class LoggedUser {
    private static Integer id;
    public static void setId(int userId) {
        id = userId;
    }

    public static int getId() {
        return id;
    }
}
