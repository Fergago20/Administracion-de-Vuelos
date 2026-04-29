package ni.edu.uam.administracion.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Crear Vuelo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = numero,
            onValueChange = { numero = it },
            label = { Text("Número de vuelo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = aerolinea,
            onValueChange = { aerolinea = it },
            label = { Text("Aerolínea") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = destino,
            onValueChange = { destino = it },
            label = { Text("Destino") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = tipo,
            onValueChange = { tipo = it },
            label = { Text("Tipo de viaje") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ElevatedButton(
                onClick = {
                    if (numero.isNotBlank()) {
                        val vuelo = Vuelo(
                            numero,
                            if (aerolinea.isBlank()) Vuelo.aerolineasDisponibles.first() else aerolinea,
                            if (destino.isBlank()) Vuelo.destinosDisponibles.first() else destino,
                            if (tipo.isBlank()) Vuelo.tiposDeViaje.first() else tipo
                        )
                        DataRepository.cola.encolar(vuelo)
                        mensaje = "Vuelo creado y encolado"
                        // limpiar
                        numero = ""; aerolinea = ""; destino = ""; tipo = ""
                    } else {
                        mensaje = "Ingrese número de vuelo"
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Crear Vuelo",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Crear Vuelo")
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
                    containerColor = if (mensaje.contains("creado")) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = mensaje,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
