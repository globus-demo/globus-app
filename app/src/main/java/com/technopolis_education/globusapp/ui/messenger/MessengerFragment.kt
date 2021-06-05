package com.technopolis_education.globusapp.ui.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolis_education.globusapp.databinding.FragmentMessengerBinding

class MessengerFragment : Fragment() {

    private lateinit var messengerViewModel: MessengerViewModel
    private var _binding: FragmentMessengerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        messengerViewModel =
            ViewModelProvider(this).get(MessengerViewModel::class.java)

        _binding = FragmentMessengerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}