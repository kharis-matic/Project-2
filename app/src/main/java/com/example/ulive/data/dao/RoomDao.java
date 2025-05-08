package com.example.ulive.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.ulive.data.models.Room;


@Dao
public interface RoomDao {

    /**
     * Inserts a single room into the database.
     * */
    @Insert
    void insertRoom(Room room);

    /**
     * Inserts multiple rooms into the database.
     * */
    @Insert
    void insertRooms(Room... rooms);


}
