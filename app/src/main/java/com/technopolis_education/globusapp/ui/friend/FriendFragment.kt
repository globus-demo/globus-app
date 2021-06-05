package com.technopolis_education.globusapp.ui.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.databinding.FragmentFriendBinding
import com.technopolis_education.globusapp.ui.friend.activity.FriendActivityFragment
import com.technopolis_education.globusapp.ui.friend.friends.FriendFriendsFragment

class FriendFragment : Fragment() {
    private lateinit var friendViewModel: FriendsViewModel
    private var _binding: FragmentFriendBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        // Friend info
        val friendNameSurname: TextView = binding.nameSurnameFieldFriend
        val friendEmail: TextView = binding.eMailFieldFriend
        val friendFriendsCount: TextView = binding.friendsCountFriend
        val friendActivityCount: TextView = binding.activityCountFriend

        friendNameSurname.text = "Friend Test1"
        friendEmail.text = "qwertyu@qwrt.ru"
        friendFriendsCount.text = "Friends: 10"
        friendActivityCount.text = "Activity: 10"

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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