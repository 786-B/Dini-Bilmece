package com.ykp.DiniBilmece.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykp.DiniBilmece.component.ButtonBehavior
import com.ykp.DiniBilmece.component.NoConnectionPage
import com.ykp.DiniBilmece.component.Question
import com.ykp.DiniBilmece.network.checkNetworkState
import com.ykp.IslamicSkillCheck.R

@Composable
fun BilmeceHome(viewModel: QuestionsViewModel = hiltViewModel()) {

    var networkAvailable = checkNetworkState()

    if (networkAvailable) {
        Question(viewModel)

    } else {
        NoConnectionPage()

    }
}