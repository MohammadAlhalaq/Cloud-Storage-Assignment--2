package com.example.asignment1.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asignment1.adapter.model.Book
import com.example.my.AddBookActivity
import com.example.my.R
import java.util.*
import kotlin.collections.ArrayList

class RecyclerBookAdapter (val activity: Activity, val books: ArrayList<Book>): RecyclerView.Adapter<RecyclerBookAdapter.ViewMyHolder>(){
    inner class ViewMyHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var bookName = itemView.findViewById<TextView>(R.id.bookName)
        var author = itemView.findViewById<TextView>(R.id.author)
        var year = itemView.findViewById<TextView>(R.id.publish_year)
        var price = itemView.findViewById<TextView>(R.id.bookPrice)
        var ratingStar = itemView.findViewById<RatingBar>(R.id.ratingStar)
        var rating = itemView.findViewById<TextView>(R.id.ratingNumber)
        var editButton = itemView.findViewById<Button>(R.id.edit_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewMyHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.book_item, parent, false)
        return ViewMyHolder(root)
    }

    override fun onBindViewHolder(holder: ViewMyHolder, position: Int) {
        holder.bookName.text = books[position].name
        holder.author.text = books[position].author
        holder.year.text = (books[position].year!!.year + 1900).toString()
        holder.price.text = books[position].price.toString()
        holder.rating.text = books[position].rates.toString()
        holder.ratingStar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            if (b) {
                holder.rating.text = fl.toString()
            }
        }
        holder.ratingStar.rating = books[position].rates

        holder.editButton.setOnClickListener {
            activity.startActivity(Intent(activity, AddBookActivity:: class.java).putExtra("book", books[position]))
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }
}