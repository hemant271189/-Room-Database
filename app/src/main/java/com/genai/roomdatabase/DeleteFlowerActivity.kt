package com.genai.roomdatabase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.genai.roomdatabase.database.FlowerDataBase
import com.genai.roomdatabase.ui.theme.RoomDatabaseTheme
import kotlinx.coroutines.launch

class DeleteFlowerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDatabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DeleteFlowerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DeleteFlowerScreen(modifier: Modifier = Modifier) {
    var flowerId by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Getting the database instance through our Singleton
    val database = FlowerDataBase.getDatabase(context)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Delete From Database", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Input field for the ID to delete
        OutlinedTextField(
            value = flowerId,
            onValueChange = { flowerId = it },
            label = { Text("Enter Flower ID (UID)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // DELETE OPERATION
        Button(
            onClick = {
                if (flowerId.isNotEmpty()) {
                    val id = flowerId.toIntOrNull()
                    if (id != null) {
                        // Launch a coroutine to call the 'suspend' function deleteFlowerById
                        coroutineScope.launch {
                            database.flowerDao().deleteFlowerById(id)
                            flowerId = "" // Clear the text field after deletion attempt
                        }
                        Toast.makeText(context, "Flower deleted if it existed", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(context, "Invalid ID format", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please enter an ID to delete", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete Record")
        }
    }
}