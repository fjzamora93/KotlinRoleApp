package com.unir.roleapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    var searchText by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearch(searchText)
            },
            label = { Text("Search") },
            modifier = Modifier
                .weight(5f)
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            singleLine = true
        )



        IconButton(
            onClick = {
                searchText = ""
                onSearch(searchText)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Limpiar búsqueda",
                tint = primaryColor
            )
        }
    }
}