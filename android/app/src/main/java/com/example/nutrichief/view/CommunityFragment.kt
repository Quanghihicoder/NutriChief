package com.example.nutrichief.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.adapter.CommunityPostAdapter
import com.example.nutrichief.adapter.CommunityRoomAdapter
import com.example.nutrichief.adapter.ExpertAdapter
import com.example.nutrichief.datamodels.CommunityPost
import com.example.nutrichief.datamodels.CommunityRoom
import com.example.nutrichief.datamodels.Expert
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
class CommunityFragment : Fragment(), CommunityPostAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var communityRecyclerView: RecyclerView

    private lateinit var communityAdapter: CommunityPostAdapter
    private lateinit var expertAdapter: ExpertAdapter
    private lateinit var communityRoomAdapter: CommunityRoomAdapter

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

        val roomsBtn = view.findViewById<Button>(R.id.button_rooms)
        val blogBtn = view.findViewById<Button>(R.id.button_blogs)
        val expertBtn = view.findViewById<Button>(R.id.button_expert_consultation)

        var featureDesc: TextView = view.findViewById(R.id.community_feature_slogan)

        //set button colors
        roomsBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))
        blogBtn.setBackgroundColor(Color.parseColor("#6173DF"))
        expertBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))

        communityRecyclerView = view.findViewById(R.id.community_recycler_view)
        communityRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val posts = mutableListOf(
            CommunityPost(1, "Banh Mi", "good", "Chi Pham", 10, 0),
            CommunityPost(2, "Vegetable", "bad", "Tri Luu", 20, 10),
        )

        communityAdapter = CommunityPostAdapter(posts, this)
        communityRecyclerView.adapter = communityAdapter

        val experts = mutableListOf(
            Expert(1, "Kevin Strong", "Body builing expert"),
            Expert(2, "Jack Wise", "Mental consulting expert"),
        )

        val rooms = mutableListOf(
            CommunityRoom(1, "Ramen Lovers", "For ramen enthusiasts around the world", "https://www.modernfarmhouseeats.com/wp-content/uploads/2021/03/chili-lime-shrimp-ramen-2-500x500.jpg", 613, 12),
            CommunityRoom(2, "Food Art", "Eating is simple, Art is style", "https://nerdinstilettoscom.files.wordpress.com/2016/06/fullsizerender6.jpg", 532, 5),
            CommunityRoom(3, "Vegans", "Veggies are besties", "https://images.everydayhealth.com/images/what-is-a-vegan-diet-benefits-food-list-beginners-guide-alt-1440x810.jpg", 324, 8),
        )

        addPostBtn.setOnClickListener {
            val bottomDialog = PostAddingFragment()
            bottomDialog.show(requireActivity().supportFragmentManager, null)
        }

        blogBtn.setOnClickListener {
            blogBtn.setBackgroundColor(Color.parseColor("#6173DF"))
            roomsBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))
            expertBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))
            communityAdapter = CommunityPostAdapter(posts, this)
            communityRecyclerView.adapter = communityAdapter
            featureDesc.text = "Connect with others"
        }

        roomsBtn.setOnClickListener {
            roomsBtn.setBackgroundColor(Color.parseColor("#6173DF"))
            blogBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))
            expertBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))
            communityRoomAdapter = CommunityRoomAdapter(rooms)
            communityRecyclerView.adapter = communityRoomAdapter
            featureDesc.text = "Discover new communities"
        }

        expertBtn.setOnClickListener {
            expertBtn.setBackgroundColor(Color.parseColor("#6173DF"))
            roomsBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))
            blogBtn.setBackgroundColor(Color.parseColor("#A0A0A1"))
            expertAdapter = ExpertAdapter(experts)
            communityRecyclerView.adapter = expertAdapter
            featureDesc.text = "Available Experts"
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

    override fun onCommentClick(post: CommunityPost) {
        val intent = Intent (activity, PostDetailActivity::class.java)
        intent.putExtra("post_id", 1)
        startActivity(intent)
    }


}