package com.rocketseat.planner.presentation.ui.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rocketseat.planner.databinding.ItemPlannerActitivityBinding
import com.rocketseat.planner.domain.model.PlannerActivity
import com.rocketseat.planner.R

class PlannerActivityAdapter(
    private val onClickPlannerActivity: (selectedActivity: PlannerActivity) -> Unit,
    private val onChangeIsCompleted: (updateIsCompleted: Boolean, selectedActivity: PlannerActivity) -> Unit
):
    ListAdapter<PlannerActivity, PlannerActivityAdapter.ViewHolder>(PlannerActivityDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemPlannerActitivityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val plannerActivity = getItem(position)
        holder.bind(
            plannerActivity = plannerActivity,
            onClickPlannerActivity = onClickPlannerActivity,
            onChangeIsCompleted = onChangeIsCompleted
        )
    }

    class ViewHolder(private val binding: ItemPlannerActitivityBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            plannerActivity: PlannerActivity,
            onClickPlannerActivity: (selectedActivity: PlannerActivity) -> Unit,
            onChangeIsCompleted: (updateIsCompleted: Boolean, selectedActivity: PlannerActivity) -> Unit
        ) {
            with(binding) {
                clPlannerActivityContainer.setOnClickListener {
                    onClickPlannerActivity(plannerActivity)
                }
                tvName.text = plannerActivity.name
                tvDateTime.text = plannerActivity.dateTimeString
                ivIsCompleted.setImageResource(
                    if(plannerActivity.isCompleted) {
                        ivIsCompleted.setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.lime_300
                            )
                        )
                        R.drawable.ic_circle_check
                    } else {
                        ivIsCompleted.clearColorFilter()
                        R.drawable.ic_circle_dashed
                    }
                )
                ivIsCompleted.setOnClickListener {
                    onChangeIsCompleted(!plannerActivity.isCompleted, plannerActivity)
                }
            }
        }
    }
}

class PlannerActivityDiffCallBack: DiffUtil.ItemCallback<PlannerActivity>() {
    override fun areItemsTheSame(
        oldItem: PlannerActivity,
        newItem: PlannerActivity
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: PlannerActivity,
        newItem: PlannerActivity
    ): Boolean {
        return oldItem == newItem
    }
}
