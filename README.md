# Taller3Eventos FranLópez
https://github.com/franlopezsoto/Taller3EventosFranLopez

## Descripción General
**Taller3EventosFranLopez** es una aplicación Android que permite al usuario:
- Ingresar y guardar su nombre utilizando `SharedPreferences`.
- Almacenar y recuperar el nombre de una base de datos SQLite.
- Cambiar la configuración de la aplicación, como el color de fondo, a través de una pantalla de configuración.
- Mostrar un saludo personalizado basado en la hora del día ("Buenos días", "Buenas tardes" o "Buenas noches").

## Funcionalidades
### 1. Pantalla de Inicio
- Saludo que cambia según la hora del día (por ejemplo, "Buenos días" si es mañana).
- Campo de texto para ingresar el nombre.
- Botón para guardar el nombre en `SharedPreferences`.
- Visualización del nombre guardado.
- Botón para almacenar y recuperar el nombre en la base de datos SQLite.
- Botón para acceder a la pantalla de configuración.

### 2. Pantalla de Configuración
- Opciones para cambiar el color de fondo de la aplicación.
- Guardado de la preferencia de color en `SharedPreferences`.

## Estructura del Proyecto
### Clases Principales
- **MainActivity.java**
  - Maneja la lógica de la pantalla principal, incluyendo el saludo y la interacción con `SharedPreferences` y la base de datos SQLite.

- **SettingActivity.java**
  - Permite al usuario cambiar el color de fondo de la aplicación y guarda esta configuración en `SharedPreferences`.

## Cómo Funciona
1. **Saludo Personalizado**: Al abrir la aplicación, un `TextView` muestra un saludo dependiendo de la hora del día.
2. **Ingreso y Guardado del Nombre**:
   - El usuario puede ingresar su nombre en un campo de texto y guardarlo en `SharedPreferences`.
   - El nombre guardado se muestra debajo del campo de texto.
3. **Interacción con SQLite**:
   - El usuario puede guardar su nombre en la base de datos SQLite y recuperarlo con un solo botón.
4. **Configuración del Color de Fondo**:
   - En la pantalla de configuración, el usuario puede elegir un color de fondo, que se guarda y aplica a la aplicación.

## Requisitos de Instalación
- Android Studio.
- Emulador de Android o dispositivo físico con Android 5.0 (Lollipop) o superior.

## Capturas de Pantalla
*Añadir capturas de pantalla aquí para ilustrar la interfaz.*

## Tecnologías Utilizadas
- **Lenguaje**: Java
- **Base de Datos**: SQLite
- **Almacenamiento**: `SharedPreferences`
- **Interfaz de Usuario**: XML

## Autor
Desarrollado por Franchesco Capaldi Carlino Lopesiño Sotelo
