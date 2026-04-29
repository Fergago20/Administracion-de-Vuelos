package ni.edu.uam.administracion.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ni.edu.uam.administracion.vistas.AdministrarPasajerosScreen
import ni.edu.uam.administracion.vistas.AdministrarVuelosScreen
import ni.edu.uam.administracion.vistas.CrearPasajeroScreen
import ni.edu.uam.administracion.vistas.CrearVueloScreen
import ni.edu.uam.administracion.vistas.HomeScreen

// Navegación simple basada en estado para evitar dependencia de Navigation Compose
sealed class Screen {
	object Home : Screen()
	object CrearVuelo : Screen()
	object CrearPasajero : Screen()
	object AdministrarVuelos : Screen()
	data class AdministrarPasajeros(val index: Int) : Screen()
}

@Composable
fun Navegacion() {
	val current = remember { mutableStateOf<Screen>(Screen.Home) }

	when (val screen = current.value) {
		is Screen.Home -> HomeScreen(
			onCrearVuelo = { current.value = Screen.CrearVuelo },
			onCrearPasajero = { current.value = Screen.CrearPasajero },
			onAdministrarVuelos = { current.value = Screen.AdministrarVuelos }
		)

		is Screen.CrearVuelo -> CrearVueloScreen(onDone = { current.value = Screen.Home })

		is Screen.CrearPasajero -> CrearPasajeroScreen(
			onDone = { current.value = Screen.Home },
			onAdministrarPasajeros = { index -> current.value = Screen.AdministrarPasajeros(index) }
		)

		is Screen.AdministrarVuelos -> AdministrarVuelosScreen(onVerPasajeros = { index -> current.value = Screen.AdministrarPasajeros(index) })

		is Screen.AdministrarPasajeros -> AdministrarPasajerosScreen(vueloIndex = screen.index)
	}
}

