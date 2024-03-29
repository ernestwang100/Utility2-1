package com.jasslin.utility2_1

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsDisplay(
    optionList: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding() // Add padding to account for status bar
    ) {

        // Display the selected option
        var selectedOption by remember { mutableStateOf("Options") }
        DropDown(
            options = optionList,
            selectedOption = selectedOption,
            modifier = Modifier.fillMaxWidth(),
            initiallyOpen = true, // Open the dropdown by default
            onOptionSelected = { newOption ->
                selectedOption = newOption
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // TextField for custom option
        var customOption by remember { mutableStateOf("") }
        TextField(
            value = customOption,
            onValueChange = { customOption = it },
            label = { Text("Enter custom option") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to submit selected or custom option
        Button(
//                            colors =  ButtonDefaults.buttonColors(
//                                containerColor = redColor,
//                                contentColor = Color.White),
            onClick = {
                // Display the selected option and the text entered by the user
                println("Selected Option: $selectedOption")
                println("Custom Option: $customOption")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text("Submit")
        }
        // Display the selected option and the text entered by the user
        Text("Selected Option: $selectedOption")
        Text("Custom Option: $customOption")
    }
}

@Composable
fun DropDown(
    options: List<String>,
    selectedOption: String,
    modifier: Modifier = Modifier,
    initiallyOpen: Boolean = false,
    onOptionSelected: (String) -> Unit
) {
    var isOpen by remember { mutableStateOf(initiallyOpen) }
    val alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
        )
    )

    val rotateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300,
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = selectedOption,
                modifier = Modifier
                    .weight(1f)
                    .clickable { isOpen = !isOpen }
                    .padding(4.dp)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Dropdown Arrow",
                modifier = Modifier
                    .clickable { isOpen = !isOpen }
                    .scale(1f, if (isOpen) -1f else 1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value)
        ) {
            // Display the dropdown items here
            if (isOpen) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(4.dp)
                        .shadow(4.dp)
                ) {
                    options.forEach { option ->
                        DropDownMenuItem(
                            option = option,
                            onOptionSelected = {
                                isOpen = false
                                onOptionSelected(it)
                            }
                        )
                    }
                }
            }
        }


    }


}

@Composable
fun DropDownMenuItem(option: String, onOptionSelected: (String) -> Unit) {
    Text(
        text = option,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOptionSelected(option) }
            .padding(8.dp)
    )
}
