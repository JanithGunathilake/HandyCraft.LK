import android.content.Context

class SessionManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    companion object {
        const val KEY_EMAIL = "email"
    }

    fun saveSession(email: String, password: String) {
        editor.putBoolean("isLoggedIn", true)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    fun getSession(): HashMap<String, String> {
        val sessionData = HashMap<String, String>()
        sessionData["email"] = sharedPreferences.getString("email", "").toString()
        sessionData["password"] = sharedPreferences.getString("password", "").toString()
        sessionData["id"] = sharedPreferences.getString("id","").toString()
        return sessionData
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun logout() {
        editor.clear().apply()
    }
    fun getEmail(): String? {
        return sharedPreferences.getString("email", null)
    }
}
