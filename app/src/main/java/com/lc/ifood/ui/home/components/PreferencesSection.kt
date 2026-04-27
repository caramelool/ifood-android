package com.lc.ifood.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lc.ifood.R
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.ui.composable.composable
import com.lc.ifood.ui.preference.delete.SwipeToDeletePreference
import com.lc.ifood.ui.preference.delete.rememberDeletePreferenceState
import com.lc.ifood.ui.theme.IfoodRed
import com.lc.ifood.ui.theme.IfoodSurface
import com.lc.ifood.ui.theme.IfoodTextPrimary
import com.lc.ifood.ui.theme.IfoodTextSecondary

@Composable
internal fun PreferencesSection(
    preferences: List<UserPreference>,
    onAddClick: () -> Unit
) {
    val deleteState = rememberDeletePreferenceState()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.home_preferences_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = IfoodTextPrimary
            )
            Surface(
                onClick = onAddClick,
                shape = CircleShape,
                color = IfoodRed,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.home_preferences_add_content_description),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        if (preferences.isEmpty()) {
            Text(
                text = stringResource(R.string.home_preferences_empty),
                fontSize = 14.sp,
                color = IfoodTextSecondary,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                preferences.forEach { preference ->
                    SwipeToDeletePreference(
                        preference = preference,
                        state = deleteState
                    ) {
                        PreferenceCard(
                            preference = preference,
                            onDeleteClick = { deleteState.requestDelete(preference) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferenceCard(
    preference: UserPreference,
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IfoodSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Text(
                    text = preference.label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = IfoodTextPrimary
                )
                if (preference.mealTypes.isNotEmpty()) {
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        preference.mealTypes.forEach { type ->
                            MealTypeChip(type)
                        }
                    }
                }
            }
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_preference_confirm),
                    tint = IfoodTextSecondary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun MealTypeChip(mealType: MealType) {
    Surface(
        shape = RoundedCornerShape(50),
        color = IfoodRed.copy(alpha = 0.1f)
    ) {
        Text(
            text = mealType.composable().sortLabel,
            fontSize = 11.sp,
            color = IfoodRed,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}
