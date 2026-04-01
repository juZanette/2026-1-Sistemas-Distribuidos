#!/bin/bash
# Script para compilar o projeto RMI

echo ""
echo "=== Compilando arquivos Java ==="
echo ""

# Ir para o diretório do script
cd "$(dirname "$0")"

# Compilar todos os arquivos .java
javac -d bin src/*.java

if [ $? -ne 0 ]; then
    echo "Erro na compilação!"
    exit 1
fi

echo ""
echo "=== Compilação concluída com sucesso! ==="
echo ""
echo "Arquivos compilados em: bin/"
echo ""
echo "Para executar:"
echo "1. Em um terminal: java -cp bin TodoListServer"
echo "2. Em outro terminal: java -cp bin TodoListClient"
echo ""
