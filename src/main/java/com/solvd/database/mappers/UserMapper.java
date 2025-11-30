package com.solvd.database.mappers;

import com.solvd.database.model.User;

public interface UserMapper {

    void create(User user);

    User findById(long id);

    void update(User user);

    void delete(User user);
}
