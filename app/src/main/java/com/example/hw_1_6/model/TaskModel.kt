package com.example.hw_1_6.model

import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.DialogTitle
import java.io.Serializable

data class TaskModel(
    var id:Int,
    var checkBox: Boolean,
    var title: String?=null,
    var task: String?=null
):Serializable
