package com.example.nutrichief.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.adapter.IngredientAdapter
import com.example.nutrichief.adapter.PostCommentAdapter
import com.example.nutrichief.datamodels.PostComment
import com.example.nutrichief.datamodels.RecipeIngredient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class PostDetailActivity : AppCompatActivity() {
    private lateinit var postCommentRecyclerView: RecyclerView
    private lateinit var adapter: PostCommentAdapter

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        postCommentRecyclerView = findViewById(R.id.comments_recycler_view)

        val dummy = mutableListOf<PostComment>(
            PostComment(1, 1, 1, "Comment 1"),
            PostComment(1, 2, 1, "Comment 2"),
            PostComment(1, 3, 1, "Comment 3")
        )

        adapter = PostCommentAdapter(dummy)
        postCommentRecyclerView.layoutManager = LinearLayoutManager(this@PostDetailActivity)
        postCommentRecyclerView.adapter = adapter

    }

    fun goBack(view: View) { onBackPressedDispatcher.onBackPressed() }
}