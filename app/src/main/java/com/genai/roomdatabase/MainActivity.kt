package com.genai.roomdatabase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.genai.roomdatabase.database.FlowerDataBase
import com.genai.roomdatabase.ui.theme.RoomDatabaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enables edge-to-edge drawing (behind the system bars)
        enableEdgeToEdge()

        // We initialize the database here to ensure it's ready for use
        val db = FlowerDataBase.getDatabase(this)

        setContent {
            val context = LocalContext.current

            // Apply the project's theme (colors, fonts, etc.)
            RoomDatabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Main Menu Column
                    Column(
                        Modifier.fillMaxSize().padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Navigate to "Create" screen
                        Button(onClick = {
                            val intent = Intent(context, CreateDataEntryActivityKt::class.java)
                            context.startActivity(intent)
                        }){
                            Text("Add To Database (Create)")
                        }

                        // Navigate to "Read" screen
                        Button(onClick = {
                            val intent = Intent(context, ViewAllDataActivity::class.java)
                            context.startActivity(intent)
                        }){
                            Text("View All Entries (Read)")
                        }

                        // Navigate to "Update" screen
                        Button(onClick = {
                            val intent = Intent(context, EditDatabaseActivity::class.java)
                            context.startActivity(intent)
                        }){
                            Text("Edit Database (Update)")
                        }

                        // Navigate to "Delete" screen
                        Button(onClick = {
                            val intent = Intent(context, DeleteFlowerActivity::class.java)
                            context.startActivity(intent)
                        }){
                            Text("Delete Database (Delete)")
                        }
                    }
                }
            }
        }
    }
}