package com.travelwaka.app.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelwaka.app.datastore.TokenDataStore
import com.travelwaka.app.network.ApiClient
import com.travelwaka.app.network.model.LoginRequest
import com.travelwaka.app.network.model.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val context: Context) : ViewModel() {

    private val apiService = ApiClient.apiService
    private val tokenDataStore = TokenDataStore(context)

    // State untuk loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // State untuk error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // State untuk sukses
    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    // Data user
    val userName = tokenDataStore.userName
    val userEmail = tokenDataStore.userEmail
    val userRole = tokenDataStore.userRole

    // Login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.status) {
                    response.data?.let { data ->
                        tokenDataStore.saveAuth(
                            token = data.token,
                            role = data.user.role,
                            name = data.user.name,
                            email = data.user.email
                        )
                    }
                    _isSuccess.value = true
                } else {
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                _errorMessage.value = "Gagal terhubung ke server"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Register
    fun register(name: String, email: String, password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.register(
                    RegisterRequest(name, email, password, passwordConfirmation)
                )
                if (response.status) {
                    response.data?.let { data ->
                        tokenDataStore.saveAuth(
                            token = data.token,
                            role = data.user.role,
                            name = data.user.name,
                            email = data.user.email
                        )
                    }
                    _isSuccess.value = true
                } else {
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                _errorMessage.value = "Gagal terhubung ke server"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Logout
    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                tokenDataStore.token.collect { token ->
                    if (token != null) {
                        apiService.logout("Bearer $token")
                    }
                    tokenDataStore.clearAuth()
                    _isSuccess.value = false
                    onSuccess()
                    return@collect
                }
            } catch (e: Exception) {
                tokenDataStore.clearAuth()
                onSuccess()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetError() {
        _errorMessage.value = null
    }
}