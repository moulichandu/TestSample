package com.example.user.testsample.modle

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "employeeTb")
class Employee {

    @PrimaryKey @NonNull
     var id:String?=null;
     var employee_name:String?=null;
     var employee_salary:String?=null;
     var employee_age:String?=null;
     var profile_image:String?=null;
}