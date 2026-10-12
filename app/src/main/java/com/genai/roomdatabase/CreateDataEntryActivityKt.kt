package com.genai.roomdatabase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.genai.roomdatabase.database.Flower
import com.genai.roomdatabase.database.FlowerDao
import com.genai.roomdatabase.database.FlowerDataBase
import com.genai.roomdatabase.ui.theme.RoomDatabaseTheme
import kotlinx.coroutines.launch

/**
 * CREATE ACTIVITY: This activity allows users to add new flowers to the database.
 */
class CreateDataEntryActivityKt : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize our Database and DAO
        val db = FlowerDataBase.getDatabase(this)
        val flowerDao = db.flowerDao()

        setContent {
            RoomDatabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Pass the DAO to the composable function
                    FlowerScreen(flowerDao, Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun FlowerScreen(flowerDao: FlowerDao, modifier: Modifier = Modifier) {
        // STATE: 'remember' keeps these values during UI updates (recompositions)
        var name by remember { mutableStateOf("") }
        var color by remember { mutableStateOf("") }

        val context = LocalContext.current

        // COROUTINE SCOPE: Necessary to run 'suspend' functions (like database calls)
        val scope = rememberCoroutineScope()

        Column(modifier = modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Text("Add New Flower", style = MaterialTheme.typography.headlineMedium)

            // Input field for Flower Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Flower Name") },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )

            // Input field for Flower Color
            OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )

            // CREATE OPERATION: Clicking the button saves data to Room
            Button(
                onClick = {
                    if (name.isNotBlank() && color.isNotBlank()) {
                        // Launch a coroutine because DAO methods are 'suspend' functions
                        scope.launch {
                            val flower = Flower(name = name, color = color)

                            // Call the DAO to insert the flower into the database
                            flowerDao.insertFlower(flower)

                            // Reset input fields
                            name = ""
                            color = ""
                        }
                        Toast.makeText(context, "Flower Added!", Toast.LENGTH_SHORT).show()

                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Save to Database")
            }
        }
    }
}