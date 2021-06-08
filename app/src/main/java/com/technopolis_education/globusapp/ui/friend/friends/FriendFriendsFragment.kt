package com.technopolis_education.globusapp.ui.friend.friends

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.databinding.FragmentFriendFriendsBinding
import com.technopolis_education.globusapp.logic.adapter.empty.friends.friends.EmptyFriendFriendsAdapter
import com.technopolis_education.globusapp.logic.adapter.error.InternetErrorAdapter
import com.technopolis_education.globusapp.logic.adapter.profile.friends.FriendFriendsAdapter
import com.technopolis_education.globusapp.logic.check.InternetConnectionCheck
import com.technopolis_education.globusapp.logic.interfaces.profile.OnFriendClickListener
import com.technopolis_education.globusapp.model.FriendsInfo
import com.technopolis_education.globusapp.model.OneEmailRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendFriendsFragment : Fragment(), OnFriendClickListener {

    private val webClient = WebClient().getApi()
    private var friendFriendsList: ArrayList<FriendsInfo> = ArrayList()

    private lateinit var friendFriendsViewModel: FriendFriendsViewModel
    private var _binding: FragmentFriendFriendsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        friendFriendsList.clear()
        friendFriendsViewModel =
            ViewModelProvider(this).get(FriendFriendsViewModel::class.java)

        _binding = FragmentFriendFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //------------------------------------------------------//
        // Friend email
        val friendEmailSP = context?.getSharedPreferences("FRIEND EMAIL", Context.MODE_PRIVATE)
        var friendEmailText = ""
        if (friendEmailSP?.contains("FriendEmail") == true) {
            friendEmailText = friendEmailSP.getString("FriendEmail", "").toString()
        }
        Log.i("test", friendEmailText)
        //------------------------------------------------------//

        //------------------------------------------------------//
        // Friend friends and filter
        val friendFriends: RecyclerView = binding.friendFriends

        val emailRequest = OneEmailRequest(
            friendEmailText
        )

        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val callFollowersFromMe = webClient.followersFromMe(emailRequest)
            callFollowersFromMe.enqueue(object : Callback<ArrayList<FriendsInfo>> {
                override fun onResponse(
                    call: Call<ArrayList<FriendsInfo>>,
                    response: Response<ArrayList<FriendsInfo>>
                ) {
                    for (i in 0 until response.body()!!.size) {
                        val userGroup = response.body()!![i]
                        friendFriendsList.add(userGroup)
                    }
                    printFriends(friendFriends, friendFriendsList)
                }

                override fun onFailure(call: Call<ArrayList<FriendsInfo>>, t: Throwable) {
                    Log.i("test", "error $t")
                }
            })
        }
        //------------------------------------------------------//

        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun printFriends(friendFriends: RecyclerView, userFriendsList: ArrayList<FriendsInfo>) {
        friendFriends.layoutManager =
            LinearLayoutManager(context)
        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()

            friendFriends.adapter = InternetErrorAdapter()
        } else {
            if (userFriendsList.isEmpty()) {
                friendFriends.adapter = EmptyFriendFriendsAdapter()
            } else {
                friendFriends.adapter = FriendFriendsAdapter(friendFriendsList, this)

                val adapter = FriendFriendsAdapter(friendFriendsList, this)
                binding.friendsSearch.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(newFriend: String?): Boolean {
                        binding.friendsSearch.clearFocus()
                        return false
                    }

                    override fun onQueryTextChange(newFriend: String?): Boolean {
                        adapter.filter.filter(newFriend)
                        return false
                    }
                })

                friendFriends.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onFriendItemClick(item: FriendsInfo, position: Int) {
        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val friendEmailSP = context?.getSharedPreferences("FRIEND EMAIL", Context.MODE_PRIVATE)
            friendEmailSP?.edit()
                ?.putString("FriendEmail", item.email)
                ?.apply()
            findNavController().navigate(R.id.action_friendFragment_self)
        }
    }
}