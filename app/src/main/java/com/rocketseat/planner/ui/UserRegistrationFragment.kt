package com.rocketseat.planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rocketseat.planner.R
import com.rocketseat.planner.data.utils.imageBitmapToBase64
import com.rocketseat.planner.data.utils.imageUriToBitmap
import com.rocketseat.planner.databinding.FragmentUserRegistrationBinding
import com.rocketseat.planner.ui.viewmodel.UserRegistrationViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserRegistrationFragment : Fragment() {

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    private val userRegistrationViewModel by viewModels<UserRegistrationViewModel>()

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if(uri != null) {
            val imageBitmap = requireContext().imageUriToBitmap(uri)
            imageBitmap?.let{
                val imageBase64 = imageBitmapToBase64(imageBitmap)
                userRegistrationViewModel.updateProfile(image = imageBase64)
                binding.ivAddPhoto.setImageURI(uri)
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Oops... Nenhuam foto selecionada",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            ivAddPhoto.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            tietName.addTextChangedListener { text ->
                userRegistrationViewModel.updateProfile(
                    name = text.toString()
                )
            }

            tietEmail.addTextChangedListener { text ->
                userRegistrationViewModel.updateProfile(
                    email = text.toString()
                )
            }

            tietPhone.addTextChangedListener { text ->
                userRegistrationViewModel.updateProfile(
                    phone = text.toString()
                )
            }

            btnSaveUser.setOnClickListener {
                userRegistrationViewModel.saveProfile()
                navController.navigate(R.id.action_userRegistrationFragment_to_homeFragment)
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            userRegistrationViewModel.isProfileValid.collect { isProfileValid ->
                binding.btnSaveUser.isEnabled = isProfileValid
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}