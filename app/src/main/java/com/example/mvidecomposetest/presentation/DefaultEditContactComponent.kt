package com.example.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.statekeeper.consume
import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.EditContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultEditContactComponent(
    componentContext: ComponentContext,
    private val contact: Contact,
    val onContactSaved: () -> Unit
) : EditContactComponent, ComponentContext by componentContext{
    private val _model = MutableStateFlow(
        stateKeeper.consume(KEY_STATE) ?: EditContactComponent.Model(username = contact.username, phone = contact.phone)
    )

    init {
        stateKeeper.register(KEY_STATE) {
            model.value
        }
    }

    private val repository = RepositoryImpl
    private val editContactUseCase = EditContactUseCase(repository)

    override val model: StateFlow<EditContactComponent.Model>
        get() = _model.asStateFlow()

    override fun onUserNameChanged(username: String) {
        _model.value = model.value.copy(username = username)
    }

    override fun onPhoneChanged(phone: String) {
        _model.value = model.value.copy(phone = phone)
    }

    override fun onSaveContactClicked() {
        val (username, phone) = model.value
        editContactUseCase(
            contact.copy(
                username = username,
                phone =  phone
            )
        )
        onContactSaved()
    }

    companion object {
        private const val KEY_STATE = "DefaultEditContactComponentImpl"
    }
}