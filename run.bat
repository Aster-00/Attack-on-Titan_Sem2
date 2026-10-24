@echo off
rem Compiles and launches the game. Requires a JDK and the JavaFX SDK in lib\javafx-sdk-25.0.1
rem (download: https://gluonhq.com/products/javafx/).
cd /d "%~dp0"
set FX=lib\javafx-sdk-25.0.1\lib
set MODS=javafx.controls,javafx.fxml,javafx.media

if exist out rmdir /s /q out
dir /s /b src\*.java > sources.txt
javac --module-path "%FX%" --add-modules %MODS% -d out @sources.txt || (del sources.txt & pause & exit /b 1)
del sources.txt
xcopy /s /q /y src\game\gui\*.fxml out\game\gui\ >nul
xcopy /s /q /y src\game\gui\*.css out\game\gui\ >nul
xcopy /s /q /y /i src\game\gui\tools out\game\gui\tools >nul

java --module-path "%FX%" --add-modules %MODS% --enable-native-access=javafx.graphics,javafx.media -cp out game.gui.Main
