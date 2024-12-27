package com.rocketseat.planner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rocketseat.planner.R
import com.rocketseat.planner.databinding.FragmentInitialLoadingBinding

class InitialLoadingFragment : Fragment() {

    private var _binding: FragmentInitialLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInitialLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // TODO: lógica da tela de loading inicial (ir para cadastro do usuário ou home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}