package com.example.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Store

interface EditContactStore : Store<EditContactStore.Intent, EditContactStore.State, EditContactStore.Label> {

    data class State (
        val username: String,
        val phone : String
    )

    sealed interface Label {
        object ContactSaved: Label
    }

    sealed interface Intent {

        data class ChangedUsername(val username: String) : Intent

        data class ChangedPhone(val phone: String) : Intent

        object SaveContact : Intent
    }
}