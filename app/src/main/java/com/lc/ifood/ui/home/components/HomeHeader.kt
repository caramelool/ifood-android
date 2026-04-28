package com.lc.ifood.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardOptions.Companion
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lc.ifood.R
import com.lc.ifood.ui.theme.IfoodRed
import com.lc.ifood.ui.theme.IfoodTextPrimary
import com.lc.ifood.ui.theme.IfoodTextSecondary

@Composable
internal fun HomeHeader(
    userName: String?,
    isUserLoaded: Boolean,
    onSaveName: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isUserLoaded) {
        if (isUserLoaded && userName == null) showDialog = true
    }

    if (showDialog) {
        UserNameDialog(
            onConfirm = { name ->
                onSaveName(name)
                showDialog = false
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 18.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.home_welcome),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = IfoodTextPrimary
        )
        if (userName != null) {
            Spacer(Modifier.width(4.dp))
            Text(
                text = userName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = IfoodTextPrimary
            )
        }
    }
}

@Composable
private fun UserNameDialog(
    onConfirm: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.home_username_dialog_title)) },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.home_username_dialog_message),
                    color = IfoodTextSecondary
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(stringResource(R.string.home_username_dialog_hint)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (name.isNotBlank()) onConfirm(name.trim()) },
                colors = ButtonDefaults.textButtonColors(contentColor = IfoodRed)
            ) {
                Text(text = stringResource(R.string.home_username_dialog_confirm))
            }
        }
    )
}
