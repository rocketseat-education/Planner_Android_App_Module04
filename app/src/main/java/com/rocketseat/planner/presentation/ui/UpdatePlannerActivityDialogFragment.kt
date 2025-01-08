package com.rocketseat.planner.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rocketseat.planner.R
import com.rocketseat.planner.databinding.FragmentUpdatePlannerActivityDialogBinding
import com.rocketseat.planner.domain.model.PlannerActivity
import com.rocketseat.planner.domain.utils.createCalendarFromTimeInMillis
import com.rocketseat.planner.domain.utils.toPlannerActivityDate
import com.rocketseat.planner.domain.utils.toPlannerActivityTime

class UpdatePlannerActivityDialogFragment(
    private val selectedActivity: PlannerActivity
) : BottomSheetDialogFragment() {

    private var _binding: FragmentUpdatePlannerActivityDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdatePlannerActivityDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val selectActivityDateTimeCalendar =
                createCalendarFromTimeInMillis(timeMillis = selectedActivity.dateTime)

            tietUpdatedPlannerActivityName.setText(selectedActivity.name)
            tietUpdatedPlannerActivityDate.setText(selectActivityDateTimeCalendar.toPlannerActivityDate())
            tietUpdatedPlannerActivityTime.setText(selectActivityDateTimeCalendar.toPlannerActivityTime())
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