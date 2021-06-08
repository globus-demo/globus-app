package com.technopolis_education.globusapp.ui.friend

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.databinding.FragmentFriendBinding
import com.technopolis_education.globusapp.logic.check.InternetConnectionCheck
import com.technopolis_education.globusapp.model.FriendInfoResponse
import com.technopolis_education.globusapp.model.FriendsInfo
import com.technopolis_education.globusapp.model.OneEmailRequest
import com.technopolis_education.globusapp.ui.friend.activity.FriendActivityFragment
import com.technopolis_education.globusapp.ui.friend.friends.FriendFriendsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendFragment : Fragment() {
    private val webClient = WebClient().getApi()
    private lateinit var friendViewModel: FriendsViewModel
    private var _binding: FragmentFriendBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        friendViewModel =
            ViewModelProvider(this).get(FriendsViewModel::class.java)

        _binding = FragmentFriendBinding.inflate(inflater, container, false)
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
        // Friend info
        val friendNameSurname: TextView = binding.nameSurnameFieldFriend
        val friendEmail: TextView = binding.eMailFieldFriend
        val friendFriendsCount: TextView = binding.friendsCountFriend
        val friendActivityCount: TextView = binding.activityCountFriend

        val emailRequest = OneEmailRequest(
            friendEmailText
        )

        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()

            friendNameSurname.text = "Friend Name"
            friendEmail.text = "Friend email"
            friendFriendsCount.text = "Friend friends"
            friendActivityCount.text = "Friend activity"

        } else {
            val callFriend = webClient.friendInfo(emailRequest)
            callFriend.enqueue(object : Callback<FriendInfoResponse> {
                override fun onResponse(
                    call: Call<FriendInfoResponse>,
                    response: Response<FriendInfoResponse>
                ) {
                    val friendInfo = response.body()?.objectToResponse

                    friendNameSurname.text = "${friendInfo?.name} ${friendInfo?.surname}"
                    friendEmail.text = friendInfo?.email
                }

                override fun onFailure(call: Call<FriendInfoResponse>, t: Throwable) {
                    Log.i("test", "error $t")
                }
            })

            val callFollowersFromMe = webClient.followersFromMe(emailRequest)
            callFollowersFromMe.enqueue(object : Callback<ArrayList<FriendsInfo>> {
                override fun onResponse(
                    call: Call<ArrayList<FriendsInfo>>,
                    response: Response<ArrayList<FriendsInfo>>
                ) {
                    friendFriendsCount.text = "Friends: ${response.body()?.size.toString()}"
                }

                override fun onFailure(call: Call<ArrayList<FriendsInfo>>, t: Throwable) {
                    Log.i("test", "error $t")
                }
            })

            friendActivityCount.text = "Activity: "
        }

        //------------------------------------------------------//

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.friend_container, FriendActivityFragment())
            ?.commit()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!InternetConnectionCheck().isOnline(context)) {
            Toast.makeText(
                context,
                getString(R.string.error_no_internet_connection),
                Toast.LENGTH_LONG
            ).show()

        } else {
            binding.friendsCountFriend.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.friend_container, FriendFriendsFragment())
                    ?.commit()
            }
            binding.activityCountFriend.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.friend_container, FriendActivityFragment())
                    ?.commit()
            }
        }
    }
}