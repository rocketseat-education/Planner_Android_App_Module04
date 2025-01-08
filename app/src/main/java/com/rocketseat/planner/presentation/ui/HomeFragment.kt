package com.rocketseat.planner.presentation.ui

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.rocketseat.planner.R
import com.rocketseat.planner.databinding.FragmentHomeBinding
import com.rocketseat.planner.domain.utils.imageBase64ToBitmap
import com.rocketseat.planner.domain.utils.toPlannerActivityDate
import com.rocketseat.planner.domain.utils.toPlannerActivityDateTime
import com.rocketseat.planner.domain.utils.toPlannerActivityTime
import com.rocketseat.planner.presentation.ui.component.PlannerActivityAdapter
import com.rocketseat.planner.presentation.ui.component.PlannerActivityDatePickerDialogFragment
import com.rocketseat.planner.presentation.ui.component.PlannerActivityTimePickerDialogFragment
import com.rocketseat.planner.presentation.ui.extension.hideKeyboard
import com.rocketseat.planner.presentation.ui.viewmodel.PlannerActivityViewModel
import com.rocketseat.planner.presentation.ui.viewmodel.SetDate
import com.rocketseat.planner.presentation.ui.viewmodel.SetTime
import com.rocketseat.planner.presentation.ui.viewmodel.UserRegistrationViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val userRegistrationViewModel by activityViewModels<UserRegistrationViewModel>()
    private val plannerActivityViewModel by activityViewModels<PlannerActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        with(binding) {
            plannerActivityViewModel.fetchActivities()

            clHomeContainer.setOnClickListener {
                tietPlannerActivityName.clearFocus()
                requireContext().hideKeyboard(fromView = tietPlannerActivityName)
            }

            tietPlannerActivityName.doOnTextChanged{ text, _, _, _ ->
                if(text.toString().isEmpty()) {
                    tietPlannerActivityName.clearFocus()
                    requireContext().hideKeyboard(fromView = tietPlannerActivityName)
                }
                plannerActivityViewModel.updateNewActivity(
                    name = text.toString()
                )
            }

            tietNewPlannerActivityDate.setOnClickListener {
                PlannerActivityDatePickerDialogFragment(
                    onConfirm = { year, month, dayOfMonth ->
                        val filledCalendar = Calendar.getInstance().apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, month)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }

                        tietNewPlannerActivityDate.setText(filledCalendar.toPlannerActivityDate())
                        plannerActivityViewModel.updateNewActivity(
                            date = SetDate(
                                year = year,
                                month = month,
                                dayOfMonth = dayOfMonth
                            )
                        )
                    },
                    onCancel = {}
                ).show(
                    childFragmentManager,
                    PlannerActivityDatePickerDialogFragment.TAG
                )
            }

            tietNewPlannerActivityTime.setOnClickListener {
                PlannerActivityTimePickerDialogFragment(
                    onConfirm = { hourOfDay, minute ->
                        val filledCalendar = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                        }

                        tietNewPlannerActivityTime.setText(filledCalendar.toPlannerActivityTime())
                        plannerActivityViewModel.updateNewActivity(
                            time = SetTime(
                                hourOfDay = hourOfDay,
                                minute = minute
                            )
                        )
                    },
                    onCancel = {}
                ).show(
                    childFragmentManager,
                    PlannerActivityTimePickerDialogFragment.TAG
                )
            }

            btnSaveNewPlannerActivity.setOnClickListener {
                plannerActivityViewModel.saveNewActivity(
                    onSuccess = {
                        clearNewPlannerActivityFields()
                    },
                    onError = {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.oops_houve_uma_falha_ao_salvar_a_atividade),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        }
    }

    private fun FragmentHomeBinding.clearNewPlannerActivityFields() {
        tietPlannerActivityName.text = null
        tietNewPlannerActivityDate.text = null
        tietNewPlannerActivityTime.text = null
        requireContext().hideKeyboard(fromView = tietPlannerActivityName)
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            launch {
                userRegistrationViewModel.profile.collect { profile ->
                    binding.tvUserName.text = getString(R.string.ola_usuario, profile.name)
                    imageBase64ToBitmap(base64String = profile.image)?.let { imageBitmap ->
                        binding.ivUserPhoto.setImageBitmap(imageBitmap)
                    }
                }
            }
            launch {
                userRegistrationViewModel.isTokenValid.distinctUntilChanged {
                        old, new -> old == new
                }.collect { isTokenValid ->
                    if(isTokenValid == false) showNewTokenSnackbar()
                }
            }
            launch {
                plannerActivityViewModel.activities.collect { activities ->
                    with(binding) {
                        if(rvPlannerActivities.adapter == null) {
                            rvPlannerActivities.adapter = PlannerActivityAdapter(
                                onClickPlannerActivity = { selectedActivity ->
                                    UpdatePlannerActivityDialogFragment(
                                        selectedActivity = selectedActivity,
                                    ).show(
                                        childFragmentManager,
                                        UpdatePlannerActivityDialogFragment.TAG
                                    )
                                },
                                onChangeIsCompleted = { updateIsCompleted, selectedActivity ->
                                    plannerActivityViewModel.updateIsCompleted(
                                        uuid = selectedActivity.uuid,
                                        isCompleted = updateIsCompleted
                                    )
                                }
                            )
                        }
                        (rvPlannerActivities.adapter as PlannerActivityAdapter).submitList(
                            activities
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showNewTokenSnackbar() {
        Snackbar.make(requireView(), "Oops... O seu token expirou.", Snackbar.LENGTH_INDEFINITE)
            .setAction("OBTER NOVO TOKEN") {
                userRegistrationViewModel.obtainNewToken()
            }
            .setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.lime_300
                )
            ).show()
    }

}