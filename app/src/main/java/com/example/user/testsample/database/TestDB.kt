package com.example.user.testsample.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.user.testsample.database.dao.EmployeeDAO
import com.example.user.testsample.modle.Employee

@Database(entities = arrayOf(Employee::class),version = 1)
abstract class TestDB:RoomDatabase() {
    abstract fun employeeDao():EmployeeDAO
    companion object {
        private var INSTANCE: TestDB? = null
        fun getInstance(context: Context): TestDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context,
                        TestDB::class.java,
                        "testdb")
                        .build()
            }
            return INSTANCE as TestDB
        }
    }
}