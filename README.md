# Gerenciador Remoto de Lista de Tarefas - Java RMI

## Descrição
Implementação de um aplicativo distribuído que permite gerenciar uma lista de tarefas através de chamadas remotas via Java RMI (Remote Method Invocation).

## Estrutura do Projeto

```
src/
├── TodoListService.java          # Interface remota
├── TodoListServiceImpl.java       # Implementação do servidor
├── TodoListServer.java           # Servidor RMI
└── TodoListClient.java           # Cliente RMI
```

## Componentes

### 1. TodoListService (Interface Remota)
Define os métodos disponíveis para o cliente:
- `addTask(String taskDescription)` - Adiciona uma nova tarefa
- `getAllTasks()` - Retorna todas as tarefas como array de strings
- `markTaskComplete(int taskIndex)` - Remove uma tarefa pelo índice

### 2. TodoListServiceImpl (Implementação)
Implementa a interface remota com:
- ArrayList para armazenar as tarefas
- Sincronização adequada para operações thread-safe
- Tratamento de erros para índices inválidos

### 3. TodoListServer (Servidor)
- Cria instância do serviço
- Inicia o registro RMI na porta 1099
- Vincula o serviço ao nome "TodoListService"

### 4. TodoListClient (Cliente)
- Menu interativo de linha de comando
- Opções: Adicionar, Visualizar, Concluir e Sair
- Tratamento de erros e exceções

## Como Compilar

### Windows
```bash
compilar.bat
```

### Linux/Mac
```bash
bash compilar.sh
```

Ou compilar manualmente:
```bash
javac -d bin src/*.java
```

## Como Executar

### Terminal 1 - Inicie o Servidor RMI
```bash
java -cp bin TodoListServer
```

Você verá:
```
[Servidor] Registro RMI criado na porta 1099
[Servidor] Serviço TodoListService registrado com sucesso!
[Servidor] Aguardando conexões de clientes...
```

### Terminal 2 - Inicie o Cliente
```bash
java -cp bin TodoListClient
```

Você será apresentado a um menu interativo.

## Menu do Cliente

```
=== Menu de Tarefas ===
1. Adicionar tarefa
2. Visualizar tarefas
3. Concluir tarefa
4. Sair
```

### Opções:
- **1 - Adicionar**: Digite uma descrição e a tarefa será adicionada ao servidor
- **2 - Visualizar**: Mostra todas as tarefas atuais com seus índices
- **3 - Concluir**: Remove uma tarefa pelo seu índice
- **4 - Sair**: Encerra o cliente

## Funcionalidades Implementadas

✅ Interface remota com 3 métodos  
✅ Servidor que gerencia ArrayList de tarefas  
✅ Cliente com menu interativo  
✅ Registro RMI automático na porta 1099  
✅ Tratamento de exceções (RemoteException, IndexOutOfBoundsException)  
✅ Validação de entrada do usuário  
✅ Implementação thread-safe com UnicastRemoteObject  
✅ Log de operações no servidor  

## Caso de Uso Exemplo

1. Inicie o servidor em um terminal
2. Abra outro terminal e inicie o cliente
3. Selecione opção 1 e adicione tarefas:
   - "Fazer compras"
   - "Estudar Java"
   - "Fazer exercício"
4. Selecione opção 2 para visualizar:
   ```
   [0] Fazer compras
   [1] Estudar Java
   [2] Fazer exercício
   ```
5. Selecione opção 3 e escolha um índice (ex: 1) para marcar "Estudar Java" como concluída
6. Visualize novamente para confirmar a remoção

## Requisitos
- Java 8 ou superior
- rmiregistry (geralmente incluído no JDK)

## Tecnologias
- Java RMI (Remote Method Invocation)
- java.rmi.Remote
- java.rmi.server.UniactRemoteObject
- java.rmi.registry

## Notas de Implementação

- O projeto mantém foco em um cliente único como indicado no enunciado
- A sincronização é realizada automaticamente pelo UniactRemoteObject
- As tarefas são armazenadas em memória durante a execução do servidor
- O registro RMI usa a porta padrão 1099
