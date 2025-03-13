package com.unir.sheet.ui.screens.layout


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import com.util.CustomType

@Composable
fun FontsTemplateScreen(){
    MainLayout(){
        FontsTemplateBody()
    }

}

@Composable
fun FontsTemplateBody(){
    Column(){
        Text(
            text = "TittleLarge. Esto es un texto de pmuestra",
            style = CustomType.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "TittleMEdium. Esto es un texto de pmuestra",
            style = CustomType.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "TittleSmall. Esto es un texto de pmuestra",
            style = CustomType.titleSmall
        )
        Spacer(modifier = Modifier.height(16.dp))



        Text(
            text = "BodyLarge, Esto es un texto de pmuestra",
            style = CustomType.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "bodySmall. Esto es un texto de pmuestra",
            style = CustomType.bodySmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "bodyMedium. Esto es un texto de pmuestra",
            style = CustomType.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))



        Text(
            text = "LabelMedium. Esto es un texto de pmuestra",
            style = CustomType.labelSmall
        )


    }
}