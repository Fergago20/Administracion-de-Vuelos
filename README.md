# Vuelos Cachalotes ✈️

Aplicación Android desarrollada con **Kotlin** y **Jetpack Compose** para la administración de vuelos y pasajeros.

La app permite:

- Crear vuelos.
- Crear pasajeros y asociarlos a un vuelo.
- Adjuntar una foto al pasajero.
- Administrar vuelos en una cola.
- Ver y eliminar pasajeros de cada vuelo.
- Navegar entre pantallas de forma sencilla.

---

## Funcionalidades principales

### Pantalla de carga
- Muestra el logo de la aplicación.
- Incluye una barra de progreso al iniciar.

### Menú principal
- Acceso a las funciones principales de la app:
  - **Crear Vuelo**
  - **Crear Pasajero**
  - **Administrar Vuelos**

### Crear vuelo
- Captura el número de vuelo.
- Permite elegir:
  - Aerolínea
  - Destino
  - Tipo de viaje
- Agrega el vuelo a una cola en memoria.
- Si el tipo de viaje es **Emergencia**, el vuelo se prioriza al inicio de la cola.

### Crear pasajero
- Permite ingresar:
  - Nombre
  - Apellido
  - Edad
- Permite seleccionar una **foto del pasajero** desde el dispositivo.
- Asocia el pasajero a un vuelo existente.
- Muestra el último pasajero agregado con sus datos y foto.

### Administrar vuelos
- Lista los vuelos creados.
- Permite ver los pasajeros de un vuelo.
- Permite desencolar el primer vuelo si tiene pasajeros.

### Administrar pasajeros
- Muestra los pasajeros del vuelo seleccionado.
- Permite ver la foto del pasajero.
- Permite eliminar pasajeros.

---

## Paleta visual sugerida

La interfaz está pensada para verse ordenada, limpia y moderna usando esta base visual:

- **Color primario:** `#00D2FF` o `#3A86FF`
- **Fondo:** `#F8FAFC`
- **Superficies / Cards:** blanco puro con sombras suaves
- **Énfasis / error / cancelación:** `#FF006E`

---

## Tecnologías utilizadas

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **Activity Result API** para seleccionar imágenes
- **Gradle Kotlin DSL**

---

## Estructura del proyecto

```text
app/src/main/java/ni/edu/uam/administracion/
├── MainActivity.kt
├── modelo/
│   ├── Cola.kt
│   ├── DataRepository.kt
│   ├── Pasajero.kt
│   └── Vuelo.kt
├── navegacion/
│   └── Navegacion.kt
├── ui/theme/
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
└── vistas/
    ├── AdministrarPasajeros.kt
    ├── AdministrarVuelos.kt
    ├── CargaScreen.kt
    ├── CrearPasajero.kt
    ├── CrearVuelo.kt
    └── HomeScreen.kt
```

---

## Modelo de datos

### `Vuelo`
Representa un vuelo con:
- número de vuelo
- aerolínea
- destino
- tipo de viaje
- lista de pasajeros

### `Pasajero`
Representa un pasajero con:
- nombre
- apellido
- edad
- número de pasajero autogenerado
- foto opcional

### `Cola`
Maneja la lista de vuelos en memoria.

### `DataRepository`
Repositorio simple en memoria para mantener la cola de vuelos durante la ejecución de la app.

---

## Navegación

El flujo principal se encuentra en:

- `app/src/main/java/ni/edu/uam/administracion/navegacion/Navegacion.kt`

### Flujo de pantallas
1. **Carga**
2. **Inicio**
3. **Crear vuelo**
4. **Crear pasajero**
5. **Administrar vuelos**
6. **Administrar pasajeros**

---

## Requisitos

- Android Studio
- JDK 11 o superior
- Android SDK configurado
- Emulador o dispositivo físico con Android 7.0+ (API 24)

---

## Cómo ejecutar el proyecto

1. Abrir el proyecto en Android Studio.
2. Esperar a que Gradle sincronice.
3. Ejecutar la app en un emulador o dispositivo físico.

### Desde Windows PowerShell

```powershell
.\gradlew.bat assembleDebug
```

Para correr pruebas:

```powershell
.\gradlew.bat test
```

---

## Notas importantes

- Los datos se guardan **solo en memoria** mientras la app está abierta.
- Al cerrar la aplicación, la información se pierde.
- El pasajero puede adjuntar una foto desde el dispositivo.
- La interfaz está pensada para ser clara, ordenada y navegable entre pantallas.

---

## Posibles mejoras futuras

- Persistencia con Room o DataStore.
- Edición de vuelos y pasajeros.
- Filtros y búsqueda.
- Mejoras visuales con una paleta personalizada y modo oscuro.
- Carga de imágenes desde cámara además de galería.

---

## Autor

Proyecto académico de administración de vuelos.
