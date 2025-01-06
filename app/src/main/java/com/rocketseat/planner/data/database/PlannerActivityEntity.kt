package com.rocketseat.planner.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlannerActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val uuid: String,
    val name: String,
    val dateTime: Long,
    @ColumnInfo("is_completed")
    val isCompleted: Boolean
)
