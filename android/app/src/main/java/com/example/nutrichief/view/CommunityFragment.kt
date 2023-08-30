package com.example.nutrichief.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.MainActivity
import com.example.nutrichief.R
import com.example.nutrichief.adapter.CommunityPostAdapter
import com.example.nutrichief.datamodels.CommunityPost
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommunityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommunityFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var communityRecyclerView: RecyclerView
    private lateinit var communityAdapter: CommunityPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        val searchBox = view.findViewById<SearchView>(R.id.community_search_view)
        val addPostBtn = view.findViewById<FloatingActionButton>(R.id.addPostBtn)

        communityRecyclerView = view.findViewById(R.id.community_recycler_view)
        communityRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapters
        communityAdapter = CommunityPostAdapter(mutableListOf(), )

        val posts = mutableListOf(
            CommunityPost(1, "Banh Mi", "good", "Chi Pham", 10, 0),
            CommunityPost(2, "Vegetable", "bad", "Tri Luu", 20, 10),
        )

        communityAdapter = CommunityPostAdapter(posts)
        communityRecyclerView.adapter = communityAdapter



        addPostBtn.setOnClickListener {
            val bottomDialog = PostAddingFragment()
            bottomDialog.show(requireActivity().supportFragmentManager, null)
        }

        return view
//        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//        val name = sharedPrefs.getString("user_name", "") ?: ""
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommunityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommunityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}