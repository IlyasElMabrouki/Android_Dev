package com.ilyaselmabrouki.db_sqlite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaselmabrouki.db_sqlite.ui.theme.DB_SQLITETheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DB_SQLITETheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}


@Composable
fun MyApp(databaseHelper: DataBaseHelper = DataBaseHelper(LocalContext.current)) {

        var personnes by remember { mutableStateOf(databaseHelper.getPersonnes()) }
        var selectedPersonIds by remember { mutableStateOf(emptyList<Int>()) }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var id by remember { mutableStateOf(0) }
            var name by remember { mutableStateOf("") }

            TextField(
                value = id.toString(),
                onValueChange = { id = it.toIntOrNull() ?: 0 },
                label = { Text("ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    // Add Button Click Handler
                    databaseHelper.addPersonne(name)
                    //id = ""
                    name = ""
                    personnes = databaseHelper.getPersonnes()
                }) {
                    Text("ADD")
                }
                Button(onClick = {
                    // Delete selected persons from the database
                    selectedPersonIds.forEach { id ->
                        databaseHelper.deletePersonne(id)
                    }
                    // Update the list of personnes after deletion
                    personnes = databaseHelper.getPersonnes()
                    // Clear the selection
                    selectedPersonIds = emptyList()
                }) {
                    Text("DELETE")
                }
                Button(onClick = {
                    // Update Button Click Handler
                    databaseHelper.updatePersonne(Personne(id, name))
                    personnes = databaseHelper.getPersonnes()
                }) {
                    Text("UPDATE")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // List view to display data
            LazyColumn {
                items(personnes) { personne ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Checkbox(
                            checked = selectedPersonIds.contains(personne.id),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedPersonIds = selectedPersonIds + personne.id
                                    id = personne.id
                                    name = personne.name
                                } else {
                                    selectedPersonIds = selectedPersonIds - personne.id
                                }
                            },
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "ID: ${personne.id}",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Name: ${personne.name}",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}