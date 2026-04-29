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
import androidx.compose.material3.MenuAnchorType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearVueloScreen(onDone: () -> Unit) {
    var numero by remember { mutableStateOf("") }
    var numeroError by remember { mutableStateOf<String?>(null) }
    var aerolinea by remember { mutableStateOf(Vuelo.aerolineasDisponibles.first()) }
    var destino by remember { mutableStateOf(Vuelo.destinosDisponibles.first()) }
    var tipo by remember { mutableStateOf(Vuelo.tiposDeViaje.first()) }
    var aerolineaExpanded by remember { mutableStateOf(false) }
    var destinoExpanded by remember { mutableStateOf(false) }
    var tipoExpanded by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
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
                onValueChange = {
                    numero = it
                    numeroError = null
                },
                label = { Text("Número de vuelo") },
                isError = numeroError != null,
                supportingText = { numeroError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = aerolineaExpanded,
                onExpandedChange = { aerolineaExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = aerolinea,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Aerolínea") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = aerolineaExpanded) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = aerolineaExpanded,
                    onDismissRequest = { aerolineaExpanded = false }
                ) {
                    Vuelo.aerolineasDisponibles.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                aerolinea = opcion
                                aerolineaExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = destinoExpanded,
                onExpandedChange = { destinoExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = destino,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Destino") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = destinoExpanded) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = destinoExpanded,
                    onDismissRequest = { destinoExpanded = false }
                ) {
                    Vuelo.destinosDisponibles.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                destino = opcion
                                destinoExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = tipoExpanded,
                onExpandedChange = { tipoExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = tipo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de viaje") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tipoExpanded) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = tipoExpanded,
                    onDismissRequest = { tipoExpanded = false }
                ) {
                    Vuelo.tiposDeViaje.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                tipo = opcion
                                tipoExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ElevatedButton(
                    onClick = {
                        val numeroNormalizado = numero.trim().lowercase()
                        val formatoValido = Regex("^no-\\d{4}$")

                        if (numeroNormalizado.isBlank()) {
                            numeroError = "Ingrese número de vuelo"
                            mensaje = "Ingrese número de vuelo"
                            return@ElevatedButton
                        }

                        if (!formatoValido.matches(numeroNormalizado)) {
                            numeroError = "Formato inválido. Use: no-4512"
                            mensaje = "Número inválido"
                            return@ElevatedButton
                        }

                        if (numeroNormalizado.isNotBlank()) {
                            val vuelo = Vuelo(numeroNormalizado, aerolinea, destino, tipo)
                            DataRepository.cola.encolar(vuelo)
                            mensaje = "Vuelo creado y agregado"
                            numero = ""
                            numeroError = null
                            aerolinea = Vuelo.aerolineasDisponibles.first()
                            destino = Vuelo.destinosDisponibles.first()
                            tipo = Vuelo.tiposDeViaje.first()
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
}
