package com.example.hw_1_6.model

import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.DialogTitle

data class TaskModel(
    var checkBox: Boolean,
    val title: String?=null,
    val task: String?=null
)
