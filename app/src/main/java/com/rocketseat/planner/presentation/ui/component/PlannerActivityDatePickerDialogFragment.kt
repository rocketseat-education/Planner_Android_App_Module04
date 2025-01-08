package com.rocketseat.planner.presentation.ui.component

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.rocketseat.planner.R

class PlannerActivityDatePickerDialogFragment(
    private val initialDate: Calendar? = null,
    private val onConfirm: (Int, Int, Int) -> Unit,
    private val onCancel: () -> Unit
): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = initialDate ?: Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val customDatePickerDialog = DatePickerDialog(
            requireContext(),
            this,
            year,
            month,
            day
        ).setupPlannerDatePicker(minDate = calendar.timeInMillis)

        return customDatePickerDialog
    }

    fun DatePickerDialog.setupPlannerDatePicker(minDate: Long): DatePickerDialog =
        this.apply {
            datePicker.minDate = minDate

            window?.decorView?.setBackgroundColor(ContextCompat.getColor(context, R.color.lime_950))

            datePicker.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )

            setButton(
                DialogInterface.BUTTON_POSITIVE,
                getString(R.string.confirmar)
            ) { _, _ ->
                onConfirm(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            }

            setButton(
                DialogInterface.BUTTON_NEGATIVE,
                getString(R.string.cancelar)
            ) { _, _ ->
                onCancel()
            }
        }

    override fun onDateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        // Obs: só seria utilizado caso não houvesse a sobreescrita do botão primário de confirmação.
    }

    companion object {
        const val TAG = "PlannerActivityDatePickerDialogFragment"
    }
}