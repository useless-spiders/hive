#!/usr/bin/env bash

# Construire le projet avec Maven
echo "Construction du projet avec Maven..."
mvn -B package --file pom.xml
if [ $? -ne 0 ]; then
    echo "La construction Maven a échoué !"
    exit 1
fi
echo "La construction Maven a réussi."

# Exécuter l'application Java
echo "Exécution de l'application Java..."
java -jar target/*.jar
if [ $? -ne 0 ]; then
    echo "L'application Java a échoué à s'exécuter !"
    exit 1
fi
echo "Fin d'exécution."