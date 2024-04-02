package com.example.mvidecomposetest.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.statekeeper.consume
import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.AddContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultAddContactComponent(
    componentContext: ComponentContext,
    val onContactSaved: () -> Unit
) : AddContactComponent, ComponentContext  by componentContext{
    private val _model = MutableStateFlow(
        stateKeeper.consume(KEY_STATE) ?: AddContactComponent.Model(username = "", phone = "")
    )

    init {
        stateKeeper.register(KEY_STATE) {
            model.value
        }
    }

    private val repository = RepositoryImpl
    private val addContactUseCase = AddContactUseCase(repository)

    override val model: StateFlow<AddContactComponent.Model>
        get() = _model.asStateFlow()

    override fun onUserNameChanged(username: String) {
        _model.value = model.value.copy(username = username)
    }

    override fun onPhoneChanged(phone: String) {
        _model.value = model.value.copy(phone = phone)
    }

    override fun onSaveContactClicked() {
        val (username, phone) = model.value
        addContactUseCase(username, phone)
        onContactSaved()
    }

    companion object {
        private const val KEY_STATE = "DefaultAddContactComponent"
    }
}