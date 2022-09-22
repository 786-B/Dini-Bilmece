package com.ykp.DiniBilmece.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykp.DiniBilmece.component.ButtonBehavior
import com.ykp.IslamicSkillCheck.R

@Composable
fun NoInternet() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.signal_disconnected),
            "disconnected",
            modifier = Modifier
                .size(90.dp),
        )
        Text(text = "Bağlantı yok", fontSize = 30.sp)
        ButtonBehavior(
            buttonText = "Yeniden dene",
            modifier = Modifier.padding(37.dp),
            buttonColor = Color.Gray,
            icon = Icons.Default.Cached
        ) {

        }
    }
}