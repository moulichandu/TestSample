package com.example.user.testsample.activities

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.user.testsample.MyApp
import com.example.user.testsample.R
import com.example.user.testsample.adapter.NewsRecyclerAdapter
import com.example.user.testsample.database.TestDB
import com.example.user.testsample.impls.ApiService
import com.example.user.testsample.modle.Employee
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import android.os.AsyncTask



class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: NewsRecyclerAdapter
    private lateinit var listdata: ArrayList<Employee>
    private lateinit var pDialog: ProgressDialog;
    private lateinit var mDb:TestDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        listdata=ArrayList()
        mAdapter= NewsRecyclerAdapter(listdata,this);
        recyclerView?.layoutManager=linearLayoutManager;
        recyclerView?.adapter=mAdapter;
        mDb= TestDB.getInstance(applicationContext)

        if(!MyApp.myApp.checkNetwork())
        {

            fetchDataFromDb();
            Toast.makeText(applicationContext,"No Network available",Toast.LENGTH_SHORT).show();
            return;
        }
      val apiService=  ApiService.create();
        pDialog= ProgressDialog.show(this,"Loading","Loading");
     val call= apiService.fetChEmploye();
        Log.d("MainActivity", "MainActivity 1 " +  call)
        call.enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: retrofit2.Response<List<Employee>>?) {
                if (response != null) {

                    var list: List<Employee> = response.body()!!

                    listdata.addAll(list);
                    mAdapter.notifyDataSetChanged();

                    object : AsyncTask<Void, Void, Void>() {
                        override fun doInBackground(vararg voids: Void): Void? {
                            for (item: Employee in list.iterator()) {
                                mDb.employeeDao().insert(item);
                            }
                            return null
                        }
                    }.execute()

                    if(pDialog!=null&&pDialog.isShowing)
                    pDialog.dismiss()

                    /*
                    Toast.makeText(this@MainActivity, "List of Category  \n  " + msg, Toast.LENGTH_LONG).show()
                    txtDisplay.setText(msg + "")*/
                }

            }

            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                //                Log.e(TAG, t.toString());
                Log.d("MainActivity", "MainActivity" +  "error "+t.message)
                if(pDialog!=null&&pDialog.isShowing)
                    pDialog.dismiss()
            }
        })
    }
    private fun fetchDataFromDb()
    {
        pDialog= ProgressDialog.show(this,"Loading","Loading");
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                var list: List<Employee> = mDb.employeeDao().allEmployees();
                listdata.addAll(list);
                return null
            }
        }.execute()
        if(pDialog!=null&&pDialog.isShowing)
            pDialog.dismiss()
        mAdapter.notifyDataSetChanged();
    }

    public fun delete(pos:Int)
    {

        object : AsyncTask<Void, Void, Int>() {
            override fun doInBackground(vararg voids: Void): Int? {

                   val delete= mDb.employeeDao().deleteUser(listdata.get(pos).id!!);

                return delete;
            }

            override fun onPostExecute(result: Int?) {
                super.onPostExecute(result)

                Log.v("Item","Item "+result);
                    if(result!=null&&result>0) {

                        listdata.removeAt(pos)
                        mAdapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext,"Item Deleted",Toast.LENGTH_SHORT).show()
                }
            }
        }.execute()
    }

}
