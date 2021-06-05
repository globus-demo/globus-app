package com.technopolis_education.globusapp.ui.profile.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.databinding.FragmentProfileUserStatisticsBinding

class UserStatisticsFragment : Fragment() {
    private val webClient = WebClient().getApi()

    private lateinit var userStatisticsViewModel: UserStatisticsViewModel
    private var _binding: FragmentProfileUserStatisticsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userStatisticsViewModel =
            ViewModelProvider(this).get(UserStatisticsViewModel::class.java)

        _binding = FragmentProfileUserStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}