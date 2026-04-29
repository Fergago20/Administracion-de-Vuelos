package ni.edu.uam.administracion.vistas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ni.edu.uam.administracion.modelo.DataRepository

@Composable
fun AdministrarVuelosScreen(onVerPasajeros: (Int) -> Unit) {
	var refrescar by remember { mutableStateOf(0) }
	val vuelos = DataRepository.cola.DarLista()

	Column(modifier = Modifier.padding(16.dp)) {
		Text("Administrar Vuelos")

		if (vuelos.isEmpty()) {
			Text("No hay vuelos en la cola")
		} else {
			LazyColumn {
				itemsIndexed(vuelos) { index, vuelo ->
					Row(modifier = Modifier
						.fillMaxWidth()
						.padding(8.dp)) {
						Column(modifier = Modifier.weight(1f)) {
							Text("#${index + 1} - ${vuelo.getNumeroVuelo()}")
							Text(vuelo.DarDatosVuelo())
						}

						Button(onClick = { onVerPasajeros(index) }) {
							Text("Ver Pasajeros")
						}

						Button(onClick = {
							// intentar desencolar si tiene pasajeros
							val removed = DataRepository.cola.desencolar()
							if (!removed) {
								// si no se pudo, intentar remover por índice
								vuelos.removeAt(index)
							}
							refrescar++
						}, modifier = Modifier.padding(start = 8.dp)) {
							Text("Eliminar/Desencolar")
						}
					}
				}
			}
		}
	}
}
