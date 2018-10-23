package com.example.user.testsample.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.testsample.R
import com.example.user.testsample.activities.MainActivity
import com.example.user.testsample.modle.Employee
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_list_items.view.*
import java.nio.file.Files.delete
import java.util.zip.Inflater

class NewsRecyclerAdapter(private val employee: ArrayList<Employee>, private val activity:Context ): RecyclerView.Adapter<NewsRecyclerAdapter.EmployeeHolder>() {

    override fun getItemCount(): Int {
        return employee.size
    }

    override fun onBindViewHolder(holder: NewsRecyclerAdapter.EmployeeHolder, position: Int) {
        val itemNews = employee[position]
        holder.bindNews(itemNews)
        holder.lnr_delete.setTag(position);
        holder.lnr_delete.setOnClickListener(View.OnClickListener { v: View? ->
            this.deleteItem(v?.getTag() as Int);
            // val pos:Int= v?.getTag() as Int;

        } );
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecyclerAdapter.EmployeeHolder{
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_items,parent,false);
      //  val inflatedView = parent.inflate(R.layout.layout_list_items)
        return EmployeeHolder(inflatedView)
    }

    class EmployeeHolder(v:View) : RecyclerView.ViewHolder(v) , View.OnClickListener {

        private var view : View = v
        private var news : Employee? = null
        val txt_name = view.txt_name
        val txt_salary = view.txt_salary
        val img_profile = view.img_profile
        val lnr_delete = view.lnr_delete
        val txt_age = view.txt_age
        init {
            v.setOnClickListener {this}

        }

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindNews(employee: Employee) {

            if(!employee.profile_image.isNullOrEmpty())
            Picasso.get()
                    .load(employee.profile_image)
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(view.img_profile);
            //Picasso.with(view.context).load(news.news_image_url).into(view.iv_cover)
            view.txt_age.text = "Age : "+employee.employee_age
            view.txt_salary.text = "Sal : "+employee.employee_salary
            view.txt_name.text = employee.employee_name
           // view.tv_source.text = news.news_source
        }
    }

    public fun deleteItem(pos:Int){
        activity as MainActivity
        activity.delete(pos);
    }

}