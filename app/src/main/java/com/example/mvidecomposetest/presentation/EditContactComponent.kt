package com.example.mvidecomposetest.presentation

import android.os.Parcelable
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface EditContactComponent {

    val model: StateFlow<EditContactStore.State>

    fun onUserNameChanged(username: String)

    fun onPhoneChanged(phone: String)

    fun onSaveContactClicked()
}