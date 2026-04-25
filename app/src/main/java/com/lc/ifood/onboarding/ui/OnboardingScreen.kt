package com.lc.ifood.onboarding.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lc.ifood.R
import com.lc.ifood.core.ui.theme.IfoodBackground
import com.lc.ifood.core.ui.theme.IfoodRed
import com.lc.ifood.core.ui.theme.IfoodTextPrimary
import com.lc.ifood.core.ui.theme.IfoodTextSecondary
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { uiState.pages.size })

    if (uiState.isOnboardCompleted.not()) {
        LaunchedEffect(pagerState.currentPage) {
            viewModel.onPageChanged(pagerState.currentPage)
        }
    }

    Scaffold(
        contentColor = IfoodBackground,
        floatingActionButton = {
            OnboardingFab(
                isOnboardCompleted = uiState.isOnboardCompleted,
                isVisible = uiState.isFabVisible,
                onClick = {
                    if (uiState.isOnboardCompleted) {
                        viewModel.completeOnboarding()
                        onOnboardingComplete()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.weight(1f)
                    ) { pageIndex ->
                        OnboardingPageContent(page = uiState.pages[pageIndex])
                    }

                    OnboardingPagerIndicator(
                        pageCount = uiState.pages.size,
                        currentPage = pagerState.currentPage
                    )
                }
            }
        }
    )
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = page.emoji, fontSize = 88.sp)
        Spacer(Modifier.height(40.dp))
        Text(
            text = stringResource(page.title),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = IfoodTextPrimary
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(page.subtitle),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = IfoodTextSecondary,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun OnboardingPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            Box(
                modifier = Modifier
                    .size(if (isSelected) 12.dp else 8.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) IfoodRed else Color(0xFFDDDDDD))
            )
        }
    }
}

@Composable
private fun OnboardingFab(
    isOnboardCompleted: Boolean,
    isVisible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            containerColor = IfoodRed,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            ),
            icon = {
                Icon(
                    imageVector = if (isOnboardCompleted) Icons.Default.Check else Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = if (isOnboardCompleted) stringResource(R.string.onboarding_btn_start) else stringResource(R.string.onboarding_btn_next)
                )
            },
            text = {
                Text(
                    text = if (isOnboardCompleted) stringResource(R.string.onboarding_btn_start) else stringResource(R.string.onboarding_btn_next),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        )
    }
}
