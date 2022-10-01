package com.ykp.DiniBilmece.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Score(private val context: Context) {

    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Score")
        val USER_SCORE_KEY = intPreferencesKey("user_score")
        // stringPreferencesKey("user_score")
    }

    // to get the score
    val getScore: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_SCORE_KEY] ?: 0
        }

    // to save the email
    suspend fun saveScore(score: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_SCORE_KEY] = score
        }
    }
}