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
fun AdministrarPasajerosScreen(vueloIndex: Int) {
	var refrescar by remember { mutableStateOf(0) }
	val vuelos = DataRepository.cola.DarLista()

	Column(modifier = Modifier.padding(16.dp)) {
		if (vuelos.isEmpty() || vueloIndex < 0 || vueloIndex >= vuelos.size) {
			Text("Vuelo no encontrado")
			return@Column
		}

		val vuelo = vuelos[vueloIndex]
		Text("Pasajeros del vuelo ${vuelo.getNumeroVuelo()}")

		val pasajeros = vuelo.DarListaPasajeros()
		if (pasajeros.isEmpty()) {
			Text("No hay pasajeros")
		} else {
			LazyColumn {
				itemsIndexed(pasajeros) { index, pasajero ->
					Row(modifier = Modifier
						.fillMaxWidth()
						.padding(8.dp)) {
						Column(modifier = Modifier.weight(1f)) {
							Text(pasajero.DarDatosPasajero())
						}

						Button(onClick = {
							vuelo.EliminarPasajero(pasajero)
							refrescar++
						}) {
							Text("Eliminar")
						}
					}
				}
			}
		}
	}
}
