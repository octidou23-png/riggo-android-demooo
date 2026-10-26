# RIGGO Android — Demo de presentación

Esta versión permite recorrer Inicio, Socio, Comercio y Admin en una aplicación Android autocontenida. **No contiene Firebase conectado ni NFC físico**; todos los datos y movimientos son simulados y locales.

## Compilar
GitHub Actions ejecuta automáticamente el trabajo **Compilar RIGGO Demo APK** al subir cambios en main.
- Abrí el repositorio → pestaña Actions → Compilar RIGGO Demo APK.
- Esperá hasta que el trabajo indique éxito.
- Descargá el artefacto **RIGGO-DEMO-APK-API35** y extraé el ZIP que entrega GitHub.
- Dentro estará **app-debug.apk**.
- Pasá la APK al Redmi, tocala y seguí la instalación de Android. No desactives Play Protect ni intentes forzar la instalación si aparece una advertencia.

La APK usa targetSdk 35 / minSdk 26, compila con AGP 8.7.3 + Gradle 8.9 y JDK 17. Se firma con la firma estándar de depuración del ejecutor de GitHub. Solo para demostración.

### Seguridad
- Sin permisos INTERNET ni NFC.
- El HTML se sirve desde memoria con un origen local virtual HTTPS.
- Las contraseñas .demo son ficticias y están embebidas en la aplicación. No usar con usuarios reales.
- Las operaciones sensibles de Firebase/Cloud Functions de RIGGO V12.2.1 deben integrarse y probarse por separado.
- No es una APK para distribuir comercialmente.

## Siguiente etapa
Conectar Firebase real en staging, implementar NFC nativo NTAG213/215 y realizar prueba física en Redmi.
