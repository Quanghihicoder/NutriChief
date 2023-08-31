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

        val postId = intent.getIntExtra("post_id", 1)

        val dummy = mutableListOf<PostComment>(
            PostComment(postId, 1, "Trang Le", "I agree"),
            PostComment(postId, 2, "Chi Kim", "Add friend"),
            PostComment(postId, 3, "Tri Luu", "Hello")
        )

        adapter = PostCommentAdapter(dummy)
        postCommentRecyclerView.layoutManager = LinearLayoutManager(this@PostDetailActivity)
        postCommentRecyclerView.adapter = adapter

    }

    fun goBack(view: View) { onBackPressedDispatcher.onBackPressed() }
}