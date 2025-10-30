// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/ui/navigation/AppNavigation.kt
package com.bahaicommunity.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bahaicommunity.ui.feed.FeedScreen
import com.bahaicommunity.ui.createpost.CreatePostScreen
import com.bahaicommunity.ui.postdetail.PostDetailScreen
import com.bahaicommunity.ui.profile.ProfileScreen
import com.bahaicommunity.ui.chat.ChatScreen

sealed class Screen(val route: String) {
    object Feed : Screen("feed")
    object CreatePost : Screen("createPost")
    object PostDetail : Screen("postDetail/{postId}")
    object Profile : Screen("profile")
    object Chat : Screen("chat")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Feed.route
    ) {
        composable(Screen.Feed.route) {
            FeedScreen(
                onNavigateToCreatePost = { navController.navigate(Screen.CreatePost.route) },
                onNavigateToPostDetail = { postId ->
                    navController.navigate("${Screen.PostDetail.route.replace("{postId}", postId.toString())}")
                },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToChat = { navController.navigate(Screen.Chat.route) }
            )
        }
        
        composable(Screen.CreatePost.route) {
            CreatePostScreen(
                onNavigateBack = { navController.popBackStack() },
                onPostCreated = { navController.popBackStack() }
            )
        }
        
        composable(
            Screen.PostDetail.route,
            arguments = listOf(navArgument("postId") { type = NavType.LongType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getLong("postId") ?: 0L
            PostDetailScreen(
                postId = postId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Chat.route) {
            ChatScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
