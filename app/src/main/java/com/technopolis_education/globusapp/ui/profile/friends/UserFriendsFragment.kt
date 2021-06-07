package com.technopolis_education.globusapp.ui.profile.friends

import android.app.AlertDialog
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
import com.technopolis_education.globusapp.databinding.FragmentProfileUserFriendsAddFriendBinding
import com.technopolis_education.globusapp.databinding.FragmentProfileUserFriendsBinding
import com.technopolis_education.globusapp.logic.adapter.empty.profile.friends.EmptyProfileFriendsAdapter
import com.technopolis_education.globusapp.logic.adapter.error.InternetErrorAdapter
import com.technopolis_education.globusapp.logic.adapter.profile.ProfileUserFriendsAdapter
import com.technopolis_education.globusapp.logic.check.InternetConnectionCheck
import com.technopolis_education.globusapp.logic.interfaces.profile.OnFriendClickListener
import com.technopolis_education.globusapp.model.FriendsInfo
import com.technopolis_education.globusapp.model.OneEmailRequest
import com.technopolis_education.globusapp.model.TwoEmailRequest
import com.technopolis_education.globusapp.model.UserToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFriendsFragment : Fragment(), OnFriendClickListener {

    private val webClient = WebClient().getApi()
    private var userFriendsList: ArrayList<FriendsInfo> = ArrayList()
    private lateinit var adapter: ProfileUserFriendsAdapter
    private lateinit var followReq: TwoEmailRequest

    private lateinit var userFriendsViewModel: UserFriendsViewModel
    private var _binding: FragmentProfileUserFriendsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userFriendsList.clear()
        userFriendsViewModel =
            ViewModelProvider(this).get(UserFriendsViewModel::class.java)

        _binding = FragmentProfileUserFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //------------------------------------------------------//
        // User email
        val userEmailSP = context?.getSharedPreferences("USER EMAIL", Context.MODE_PRIVATE)
        var userEmail = ""
        if (userEmailSP?.contains("UserEmail") == true) {
            userEmail = userEmailSP.getString("UserEmail", "").toString()
        }
        //------------------------------------------------------//


        //------------------------------------------------------//
        // User friends and filter
        val userFriends: RecyclerView = binding.userFriends

        val emailRequest = OneEmailRequest(
            userEmail
        )

        showUserFriends(emailRequest, userFriends)
        //------------------------------------------------------//

        //------------------------------------------------------//
        // Add friend
        val addFriendBtn = binding.addFriend
        addFriendBtn.setOnClickListener {
            if (!InternetConnectionCheck().isOnline(context)) {
                Toast.makeText(
                    context,
                    getString(R.string.error_no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                addFriend(userEmail, emailRequest, userFriends)
            }
        }
        //------------------------------------------------------//

        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showUserFriends(
        oneEmailRequest: OneEmailRequest,
        userFriends: RecyclerView
    ) {
        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()
            printFriends(userFriends, userFriendsList)
        } else {
            userFriendsList.clear()
            val callFollowersFromMe = webClient.followersFromMe(oneEmailRequest)
            callFollowersFromMe.enqueue(object : Callback<ArrayList<FriendsInfo>> {
                override fun onResponse(
                    call: Call<ArrayList<FriendsInfo>>,
                    response: Response<ArrayList<FriendsInfo>>
                ) {
                    for (i in 0 until response.body()!!.size) {
                        val userGroup = response.body()!![i]
                        userFriendsList.add(userGroup)
                    }
                    printFriends(userFriends, userFriendsList)
                }

                override fun onFailure(call: Call<ArrayList<FriendsInfo>>, t: Throwable) {
                    Log.i("test", "error $t")
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun printFriends(userFriends: RecyclerView, userFriendsList: ArrayList<FriendsInfo>) {
        userFriends.layoutManager =
            LinearLayoutManager(context)
        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()

            userFriends.adapter = InternetErrorAdapter()
        } else {
            if (userFriendsList.isEmpty()) {
                userFriends.adapter = EmptyProfileFriendsAdapter()
            } else {
                userFriends.adapter = ProfileUserFriendsAdapter(context, userFriendsList, this)

                adapter = ProfileUserFriendsAdapter(context, userFriendsList, this)
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

                userFriends.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Find and add a friend
    @RequiresApi(Build.VERSION_CODES.M)
    private fun addFriend(
        userEmail: String,
        oneEmailRequest: OneEmailRequest,
        userFriends: RecyclerView
    ) {
        val addNewFriend =
            FragmentProfileUserFriendsAddFriendBinding.inflate(LayoutInflater.from(context))
        val friendEmail = addNewFriend.friendEmail
        val submitBtn = addNewFriend.submitFindFriend

        val addDialog = AlertDialog.Builder(context)
        addDialog.setView(addNewFriend.root)

        submitBtn.setOnClickListener {
            if (!InternetConnectionCheck().isOnline(context)) {
                Toast.makeText(
                    context,
                    getString(R.string.error_no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (friendEmail.text.isEmpty()) {
                    Toast.makeText(context, "Enter friend email", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    followReq = TwoEmailRequest(
                        userEmail,
                        friendEmail.text.toString()
                    )

                    val callFollow = webClient.follow(followReq)

                    callFollow.enqueue(object : Callback<UserToken> {
                        @RequiresApi(Build.VERSION_CODES.M)
                        override fun onResponse(
                            call: Call<UserToken>,
                            response: Response<UserToken>
                        ) {
                            if (response.body()!!.status) {
                                showUserFriends(oneEmailRequest, userFriends)
                                Toast.makeText(context, "Friend request send", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context, response.body()!!.text, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }

                        override fun onFailure(call: Call<UserToken>, t: Throwable) {
                            Log.i("test", "error $t")
                        }
                    })
                }
            }
        }

        addDialog.create()
        addDialog.show()
    }

    override fun onFriendItemClick(item: FriendsInfo, position: Int) {
        val friendEmailSP = context?.getSharedPreferences("FRIEND EMAIL", Context.MODE_PRIVATE)
        friendEmailSP?.edit()
            ?.putString("FriendEmail", item.email)
            ?.apply()
        findNavController().navigate(R.id.action_profile_to_friendFragment)
    }
}