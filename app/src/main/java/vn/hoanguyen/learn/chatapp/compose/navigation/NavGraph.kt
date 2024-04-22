package vn.hoanguyen.learn.chatapp.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import vn.hoanguyen.learn.chatapp.compose.features.auth.LoginPage
import vn.hoanguyen.learn.chatapp.compose.features.auth.RegisterPage
import vn.hoanguyen.learn.chatapp.compose.features.chat.ChatRoomPage
import vn.hoanguyen.learn.chatapp.compose.features.home.HomePage
import vn.hoanguyen.learn.chatapp.compose.features.splash.SplashPage

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Splash.path
    ) {
        addSplashScreen(navController)
        addLoginScreen(navController)
        addRegisterScreen(navController)
        addHomeScreen(navController)
        addChatRoomScreen(navController)
    }
}

private fun NavOptionsBuilder.clear(path: String) {
    popUpTo(path) { inclusive = true }
    launchSingleTop = true
}

private fun NavGraphBuilder.addSplashScreen(
    navController: NavHostController,
) {
    composable(route = NavRoute.Splash.path) {
        SplashPage(
            onNavigateLogin = {
                navController.navigate(NavRoute.Login.path) { clear(NavRoute.Splash.path) }
            }, onNavigateHome = {
                navController.navigate(NavRoute.Home.path) { clear(NavRoute.Splash.path) }
            })
    }
}

private fun NavGraphBuilder.addLoginScreen(
    navController: NavHostController,
) {
    composable(route = NavRoute.Login.path) {
        LoginPage(
            navigateToRegister = {
                navController.navigate(NavRoute.Register.path) {
                    clear(NavRoute.Login.path)
                }
            }, navigateToHome = {
                navController.navigate(NavRoute.Home.path) {
                    clear(NavRoute.Login.path)
                }
            })
    }
}

private fun NavGraphBuilder.addRegisterScreen(
    navController: NavHostController,
) {
    composable(route = NavRoute.Register.path) {
        RegisterPage(
            navigateToLogin = {
                navController.navigate(NavRoute.Login.path) {
                    clear(NavRoute.Register.path)
                }
            }, navigateToHome = {
                navController.navigate(NavRoute.Home.path) {
                    clear(NavRoute.Register.path)
                }
            })
    }
}

private fun NavGraphBuilder.addHomeScreen(
    navController: NavHostController,
) {
    composable(route = NavRoute.Home.path) {
        HomePage(
            navigateToProfile = { id, showDetails ->
                navController.navigate(
                    NavRoute.Profile.withArgs(
                        id.toString(),
                        showDetails.toString()
                    )
                )
            },
            navigateToSearch = { query ->
                navController.navigate(NavRoute.Search.withArgs(query))
            },
            navigateToChatRoom = { user ->
                navController.navigate(
                    route = NavRoute.ChatRoom.withArgs(user.uuid, user.email),
                )
            },
            popBackStack = { navController.popBackStack() },
            popUpToLogin = {
                navController.navigate(NavRoute.Login.path) {
                    clear(NavRoute.Home.path)
                }
            },
        )
    }
}

private fun NavGraphBuilder.addChatRoomScreen(
    navController: NavHostController,
) {
    composable(
        route = "${NavRoute.ChatRoom.path}/{${NavConstants.ArgUserId}}/{${NavConstants.ArgUserEmail}}",
        arguments = listOf(
            navArgument(NavConstants.ArgUserId) { type = NavType.StringType },
            navArgument(NavConstants.ArgUserEmail) { type = NavType.StringType },
        )
    ) { backStackEntry ->
        ChatRoomPage(
            otherUserEmail = backStackEntry.arguments?.getString(NavConstants.ArgUserEmail)
                .orEmpty(),
            navigationBack = { navController.popBackStack() }
        )
    }
}
