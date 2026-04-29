package ni.edu.uam.administracion.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ni.edu.uam.administracion.modelo.DataRepository
import ni.edu.uam.administracion.modelo.Pasajero
import androidx.compose.material3.MenuAnchorType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearPasajeroScreen(onDone: () -> Unit, onAdministrarPasajeros: (Int) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edadStr by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    // seleccionar vuelo
    val vuelos = DataRepository.cola.DarLista()
    var selectedIndex by remember { mutableStateOf(if (vuelos.isNotEmpty()) 0 else -1) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Crear Pasajero",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = edadStr,
            onValueChange = { edadStr = it },
            label = { Text("Edad") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            OutlinedTextField(
                value = if (selectedIndex >= 0 && vuelos.size > selectedIndex) vuelos[selectedIndex].getNumeroVuelo() else "Seleccione un vuelo",
                onValueChange = {},
                readOnly = true,
                label = { Text("Vuelo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
				modifier = Modifier
					.menuAnchor()
					.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                vuelos.forEachIndexed { index, vuelo ->
                    DropdownMenuItem(
                        text = { Text(vuelo.getNumeroVuelo()) },
                        onClick = {
                            selectedIndex = index
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ElevatedButton(
                onClick = {
                    val edad = edadStr.toIntOrNull() ?: -1
                    if (nombre.isBlank() || apellido.isBlank() || edad <= 0) {
                        mensaje = "Complete los datos correctamente"
                        return@ElevatedButton
                    }

                    if (selectedIndex < 0 || selectedIndex >= vuelos.size) {
                        mensaje = "Seleccione un vuelo"
                        return@ElevatedButton
                    }

                    val pasajero = Pasajero(nombre, apellido, edad)
                    vuelos[selectedIndex].AgregarPasajero(pasajero)
                    mensaje = "Pasajero agregado al vuelo ${vuelos[selectedIndex].getNumeroVuelo()}"
                    // limpiar
                    nombre = ""; apellido = ""; edadStr = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Filled.PersonAdd,
                    contentDescription = "Agregar Pasajero",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar Pasajero")
            }

            OutlinedButton(
                onClick = onDone,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Volver")
            }
        }

        if (mensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (mensaje.contains("agregado")) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = mensaje,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Botón rápido para ir a administrar pasajeros del vuelo seleccionado
        if (selectedIndex >= 0) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { onAdministrarPasajeros(selectedIndex) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Administrar Pasajeros",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Administrar pasajeros del vuelo seleccionado")
            }
        }
    }
}
