package com.rocketseat.planner.presentation.ui

import android.content.DialogInterface
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rocketseat.planner.databinding.FragmentUpdatePlannerActivityDialogBinding
import com.rocketseat.planner.domain.model.PlannerActivity
import com.rocketseat.planner.domain.utils.createCalendarFromTimeInMillis
import com.rocketseat.planner.domain.utils.toPlannerActivityDate
import com.rocketseat.planner.domain.utils.toPlannerActivityTime
import com.rocketseat.planner.presentation.ui.component.PlannerActivityDatePickerDialogFragment
import com.rocketseat.planner.presentation.ui.component.PlannerActivityTimePickerDialogFragment
import com.rocketseat.planner.presentation.ui.extension.hideKeyboard
import com.rocketseat.planner.presentation.ui.viewmodel.PlannerActivityViewModel
import com.rocketseat.planner.presentation.ui.viewmodel.SetDate
import com.rocketseat.planner.presentation.ui.viewmodel.SetTime

class UpdatePlannerActivityDialogFragment(
    private val selectedActivity: PlannerActivity
) : BottomSheetDialogFragment() {

    private var _binding: FragmentUpdatePlannerActivityDialogBinding? = null
    private val binding get() = _binding!!

    private val plannerActivityViewModel: PlannerActivityViewModel by activityViewModels()

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        plannerActivityViewModel.clearSelectedActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdatePlannerActivityDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plannerActivityViewModel.setSelectedActivity(selectedActivity = selectedActivity)

        with(binding) {
            val selectActivityDateTimeCalendar =
                createCalendarFromTimeInMillis(timeMillis = selectedActivity.dateTime)

            tietUpdatedPlannerActivityName.setText(selectedActivity.name)
            tietUpdatedPlannerActivityDate.setText(selectActivityDateTimeCalendar.toPlannerActivityDate())
            tietUpdatedPlannerActivityTime.setText(selectActivityDateTimeCalendar.toPlannerActivityTime())

            tietUpdatedPlannerActivityName.doOnTextChanged{ text, _, _, _ ->
                if(text.toString().isEmpty()) {
                    tietUpdatedPlannerActivityName.clearFocus()
                    requireContext().hideKeyboard(fromView = tietUpdatedPlannerActivityName)
                }
                plannerActivityViewModel.updateSelectedActivity(
                    name = text.toString()
                )
            }

            tietUpdatedPlannerActivityDate.setOnClickListener {
                PlannerActivityDatePickerDialogFragment(
                    onConfirm = { year, month, dayOfMonth ->
                        val filledCalendar = Calendar.getInstance().apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, month)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }

                        tietUpdatedPlannerActivityDate.setText(filledCalendar.toPlannerActivityDate())
                        plannerActivityViewModel.updateSelectedActivity(
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

            tietUpdatedPlannerActivityTime.setOnClickListener {
                PlannerActivityTimePickerDialogFragment(
                    onConfirm = { hourOfDay, minute ->
                        val filledCalendar = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                        }

                        tietUpdatedPlannerActivityTime.setText(filledCalendar.toPlannerActivityTime())
                        plannerActivityViewModel.updateSelectedActivity(
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

            tvUpdatedPlannerActivityDelete.setOnClickListener {
                plannerActivityViewModel.delete(
                    uuid = selectedActivity.uuid
                )
                dialog?.dismiss()
            }

            btnSaveUpdatedPlannerActivity.setOnClickListener {
                plannerActivityViewModel.saveUpdatedSelectedActivity()
                dialog?.dismiss()
            }
        }
    }

    companion object {
        const val TAG = "UpdatePlannerActivityDialogFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}