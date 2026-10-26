# RIGGO — instalación demo (no producción)

La demo de `docs/index.html` funciona sin servidores y permite navegar los perfiles. **No existe backend conectado ni validación real de visitas NFC**.

## iPhone
1. Habilitar GitHub Pages desde **Settings > Pages > Source: GitHub Actions** del repositorio público (el conector no tiene el permiso de administración para hacerlo).
2. Abrir la URL HTTPS publicada en Safari.
3. Compartir > **Agregar a pantalla de inicio**.
4. Para leer/escribir NFC dentro de la app debe existir una aplicación **nativa iOS** con Core NFC, entitlement y firma Apple. Web NFC no está implementado en Safari.

## Android 9+
Abrir la misma URL en Chrome, menú > Agregar a pantalla principal / Instalar. La demo funciona. El diagnóstico de NFC Web requiere Chrome compatible, NFC en el teléfono y permiso del usuario. Escribir la URL de diagnóstico NO identifica ni acredita al socio.

## Próximos pasos
Para operación real, configurar Firebase Auth, Firestore, Functions, Hosting; distribuir cuentas y permisos reales desde el backend; y probar llaveros físicos. Código backend y emuladores en repo privado `octidou23-png/riggo-android-demo`.
