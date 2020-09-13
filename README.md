# Appium-Cucumber-SpringBoot

Framework de pruebas sobre aplicaciones mobile utilizando Appium

Tecnologias utilizadas:

- Lenguaje: Java - Maven
- Librerias: SpringBoot - Appium - Cucumber
- Reporte: Allure - Cucumber-reporting
- IDE: Eclipse
- SO: Windows 10

Configuración de ambiente:
1. Descargar y configurar variables de entorno de JAVA (JAVA_HOME y PATH): JDK_1.8.0
2. Descargar emulador de Android Studio (Se debe descargar e instalar Android Studio completo)
3. Configurar variables de entorno <br>
a. ANDROID_HOME: C:\Users\<Usuario>\AppData\Local\Android\Sdk <br>
b. PATH: <br>
 b.1. C:\Users\<Usuario>\AppData\Local\Android\Sdk <br>
 b.2. C:\Users\<Usuario>\AppData\Local\Android\Sdk\platform-tools <br>
 b.3. C:\Users\<Usuario>\AppData\Local\Android\Sdk\emulator
4. Descargar node.js
5. Instalar Appium: npm install -g appium
6. Instalar Appium-Doctor: npm install -g appium-doctor<br>
 a. Verificar que Appium esta configurado correctamente con el comando: appium-doctor
7.Para instalar Allure Report: <br>
a. Descargar scoop <br>
b. Instalar Allure: scoop install allure

Ejecutar pruebas:
- mvn clean test (basica)
- mvn clean test -Dambiente=test -Dcucumber.options="--tags '@Sumar' --tags '@High'" (detallada)

Reporte Cucumber-reporting:
- \target\cucumber-html-reports\overview-features.html

Ejecutar reporte Allure desde la raiz de proyecto:
- allure serve -h 127.0.0.1 -p 8087 --> -h 127.0.0.1 -p 8087 son opcionales
