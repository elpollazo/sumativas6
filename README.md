# sumativas6

# Aplicación Móvil con Firebase Authentication y Realtime Database

## Descripción
Esta aplicación móvil desarrollada en Android utiliza **Firebase Authentication** para permitir a los usuarios registrarse e iniciar sesión mediante correo electrónico y contraseña. Además, captura la ubicación GPS del dispositivo en tiempo real y la almacena en **Firebase Realtime Database**, lo que permite rastrear el movimiento del usuario de manera continua.

La aplicación está diseñada para:
- **Autenticación segura** mediante Firebase Authentication.
- **Obtención de la ubicación GPS** del dispositivo utilizando Google Play Services.
- **Almacenamiento de datos en tiempo real** en Firebase Realtime Database.

## Características principales
- **Inicio de sesión** con autenticación por correo electrónico y contraseña.
- **Rastreo de la ubicación GPS** en tiempo real.
- **Almacenamiento en la nube** de la latitud y longitud del usuario.
- **Compatibilidad** con versiones de Android Lollipop y superiores.

## Requisitos previos
- Android Studio instalado.
- Cuenta en **Firebase Console**.
- **Dispositivo Android** o emulador con acceso a GPS.

## Instalación

### Paso 1: Configuración de Firebase
1. Dirígete a [Firebase Console](https://console.firebase.google.com/) y crea un nuevo proyecto.
2. Habilita Firebase Authentication con correo electrónico y contraseña.
3. Habilita Firebase Realtime Database en modo de prueba.
4. Descarga el archivo `google-services.json` y colócalo en la carpeta `app` de tu proyecto en Android Studio.

### Paso 2: Agregar dependencias de Firebase
En el archivo `build.gradle` (app), agrega las siguientes dependencias:

```gradle
implementation 'com.google.firebase:firebase-auth:21.0.1'
implementation 'com.google.firebase:firebase-database:20.0.0'
implementation 'com.google.android.gms:play-services-location:21.0.1'
