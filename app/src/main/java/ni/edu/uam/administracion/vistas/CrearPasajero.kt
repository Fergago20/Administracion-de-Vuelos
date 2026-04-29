package ni.edu.uam.administracion.vistas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ni.edu.uam.administracion.modelo.DataRepository
import ni.edu.uam.administracion.modelo.Pasajero

@Composable
fun CrearPasajeroScreen(onDone: () -> Unit, onAdministrarPasajeros: (Int) -> Unit) {
	var nombre by remember { mutableStateOf("") }
	var apellido by remember { mutableStateOf("") }
	var edadStr by remember { mutableStateOf("") }
	var mensaje by remember { mutableStateOf("") }

	// seleccionar vuelo
	var expanded by remember { mutableStateOf(false) }
	val vuelos = DataRepository.cola.DarLista()
	var selectedIndex by remember { mutableStateOf(if (vuelos.isNotEmpty()) 0 else -1) }

	Column(modifier = Modifier.padding(16.dp)) {
		Text("Crear Pasajero")
		TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
		TextField(value = apellido, onValueChange = { apellido = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth())
		TextField(value = edadStr, onValueChange = { edadStr = it }, label = { Text("Edad") }, modifier = Modifier.fillMaxWidth())

		Row(modifier = Modifier.padding(top = 8.dp)) {
			Text(text = if (selectedIndex >= 0 && vuelos.size > selectedIndex) "Vuelo: ${vuelos[selectedIndex].getNumeroVuelo()}" else "Seleccione un vuelo", modifier = Modifier
				.clickable { expanded = true }
				.padding(8.dp))
			DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
				vuelos.forEachIndexed { index, vuelo ->
					DropdownMenuItem(text = { Text(vuelo.getNumeroVuelo()) }, onClick = {
						selectedIndex = index
						expanded = false
					})
				}
			}
		}

		Button(onClick = {
			val edad = edadStr.toIntOrNull() ?: -1
			if (nombre.isBlank() || apellido.isBlank() || edad <= 0) {
				mensaje = "Complete los datos correctamente"
				return@Button
			}

			if (selectedIndex < 0 || selectedIndex >= vuelos.size) {
				mensaje = "Seleccione un vuelo"
				return@Button
			}

			val pasajero = Pasajero(nombre, apellido, edad)
			vuelos[selectedIndex].AgregarPasajero(pasajero)
			mensaje = "Pasajero agregado al vuelo ${vuelos[selectedIndex].getNumeroVuelo()}"
			// limpiar
			nombre = ""; apellido = ""; edadStr = ""
		}, modifier = Modifier.padding(top = 8.dp)) {
			Text("Agregar Pasajero")
		}

		Button(onClick = onDone, modifier = Modifier.padding(top = 8.dp)) {
			Text("Volver")
		}

		if (mensaje.isNotEmpty()) {
			Text(mensaje, modifier = Modifier.padding(top = 8.dp))
		}

		// Botón rápido para ir a administrar pasajeros del vuelo seleccionado
		if (selectedIndex >= 0) {
			Button(onClick = { onAdministrarPasajeros(selectedIndex) }, modifier = Modifier.padding(top = 8.dp)) {
				Text("Administrar pasajeros del vuelo seleccionado")
			}
		}
	}
}
