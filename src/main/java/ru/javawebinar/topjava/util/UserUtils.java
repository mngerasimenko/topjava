package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtils {

    public static final List<User> users = Arrays.asList(
            new User("admin", "admin@topgava.ru", "password", Role.ADMIN),
            new User("user5", "user1@topgava.ru", "password", Role.USER),
            new User("user3", "user2@topgava.ru", "password", Role.USER),
            new User("user1", "user3@topgava.ru", "password", Role.USER),
            new User("user2", "user4@topgava.ru", "password", Role.USER),
            new User("user4", "user5@topgava.ru", "password", Role.USER)
    );
}
