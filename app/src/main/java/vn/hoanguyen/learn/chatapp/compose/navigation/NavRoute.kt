package vn.hoanguyen.learn.chatapp.compose.navigation

private object Path {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val SEARCH = "search"
    const val CHAT_ROOM = "chatRoom"
}

sealed class NavRoute(val path: String) {
    data object Splash : NavRoute(Path.SPLASH)
    data object Login : NavRoute(Path.LOGIN)
    data object Register : NavRoute(Path.REGISTER)
    data object Home : NavRoute(Path.HOME)

    data object Profile : NavRoute(Path.PROFILE) {
        val id = "id"
        val showDetails = "showDetails"
    }

    data object Search : NavRoute(Path.SEARCH) {
        val query = "query"
    }

    data object ChatRoom : NavRoute(Path.CHAT_ROOM) {
        val otherUserEmail = "otherUserEmail"
        val otherUserId = "otherUserId"
    }

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}