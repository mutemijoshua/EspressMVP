package ke.co.espress.parcels.features.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ke.co.espress.parcels.features.authentication.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val isloggedIn: Boolean = false,
    val error: String? = null
)

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(
        email: String,
        password: String
    ){
        if (email.isBlank() || password.isBlank()){
            _uiState.value = LoginUiState(
                error = "Email and password are require"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(
                isLoading = true
            )

            val result = authRepository.login(
                email = email,
                password = password
            )

            result
                .onSuccess {
                    _uiState.value = LoginUiState(
                        isloggedIn = true
                    )
                }
                .onFailure { exception ->
                    _uiState.value = LoginUiState(
                        error = exception.message
                            ?: "Login failed"
                    )
                }
        }
    }
    fun clearErroe(){
        _uiState.value = _uiState.value.copy(
            error = null
        )
    }
}