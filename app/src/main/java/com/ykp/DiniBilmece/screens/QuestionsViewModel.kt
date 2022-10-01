package com.ykp.DiniBilmece.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykp.DiniBilmece.data.DataOrException
import com.ykp.DiniBilmece.model.QuestionItem
import com.ykp.DiniBilmece.repository.DatastoreRepo
import com.ykp.DiniBilmece.repository.QuestionRepository
import com.ykp.DiniBilmece.util.Constants.USER_SCORE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val repository: QuestionRepository,
    private val datastoreRepository: DatastoreRepo
) :
    ViewModel() {

    fun storeScore(score: Int) = runBlocking {
        datastoreRepository.putScore(USER_SCORE, score)
    }

    fun getUserScore(): Int = runBlocking {
        datastoreRepository.getScore(USER_SCORE)!!
    }

    fun clearPreferences(key: String) = runBlocking {
        datastoreRepository.clearPReferences(key)
    }


    val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(null, true, Exception(""))
        )

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestions()

            if (data.value.toString().isNotEmpty()) data.value.loading = false
            if(getUserScore()== null || getUserScore().toString().isEmpty()){
                storeScore(0)
            }


        }
    }
}