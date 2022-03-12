package com.example.my

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.asignment1.adapter.model.Book
import com.example.my.databinding.ActivityAddBookBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class AddBookActivity : AppCompatActivity() {
    val db = Firebase.firestore
    lateinit var binding: ActivityAddBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.getSerializableExtra("book") !== null) {
            var book = intent.getSerializableExtra("book") as Book
            binding.editOrAddBook.text = "Edit Book"
            binding.editOrAddAddBtn.text = "Edit Book"
            binding.editOrAddBookName.setText(book.name)
            binding.editOrAddBookAuthor.setText(book.author)
            binding.editOrAddBookLaunchYear.setText((book.year!!.year + 1900).toString())
            binding.editOrAddBookPrice.setText(book.price.toString())
            binding.editOrAddBookRatingBar.rating = book.rates
            binding.editOrAddAddBtn.setOnClickListener {
                addOrEditMethod(true)
            }
            binding.editOrAddDeleteBtn.setOnClickListener {
                deleteBook(book.id!!)
            }
//            edit and delete
        } else {
            binding.editOrAddDeleteBtn.visibility = View.GONE
            Toast.makeText(this, "else done", Toast.LENGTH_SHORT).show()

            binding.editOrAddAddBtn.setOnClickListener {
                addOrEditMethod(false)
            }
        }
    }

    fun deleteBook(id: String) {
        db.collection("books").document(id).delete().addOnSuccessListener {
            Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener { e ->
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun addbook(book: Book) {

        // Create a new user with a first, middle, and last name
        val book = hashMapOf(
            "name" to book.name,
            "author" to book.author,
            "price" to book.price,
            "rates" to book.rates,
            "year" to Timestamp(book.year!!)
        )

        // Add a new document with a generated ID
        db.collection("books")
            .add(book)
            .addOnSuccessListener {
                Toast.makeText(this, "book done", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

            }
    }

    fun updateBook(book: Book) {

        // Create a new user with a first, middle, and last name
        val updateBook = hashMapOf(
            "name" to book.name,
            "author" to book.author,
            "price" to book.price,
            "rates" to book.rates,
            "year" to Timestamp(book.year!!)
        )

        // Add a new document with a generated ID
        db.collection("books")
            .document(book.id!!)
            .update(updateBook as Map<String, Any>).addOnSuccessListener {
                Toast.makeText(this, "update book done", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

            }
    }

    fun addOrEditMethod(isEdit: Boolean) {
        if (
            binding.editOrAddBookName.text.isNotEmpty() &&
            binding.editOrAddBookAuthor.text.isNotEmpty() &&
            binding.editOrAddBookLaunchYear.text.isNotEmpty() &&
            binding.editOrAddBookPrice.text.isNotEmpty() &&
            binding.editOrAddBookRatingBar.rating != 0f
        ) {
            var dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val year = binding.editOrAddBookLaunchYear.text.toString().toInt() + 1
            val month = 0
            val day = 0
            val date = dateFormat.parse("$year-$month-$day")
            Toast.makeText(this, "binding done", Toast.LENGTH_SHORT).show()

            if (isEdit) {
                updateBook(
                    Book(
                        (intent.getSerializableExtra("book") as Book).id,
                        binding.editOrAddBookName.text.toString(),
                        binding.editOrAddBookAuthor.text.toString(),
                        date,
                        binding.editOrAddBookRatingBar.rating.toString().toFloat(),
                        binding.editOrAddBookPrice.text.toString().toInt()
                    )
                )
            } else addbook(
                Book(
                    null,
                    binding.editOrAddBookName.text.toString(),
                    binding.editOrAddBookAuthor.text.toString(),
                    date,
                    binding.editOrAddBookRatingBar.rating.toString().toFloat(),
                    binding.editOrAddBookPrice.text.toString().toInt()
                )
            )
        }
    }
}