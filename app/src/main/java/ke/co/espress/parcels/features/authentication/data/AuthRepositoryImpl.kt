package ke.co.espress.parcels.features.authentication.data

import com.google.firebase.auth.FirebaseAuth
import ke.co.espress.parcels.features.authentication.domain.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository{
    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = firebaseAuth
                .signInWithEmailAndPassword(email,password)
                .await()
            val uid = result.user?.uid
                ?: return Result.failure(Exception("User is not found"))
            Result.success(uid)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun logout(){
        firebaseAuth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}