# StreamersApp

Aplicación Android para gestionar una lista de streamers con sus plataformas, categorías y perfiles.

## Descripción

StreamersApp es una aplicación desarrollada en Kotlin que permite visualizar, añadir, editar y eliminar streamers de una lista. Utiliza RecyclerView para mostrar los datos de forma eficiente y Glide para la carga de imágenes. La aplicación cuenta con un sistema de autenticación y navegación mediante Navigation Drawer y Bottom Navigation.

## Características

- **Sistema de Login**: Autenticación con credenciales (Usuario: `admin`, Contraseña: `1234`)
- **Navigation Drawer**: Menú lateral para navegación principal
- **Bottom Navigation**: Barra de navegación inferior para acceso rápido
- **Personalización única**: Interfaz personalizada con diseño propio
- Visualización de streamers con foto, nombre y categoría
- Iconos de plataformas (Twitch, YouTube, Kick)
- Añadir nuevos streamers
- Editar información de streamers existentes
- Eliminar streamers con diálogo de confirmación
- Ver perfil de cada streamer
- Imágenes circulares con Glide
- Arquitectura MVC (Model-View-Controller)

## Tecnologías utilizadas

- Lenguaje: Kotlin
- UI: XML Layouts con ViewBinding
- Arquitectura: MVC
- RecyclerView: Para listas eficientes
- Glide: Para carga y caché de imágenes
- Material Design: CardView, Dialogs, Navigation Drawer, Bottom Navigation
- Activities: Sistema multi-activity con Login

## Estructura del proyecto
```
com.example.streamersapp/
├── adapter/
│   ├── AdapterStreamer.kt
│   └── ViewHStreamer.kt
├── controler/
│   └── Controller.kt
├── models/
│   └── Streamer.kt
├── objects_models/
│   └── Repository.kt
├── LoginActivity.kt
└── MainActivity.kt
```

## Modelo de datos

### Streamer
```kotlin
class Streamer(
    val id: Int,
    var nombre: String,
    var plataformas: List<String>,
    var categoria: String,
    var urlPerfil: String,
    var foto: Any
)
```

## Funcionalidades principales

### Sistema de Login

- Usuario: `admin`
- Contraseña: `1234`
- Validación de credenciales antes de acceder a la aplicación

### Añadir Streamer
```kotlin
controller.addStreamer(nuevoStreamer)
```

### Editar Streamer
```kotlin
controller.editStreamer(position, streamerActualizado)
```

### Eliminar Streamer
```kotlin
controller.deleteStreamer(position)
```

## Configuración

### Dependencias en build.gradle
```gradle
dependencies {
    implementation 'androidx.recyclerview:recyclerview:1.3.2'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'com.github.bumptech.glide:glide:4.16.0'
    implementation 'com.google.android.material:material:1.11.0'
}
```

### ViewBinding

Habilitar ViewBinding en build.gradle:
```gradle
android {
    buildFeatures {
        viewBinding true
    }
}
```

## Uso

1. Clona el repositorio
2. Abre el proyecto en Android Studio
3. Sincroniza las dependencias de Gradle
4. Añade las imágenes de los streamers en res/drawable/
5. Ejecuta la aplicación en un emulador o dispositivo físico
6. Inicia sesión con las credenciales: **Usuario**: `admin` | **Contraseña**: `1234`

## Recursos necesarios

### Drawables requeridos

Coloca las siguientes imágenes en res/drawable/:

- ibai.png
- auron.png
- elxokas.png
- thegrefg.png
- rubius.png
- willi.png
- vegeta777.png
- jordi.png
- antonio.png
- peereira7.png
- djmario.png
- illojuan.png
- twitch.png
- youtube.png
- kick.png

## Arquitectura

### Controller

Gestiona la lógica de negocio y las operaciones CRUD sobre la lista de streamers.

### Adapter

Maneja el RecyclerView y la visualización de cada item.

### ViewHolder

Renderiza cada streamer con su información y botones de acción.

### Repository

Contiene los datos iniciales de los streamers.

## Características técnicas

- Uso de adapterPosition para evitar problemas con posiciones
- Glide con caché deshabilitado para depuración
- Diálogos personalizados para confirmación de eliminación
- ViewBinding para acceso seguro a las vistas
- Material Design Components
- Navigation Drawer para menú lateral
- Bottom Navigation para navegación rápida
- Sistema de Activities (LoginActivity y MainActivity)

## Autor

Desarrollado como proyecto educativo de desarrollo Android con Kotlin.

## Licencia

Este proyecto es de uso educativo.
