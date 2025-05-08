package com.example.ulive.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ulive.data.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users")
    List<User> getAll();
}