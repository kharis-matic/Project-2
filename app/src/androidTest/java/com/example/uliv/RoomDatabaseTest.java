package com.example.uliv;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.ulive.data.AppDatabase;
import com.example.ulive.data.dao.UserDao;
import com.example.ulive.data.models.User;

import java.util.List;

    @RunWith(AndroidJUnit4.class)
    public class RoomDatabaseTest {
        private UserDao userDao;
        private AppDatabase db;
        private static final String TAG = "RoomDatabaseTest";

        @Before
        public void createDb() {
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            db = AppDatabase.getDatabase(context);
            userDao = db.userDao();
        }

        @After
        public void closeDb() {
            db.close();
        }

        @Test
        public void insertAndGetUser() {
            User user = new User("John", "Doe", "Male", "Renter", "john.doe@example.com", "1234567890");
            userDao.insert(user);

            List<User> allUsers = userDao.getAll();
            assertFalse(allUsers.isEmpty());
            assertEquals("John", allUsers.get(0).getFirstName());
            assertEquals("Doe", allUsers.get(0).getLastName());
            assertEquals("Male", allUsers.get(0).getGender());
            assertEquals("Renter", allUsers.get(0).getType());
            assertEquals("john.doe@example.com", allUsers.get(0).getEmail());
            assertEquals("1234567890", allUsers.get(0).getPhoneNumber());
        }
    }

