package ni.edu.uam.administracion.vistas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onCrearVuelo: () -> Unit,
    onCrearPasajero: () -> Unit,
    onAdministrarVuelos: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Administración de Vuelos")

        Button(onClick = onCrearVuelo, modifier = Modifier.padding(top = 8.dp)) {
            Text("Crear Vuelo")
        }

        Button(onClick = onCrearPasajero, modifier = Modifier.padding(top = 8.dp)) {
            Text("Crear Pasajero")
        }

        Button(onClick = onAdministrarVuelos, modifier = Modifier.padding(top = 8.dp)) {
            Text("Administrar Vuelos")
        }
    }
}

