package com.ykp.DiniBilmece.component

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykp.DiniBilmece.network.checkNetworkState
import com.ykp.IslamicSkillCheck.R

@Composable
fun NoConnectionPage() {

    val activity = (LocalLifecycleOwner.current as ComponentActivity)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .fillMaxSize()
            .padding(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.signal_disconnected),
            "disconnected",
            modifier = Modifier
                .size(90.dp),
        )
        Text(text = "İnternet bağlantısı yok", fontSize = 30.sp, textAlign = TextAlign.Center)
        ButtonBehavior(
            buttonText = "kapat", Modifier
                .padding(top = 25.dp), Color.LightGray.copy(0.3f),
            Icons.Filled.Close
        ) {
            activity.finish()
        }
    }

}