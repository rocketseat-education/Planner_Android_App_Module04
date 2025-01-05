package com.rocketseat.planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rocketseat.planner.R
import com.rocketseat.planner.databinding.FragmentUserRegistrationBinding
import com.rocketseat.planner.ui.viewmodel.UserRegistrationViewModel

class UserRegistrationFragment : Fragment() {

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    private val userRegistrationViewModel by viewModels<UserRegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSaveUser.setOnClickListener {
                userRegistrationViewModel.saveIsUserRegistered(isUserRegistered = true)
                navController.navigate(R.id.action_userRegistrationFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}