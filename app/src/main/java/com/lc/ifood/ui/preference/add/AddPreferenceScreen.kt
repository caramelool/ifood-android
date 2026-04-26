package com.lc.ifood.ui.preference.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lc.ifood.R
import com.lc.ifood.ui.theme.IfoodBackground
import com.lc.ifood.ui.theme.IfoodRed
import com.lc.ifood.ui.theme.IfoodSurface
import com.lc.ifood.ui.theme.IfoodTextPrimary
import com.lc.ifood.ui.theme.IfoodTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPreferenceScreen(
    onBack: () -> Unit,
    viewModel: AddPreferenceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_preference_title),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.common_back_content_description),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = IfoodRed)
            )
        },
        containerColor = IfoodBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.label,
                onValueChange = viewModel::onLabelChange,
                label = { Text(stringResource(R.string.add_preference_label_field)) },
                placeholder = { Text(stringResource(R.string.add_preference_label_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = IfoodRed,
                    focusedLabelColor = IfoodRed,
                    cursorColor = IfoodRed
                )
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.add_preference_meal_types_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = IfoodTextPrimary
            )

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IfoodSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    uiState.mealOptions.forEach { meal ->
                        MealTypeCheckboxRow(
                            label = meal.label,
                            checked = meal in uiState.selectedMeals,
                            onCheckedChange = { viewModel.toggleMeal(meal) }
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { viewModel.save(onBack) },
                enabled = uiState.canSave && !uiState.isSaving,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = IfoodRed),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (uiState.isSaving) stringResource(R.string.common_saving)
                           else stringResource(R.string.common_save),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun MealTypeCheckboxRow(
    label: String,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            color = IfoodTextPrimary
        )
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() },
            colors = CheckboxDefaults.colors(
                checkedColor = IfoodRed,
                uncheckedColor = IfoodTextSecondary
            )
        )
    }
}
