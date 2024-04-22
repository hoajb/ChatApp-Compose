package vn.hoanguyen.learn.chatapp.compose.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.hoanguyen.learn.chatapp.compose.R
import vn.hoanguyen.learn.chatapp.compose.data.User
import vn.hoanguyen.learn.chatapp.compose.features.home.components.UserList
import vn.hoanguyen.learn.chatapp.compose.components.BackPressWrapper
import vn.hoanguyen.learn.chatapp.compose.ui.state.UIState
import vn.hoanguyen.learn.chatapp.compose.ui.theme.GrayBG
import vn.hoanguyen.learn.chatapp.compose.viewmodels.HomeViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    homeViewmodel: HomeViewmodel = hiltViewModel(),
    navigateToProfile: (Int, Boolean) -> Unit,
    navigateToSearch: (String) -> Unit,
    navigateToChatRoom: (User) -> Unit,
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
) {
    BackPressWrapper {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White,
                    ),
                    title = {
                        Text(stringResource(R.string.app_name))
                    },
                    actions = {
                        IconButton(onClick = {
                            homeViewmodel.logOut()
                            popUpToLogin()
                        }) {
                            Icon(
                                Icons.Rounded.Logout,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp),
                                contentDescription = ""
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
            val userListState by homeViewmodel.userListUIState.collectAsState()
            LaunchedEffect(Unit) {
                homeViewmodel.loadUserList()
            }

            val state = rememberPullToRefreshState()
            if (state.isRefreshing) {
                LaunchedEffect(true) {
                    homeViewmodel.loadUserList()
                    state.endRefresh()
                }
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(GrayBG)
            ) {
                when (userListState) {
                    is UIState.Loading -> {
                        Box(
                            Modifier.fillMaxSize()
                        ) { CircularProgressIndicator(Modifier.align(Alignment.Center)) }
                    }

                    is UIState.Error -> Box(Modifier.fillMaxSize()) { Text("Error") }
                    is UIState.Loaded -> {
                        val list = (userListState as UIState.Loaded).data
                        AnimatedVisibility(visible = list.isNotEmpty()) {
                            UserList(list) { userSelected ->
                                navigateToChatRoom.invoke(userSelected)
                            }
                        }
                    }
                }

                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = state,
                )
            }
        }
    }
}
