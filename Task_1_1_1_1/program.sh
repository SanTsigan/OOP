#!/bin/bash

# Указываем директории
SRC_DIR="src/main/java/ru/nsu/tsyganov"
BIN_DIR="bin"
DOC_DIR="docs"
JAR_FILE="app.jar"

# Создаем директорию для бинарных файлов и документации
mkdir -p $BIN_DIR
mkdir -p $DOC_DIR

# Компилируем Java-код
javac -d $BIN_DIR $SRC_DIR/*.java

# Генерация документации
javadoc -d $DOC_DIR $SRC_DIR/*.java

# Создание JAR-файла
jar cvf $JAR_FILE -C $BIN_DIR .

# Запуск приложения
java -cp $JAR_FILE ru.nsu.tsyganov.Main
