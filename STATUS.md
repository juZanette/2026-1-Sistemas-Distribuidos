# Resumo da Implementação - Gerenciador Remoto de Lista de Tarefas

## Status: ✅ IMPLEMENTAÇÃO COMPLETA

Todos os requisitos da atividade foram implementados com sucesso e testados.

---

## Arquivos Criados

### 1. **TodoListService.java** (Interface Remota)
- Estende `Remote`
- Define 3 métodos obrigatórios:
  - `void addTask(String taskDescription)` - Adiciona tarefa ao servidor
  - `String[] getAllTasks()` - Retorna todas as tarefas
  - `void markTaskComplete(int taskIndex)` - Remove tarefa pelo índice
- Todos os métodos declaram `throws RemoteException`
- Documentação completa com Javadoc

### 2. **TodoListServiceImpl.java** (Implementação do Servidor)
- Estende `UnicastRemoteObject` e implementa `TodoListService`
- Mantém `ArrayList<String>` para armazenar tarefas
- **addTask()**: 
  - Valida entrada (não permite vazio)
  - Adiciona string ao ArrayList
  - Log no console
- **getAllTasks()**: 
  - Retorna cópia do ArrayList como array (serializável)
  - Evita exposição direta da lista interna
- **markTaskComplete()**: 
  - Valida índice
  - Remove tarefa pelo índice
  - Lança `IndexOutOfBoundsException` para índices inválidos
  - Log da tarefa removida
- Implementa `serialVersionUID` conforme padrão RMI

### 3. **TodoListServer.java** (Servidor RMI)
- Cria instância de `TodoListServiceImpl`
- Tenta criar registro RMI na porta 1099
- Se registro já existe, continua normalmente
- Vincula serviço ao nome "TodoListService"
- Log de status do servidor
- Aguarda conexões indefinidamente

### 4. **TodoListClient.java** (Cliente Interativo)
- Conecta ao registro RMI na localhost na porta 1099
- Busca o serviço "TodoListService"
- Menu interativo com 4 opções:
  ```
  1. Adicionar tarefa
  2. Visualizar tarefas
  3. Concluir tarefa
  4. Sair
  ```
- **addTask()**: Solicita descrição e envia ao servidor
- **viewTasks()**: Recupera e exibe tarefas com índices
- **completeTask()**: Exibe tarefas, solicita índice, remove no servidor
- Tratamento de exceções:
  - `RemoteException` - Problemas de comunicação RMI
  - `IndexOutOfBoundsException` - Índice inválido
  - `NumberFormatException` - Entrada não numérica
- Loop continua até usuário escolher sair

---

## Arquivos de Suporte

### **compilar.bat** (Windows)
- Compila todos os arquivos .java do diretório src/
- Coloca .class em bin/
- Feedback ao usuário sobre sucesso/erro

### **compilar.sh** (Linux/Mac)
- Equivalente do batch para sistemas Unix

### **README.md**
- Documentação completa do projeto
- Instruções de compilação e execução
- Descrição de cada componente

### **STATUS.md** (este arquivo)
- Resumo da implementação
- Checklist de requisitos

---

## Checklist de Requisitos

✅ **Passo 1: Interface Remota** 
- Define interface `TodoListService` extends `Remote`
- Método `addTask(String taskDescription)` com `throws RemoteException`
- Método `getAllTasks()` retornando `String[]` com `throws RemoteException`
- Método `markTaskComplete(int taskIndex)` com `throws RemoteException`

✅ **Passo 2: Implementação do Servidor**
- Classe implementa interface remota
- Mantém `ArrayList<String>` para tarefas
- `addTask()` adiciona string ao ArrayList
- `getAllTasks()` retorna cópia do ArrayList
- `markTaskComplete()` remove tarefa pelo índice
- Tratamento de erros para índices inválidos
- `main()` cria instância, inicia registro RMI, vincula serviço
- Nome do serviço: "TodoListService"
- Sincronização adequada (UnicastRemoteObject)

✅ **Passo 3: Cliente Interativo**
- `main()` procura "TodoListService" no registro RMI
- Menu interativo via linha de comando:
  - "1" para Adicionar
  - "2" para Visualizar
  - "3" para Concluir
  - "4" para Sair
- Opção "Adicionar": solicita descrição, chama `addTask()`
- Opção "Visualizar": chama `getAllTasks()` e exibe com índices
- Opção "Concluir": solicita índice, chama `markTaskComplete()`
- Tratamento de `IndexOutOfBoundsException`
- Menu continua até usuário escolher sair

---

## Como Usar

### Compilação
```bash
# Windows
compilar.bat

# Linux/Mac
bash compilar.sh

# Ou manual
javac -d bin src/*.java
```

### Execução

**Terminal 1 - Servidor:**
```bash
java -cp bin TodoListServer
```

**Terminal 2 - Cliente:**
```bash
java -cp bin TodoListClient
```

### Exemplo de Sessão
```
[Cliente] Conectado ao servidor TodoListService com sucesso!

=== Menu de Tarefas ===
1. Adicionar tarefa
2. Visualizar tarefas
3. Concluir tarefa
4. Sair
Escolha uma opção: 1
Digite a descrição da tarefa: Estudar Java RMI
Tarefa adicionada com sucesso!

=== Menu de Tarefas ===
1. Adicionar tarefa
2. Visualizar tarefas
3. Concluir tarefa
4. Sair
Escolha uma opção: 2

=== Tarefas Atuais ===
[0] Estudar Java RMI

=== Menu de Tarefas ===
1. Adicionar tarefa
2. Visualizar tarefas
3. Concluir tarefa
4. Sair
Escolha uma opção: 4
Encerrando cliente...
```

---

## Tecnologias Utilizadas

- **Java RMI** - java.rmi.*
- **Remote Interface** - java.rmi.Remote
- **UnicastRemoteObject** - java.rmi.server.UnicastRemoteObject
- **Registry** - java.rmi.registry.LocateRegistry/Registry
- **ArrayList** - java.util.ArrayList
- **Scanner** - java.util.Scanner (I/O)

---

## Pontos Importantes da Implementação

1. **Serialização**: `String[]` é serializável e pode ser transmitido via RMI
2. **Segurança**: Não expõe ArrayList diretamente, retorna cópia
3. **Validação**: Índices verificados antes de acesso
4. **Sincronização**: UnicastRemoteObject fornece sincronização automática
5. **Tratamento de Erros**: Todos os requisitos cobertos
6. **Componentização**: Código bem separado entre interface, implementação, servidor e cliente
7. **Documentação**: Completa com Javadoc

---

## Próximas Melhorias (Opcionais)

- Persistência de dados em arquivo
- Suporte para múltiplos clientes simultâneos com sincronização explícita
- Interface gráfica (Swing/JavaFX)
- Serviço de autenticação
- Busca/Filtro de tarefas
- Datas de vencimento
- Prioridades de tarefas

---

**Data de Implementação:** 31 de março de 2026  
**Status:** ✅ Pronto para entrega
