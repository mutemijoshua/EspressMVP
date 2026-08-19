package ke.co.espress.parcels.features.authentication.domain

interface AuthRepository{
    suspend fun login(
        email: String,
        password: String
    ): Result<String>

    suspend fun logout()

    fun isUserLoggedIn(): Boolean
}
//intentionally the domain layer does not know anything about firebase