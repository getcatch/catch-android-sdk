package com.getcatch.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val ONE_SECOND_IN_MILLIS = 1000L
private const val FIVE_SECONDS_IN_MILLIS = 5 * ONE_SECOND_IN_MILLIS

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _complexFlow = flow {
        var counter = 0
        while (true) {
            delay(ONE_SECOND_IN_MILLIS)
            emit(
                counter++
            )
        }
    }

    val complexFLow = _complexFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(FIVE_SECONDS_IN_MILLIS),
        0
    )
}
