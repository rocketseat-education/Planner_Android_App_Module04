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

class PlannerActivityAdapter:
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
        holder.bind(plannerActivity = plannerActivity)
    }

    class ViewHolder(private val binding: ItemPlannerActitivityBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plannerActivity: PlannerActivity) {
            with(binding) {
                tvName.text = plannerActivity.name
                tvDateTime.text = plannerActivity.dateTimeString
                ivStatus.setImageResource(
                    if(plannerActivity.isCompleted) {
                        ivStatus.setColorFilter(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.lime_300
                            )
                        )
                        R.drawable.ic_circle_check
                    } else {
                        ivStatus.clearColorFilter()
                        R.drawable.ic_circle_dashed
                    }
                )
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
