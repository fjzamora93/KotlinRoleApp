# Rol App

RolApp es una aplicación para manejo de juegos de Rol que permite a los usuarios crear, actualizar, eliminar y organizar los personajes y las campañas. Este proyecto utiliza Kotlin y Jetpack Compose para la interfaz de usuario, y está estructurado siguiendo el patrón de arquitectura MVVM (Model-View-ViewModel). La inyección de dependencias se maneja con Dagger Hilt.

```
📦 Proyecto
├── 📂 data
│   ├── 📂 local
│   │   ├── 📂 database
│   │   ├── 📂 model
│   │   ├── 📂 repository
│   │
│   ├── 📂 remote
│       ├── 📂 service
│       ├── 📂 model
│       ├── 📂 repository
│
├── 📂 di
│
├── 📂 domain
│   ├── 📂 model
│   ├── 📂 repository
│
├── 📂 util
│
├── 📂 ui
│   ├── 📂 navigation
│   ├── 📂 screens
│   ├── 📂 viewmodels
```

## Características

- Crear, editar y eliminar personajes
- Crear eventos para las campañas
- Guardar los stats, inventario y cualquier dato relevante.
- Almacenar datos de sesión.

## Requisitos

- Android Studio Bumblebee o superior
- JDK 11 o superior

## Instalación

1. **Clona este repositorio**:
   ```sh
   git clone https://github.com/fjzamora93/RolApp

2. **Abre el proyecto en Android Studio.**
3. **Sincroniza el proyecto con Gradle.**
4. **Ejecuta la aplicación en un emulador o dispositivo físico.**

## Arquitectura del Proyecto

El proyecto está estructurado utilizando la arquitectura MVVM(Modelo-Vista-VistaModelo) , lo cual facilita la separación de preocupaciones y mejora la testabilidad del código.

### Directorios Principales

- **data**: Contiene los modelos de datos y el acceso a datos locales.
  - **local**: Contiene los modelos de datos y la implementación de la base de datos local (Room).
- **domain**: Contiene los casos de uso que encapsulan la lógica de negocio.
- **ui**: Contiene las pantallas de la aplicación y sus respectivos ViewModels.

