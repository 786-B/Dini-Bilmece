package com.ykp.DiniBilmece.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykp.DiniBilmece.component.Question

@Composable
fun TriviaHome(viewModel: QuestionsViewModel = hiltViewModel()) {
    Question(viewModel)

}