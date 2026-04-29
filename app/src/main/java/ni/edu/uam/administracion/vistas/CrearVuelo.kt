package ni.edu.uam.administracion.vistas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ni.edu.uam.administracion.modelo.DataRepository
import ni.edu.uam.administracion.modelo.Vuelo

@Composable
fun CrearVueloScreen(onDone: () -> Unit) {
	var numero by remember { mutableStateOf("") }
	var aerolinea by remember { mutableStateOf("") }
	var destino by remember { mutableStateOf("") }
	var tipo by remember { mutableStateOf("") }
	var mensaje by remember { mutableStateOf("") }

	Column(modifier = Modifier.padding(16.dp)) {
		Text(text = "Crear Vuelo")
		TextField(value = numero, onValueChange = { numero = it }, label = { Text("Número de vuelo") }, modifier = Modifier.fillMaxWidth())
		TextField(value = aerolinea, onValueChange = { aerolinea = it }, label = { Text("Aerolínea") }, modifier = Modifier.fillMaxWidth())
		TextField(value = destino, onValueChange = { destino = it }, label = { Text("Destino") }, modifier = Modifier.fillMaxWidth())
		TextField(value = tipo, onValueChange = { tipo = it }, label = { Text("Tipo de viaje") }, modifier = Modifier.fillMaxWidth())

		Button(onClick = {
			if (numero.isNotBlank()) {
				val vuelo = Vuelo(numero, if (aerolinea.isBlank()) Vuelo.aerolineasDisponibles.first() else aerolinea,
					if (destino.isBlank()) Vuelo.destinosDisponibles.first() else destino,
					if (tipo.isBlank()) Vuelo.tiposDeViaje.first() else tipo)
				DataRepository.cola.encolar(vuelo)
				mensaje = "Vuelo creado y encolado"
				// limpiar
				numero = ""; aerolinea = ""; destino = ""; tipo = ""
			} else {
				mensaje = "Ingrese número de vuelo"
			}
		}, modifier = Modifier.padding(top = 8.dp)) {
			Text("Crear Vuelo")
		}

		Button(onClick = onDone, modifier = Modifier.padding(top = 8.dp)) {
			Text("Volver")
		}

		if (mensaje.isNotEmpty()) {
			Text(mensaje, modifier = Modifier.padding(top = 8.dp))
		}
	}
}
