package ni.edu.uam.administracion.vistas

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ni.edu.uam.administracion.modelo.DataRepository
import ni.edu.uam.administracion.modelo.Pasajero

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearPasajeroScreen(onDone: () -> Unit, onAdministrarPasajeros: (Int) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edadStr by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<String?>(null) }
    var mensaje by remember { mutableStateOf("") }
    var nombreError by remember { mutableStateOf<String?>(null) }
    var apellidoError by remember { mutableStateOf<String?>(null) }
    var edadError by remember { mutableStateOf<String?>(null) }
    var pasajeroAgregado by remember { mutableStateOf<Pasajero?>(null) }

    // seleccionar vuelo
    val vuelos = DataRepository.cola.DarLista()
    var selectedIndex by remember { mutableStateOf(if (vuelos.isNotEmpty()) 0 else -1) }
    var expanded by remember { mutableStateOf(false) }
    val selectorFoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        fotoUri = uri?.toString()
    }
    val botonSeleccionFotoModifier = Modifier.fillMaxWidth()
    val abrirSelectorFoto = { selectorFoto.launch("image/*") }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
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
                onValueChange = {
                    nombre = it
                    nombreError = null
                },
                label = { Text("Nombre") },
                isError = nombreError != null,
                supportingText = { nombreError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            val espacioMedio = Modifier.height(16.dp)

            Spacer(modifier = espacioMedio)

            OutlinedTextField(
                value = apellido,
                onValueChange = {
                    apellido = it
                    apellidoError = null
                },
                label = { Text("Apellido") },
                isError = apellidoError != null,
                supportingText = { apellidoError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            val espacioFoto = Modifier.height(16.dp)

            Spacer(modifier = espacioFoto)

            val campoEdadModifier = Modifier.fillMaxWidth()

            OutlinedTextField(
                value = edadStr,
                onValueChange = { nuevoValor ->
                    if (nuevoValor.matches(Regex("\\d*"))) {
                        edadStr = nuevoValor
                        edadError = null
                    }
                },
                label = { Text("Edad") },
                isError = edadError != null,
                supportingText = { edadError?.let { Text(it) } },
                modifier = campoEdadModifier
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = abrirSelectorFoto,
                modifier = botonSeleccionFotoModifier
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Seleccionar Foto",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (fotoUri == null) "Seleccionar foto" else "Cambiar foto")
            }

            FotoPreview(
                fotoUri = fotoUri,
                titulo = "Vista previa de la foto"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = if (selectedIndex >= 0 && vuelos.size > selectedIndex) vuelos[selectedIndex].getNumeroVuelo() else "Seleccione un vuelo",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Vuelo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
					modifier = Modifier
            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
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

            Spacer(modifier = Modifier.height(24.dp))

            val vueloSeleccionado = selectedIndex >= 0 && selectedIndex < vuelos.size

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ElevatedButton(
                    onClick = {
                        val edad = edadStr.toIntOrNull()
                        var valido = true

                        if (nombre.isBlank()) {
                            nombreError = "Ingrese el nombre"
                            valido = false
                        }

                        if (apellido.isBlank()) {
                            apellidoError = "Ingrese el apellido"
                            valido = false
                        }

                        if (edad == null) {
                            edadError = "Ingrese una edad numérica"
                            valido = false
                        } else if (edad <= 0) {
                            edadError = "La edad debe ser mayor que 0"
                            valido = false
                        }

                        if (!valido) {
                            mensaje = "Corrija los campos marcados"
                            return@ElevatedButton
                        }

                        if (!vueloSeleccionado) {
                            mensaje = "Seleccione un vuelo"
                            return@ElevatedButton
                        }

                        val edadValida = edad ?: return@ElevatedButton
                        val pasajero = Pasajero(nombre.trim(), apellido.trim(), edadValida)
                        fotoUri?.let { pasajero.setFoto(it) }
                        vuelos[selectedIndex].AgregarPasajero(pasajero)
                        pasajeroAgregado = pasajero
                        mensaje = "Pasajero ${pasajero.getNumeroPasajero()} agregado al vuelo ${vuelos[selectedIndex].getNumeroVuelo()}"
                        nombre = ""
                        apellido = ""
                        edadStr = ""
                        fotoUri = null
                        nombreError = null
                        apellidoError = null
                        edadError = null
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

            pasajeroAgregado?.let { pasajero ->
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Último pasajero agregado",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        FotoPreview(
                            fotoUri = pasajero.getFoto(),
                            titulo = "Foto del pasajero agregado"
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = pasajero.DarDatosPasajero(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

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
}

@Composable
private fun FotoPreview(fotoUri: String?, titulo: String) {
    if (fotoUri.isNullOrBlank()) {
        return
    }

    val context = LocalContext.current
    val bitmap = remember(fotoUri) {
        runCatching {
            context.contentResolver.openInputStream(Uri.parse(fotoUri))?.use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()
            }
        }.getOrNull()
    }

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = titulo,
        style = MaterialTheme.typography.bodySmall
    )
    Spacer(modifier = Modifier.height(8.dp))

    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = titulo,
            modifier = Modifier.size(120.dp)
        )
    } else {
        Text(
            text = "No se pudo cargar la imagen seleccionada",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

