package ni.edu.uam.administracion.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
// Asegúrate de importar el R de tu proyecto para acceder al logo
// import ni.edu.uam.administracion.R

@Composable
fun CargaScreen(onLoadingFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2500) // Un poco más de tiempo para que se aprecie la barra
        onLoadingFinished()
    }

    // Usamos Box para el fondo azul oscuro como el del logo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A365D)), // Color azul marino similar a tu imagen
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Logo de la aplicación
            // Recuerda guardar tu imagen en res/drawable con un nombre simple (ej: app_logo.png)
            Image(
                painter = painterResource(id = ni.edu.uam.administracion.R.drawable.logo),
                contentDescription = "Logo de la aplicación",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Barra de carga horizontal

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(6.dp),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Iniciando viaje...",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}