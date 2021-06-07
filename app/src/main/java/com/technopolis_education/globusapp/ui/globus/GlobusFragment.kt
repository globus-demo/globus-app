package com.technopolis_education.globusapp.ui.globus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.databinding.FragmentGlobusBinding
import com.technopolis_education.globusapp.ui.globus.map.GlobusMapFragment

class GlobusFragment : Fragment() {

    private var _binding: FragmentGlobusBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGlobusBinding.inflate(inflater, container, false)
        val root: View = binding.root

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_layout, GlobusMapFragment())
            ?.commit()

        return root
    }
}