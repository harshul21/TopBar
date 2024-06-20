package com.example.tabrow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.tabrow.ui.theme.TabRowTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tabItems = listOf(
            TabItem(
                title = "Web",
                unselectedIcon = R.drawable.outline_web_24,
                selectedIcon = R.drawable.baseline_web_24_filled
            ),
            TabItem(
                title = "Translate",
                unselectedIcon = R.drawable.outline_translate_24,
                selectedIcon = R.drawable.baseline_translate_24
            ),
            TabItem(
                title = "RealImage",
                unselectedIcon = R.drawable.outline_image_search_24,
                selectedIcon = R.drawable.baseline_image_search_24
            ),
            TabItem(
                title = "Q/A",
                unselectedIcon = R.drawable.outline_question_answer_24,
                selectedIcon = R.drawable.baseline_question_answer_24
            )
        )
        setContent {
            TabRowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        var selectedTabIndex by remember {
                            mutableIntStateOf(0)
                        }

                        var pagerState = rememberPagerState {
                            tabItems.size
                        }

                        LaunchedEffect(selectedTabIndex) {
                            pagerState.animateScrollToPage(selectedTabIndex)
                        }

                        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                            if(!pagerState.isScrollInProgress)
                                selectedTabIndex = pagerState.currentPage
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            TabRow(selectedTabIndex = selectedTabIndex) {
                                tabItems.forEachIndexed { index, item ->
                                    Tab(
                                        selected = selectedTabIndex == index,
                                        onClick = {
                                            selectedTabIndex = index
                                        },
                                        text = {
                                            Text(text = item.title)
                                        },
                                        icon = {
                                            Icon(
                                                painter = if (index == selectedTabIndex) {
                                                    painterResource(id = item.selectedIcon)
                                                } else painterResource(id = item.unselectedIcon),
                                                contentDescription = item.title
                                            )

                                        }
                                    )
                                }
                            }

                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                            ) {index ->
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(text = tabItems[index].title)
                                }

                            }
                        }

                    }
                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val unselectedIcon: Int,
    val selectedIcon: Int
)

