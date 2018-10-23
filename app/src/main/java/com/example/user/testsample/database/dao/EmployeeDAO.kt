package com.example.user.testsample.database.dao

import android.arch.persistence.room.*
import com.example.user.testsample.modle.Employee

@Dao
interface EmployeeDAO {
    @Query("SELECT * FROM employeeTb")
    fun allEmployees():List<Employee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee:Employee)

    @Query("delete from employeeTb where id=:id")
    fun deleteUser(id: String): Int
}