package com.lc.ifood.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lc.ifood.R
import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.labelId
import com.lc.ifood.ui.theme.IfoodRed
import com.lc.ifood.ui.theme.IfoodSurface
import com.lc.ifood.ui.theme.IfoodTextPrimary
import com.lc.ifood.ui.theme.IfoodTextSecondary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MealRecommendationBottomSheet(
    recommendation: MealRecommendation,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = IfoodSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(bottom = 16.dp)
        ) {
            val placeholder = painterResource(R.drawable.ic_meal_placeholder)
            AsyncImage(
                model = recommendation.mealImageUrl,
                contentDescription = recommendation.mealName,
                contentScale = ContentScale.Crop,
                placeholder = placeholder,
                error = placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(50),
                color = IfoodRed.copy(alpha = 0.1f)
            ) {
                Text(
                    text = stringResource(recommendation.mealType.labelId),
                    fontSize = 12.sp,
                    color = IfoodRed,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = recommendation.mealName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = IfoodTextPrimary
            )

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = IfoodTextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${recommendation.placeName} · ${recommendation.placeAddress}",
                    fontSize = 13.sp,
                    color = IfoodTextSecondary,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = recommendation.mealDescription,
                fontSize = 14.sp,
                color = IfoodTextSecondary
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "R$ %.2f".format(recommendation.mealPrice),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = IfoodRed
            )

            if (recommendation.preferences.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    recommendation.preferences.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = IfoodRed.copy(alpha = 0.08f)
                        ) {
                            Text(
                                text = tag,
                                fontSize = 11.sp,
                                color = IfoodRed,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = IfoodRed),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Comprar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
