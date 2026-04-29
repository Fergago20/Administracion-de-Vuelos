package ni.edu.uam.administracion.vistas

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ni.edu.uam.administracion.modelo.DataRepository

@Composable
fun AdministrarPasajerosScreen(vueloIndex: Int, onDone: () -> Unit) {
    var refrescar by remember { mutableStateOf(0) }
    val vuelos = DataRepository.cola.DarLista()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedButton(
            onClick = onDone,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Volver")
        }

        if (vuelos.isEmpty() || vueloIndex < 0 || vueloIndex >= vuelos.size) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = "Vuelo no encontrado",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            return@Column
        }

        val vuelo = vuelos[vueloIndex]
        Text(
            text = "Pasajeros del vuelo ${vuelo.getNumeroVuelo()}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        val pasajeros = vuelo.DarListaPasajeros()
        if (pasajeros.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "No hay pasajeros",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(pasajeros) { _, pasajero ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FotoPasajero(
                                fotoUri = pasajero.getFoto(),
                                modifier = Modifier.size(84.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = pasajero.DarDatosPasajero(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            ElevatedButton(
                                onClick = {
                                    vuelo.EliminarPasajero(pasajero)
                                    refrescar++
                                },
                                colors = ButtonDefaults.elevatedButtonColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Eliminar",
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FotoPasajero(fotoUri: String?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val bitmap = remember(fotoUri) {
        if (fotoUri.isNullOrBlank()) {
            null
        } else {
            runCatching {
                context.contentResolver.openInputStream(fotoUri.toUri())?.use { stream ->
                    BitmapFactory.decodeStream(stream)?.asImageBitmap()
                }
            }.getOrNull()
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = "Foto del pasajero",
            modifier = modifier
        )
    } else {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sin foto",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

