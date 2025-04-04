package com.roleapp.auth.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unir.roleapp.R


@Composable
fun LoginForm(
    onLogin: (String, String) -> Unit,
    onRegister: (String, String, String) -> Unit
) {
    var email by remember { mutableStateOf("test_6@mail.com") }
    var password by remember { mutableStateOf("1234") }
    var showRegister by remember { mutableStateOf(false) }
    val textColor: Color  = colorResource(id = R.color.white)
    val secondTextColor: Color  = colorResource(id = R.color.gray)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (showRegister) "Crear Cuenta" else "Iniciar Sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = secondTextColor) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(textColor = textColor)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = secondTextColor) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(textColor = textColor)
        )

        if (showRegister) {
            var confirmPassword by remember { mutableStateOf("") }

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña", color = secondTextColor) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(textColor = textColor)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onRegister(email, password, confirmPassword) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(text = "Registrarse", fontSize = 16.sp, color = Color.White)
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onLogin(email, password) },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.accent_primary))
            ) {
                Text(text = "Iniciar sesión", fontSize = 16.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Divider(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Eres nuevo?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = secondTextColor
        )

        TextButton(onClick = { showRegister = !showRegister }) {
            Text(
                text = if (showRegister) "Volver al login" else "Crear una cuenta",
                fontSize = 16.sp,
                color = textColor
            )
        }
    }
}
