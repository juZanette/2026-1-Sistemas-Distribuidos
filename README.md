# M3 - Gerenciador Remoto de Lista de Tarefas - Java RMI

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

## Exemplos de uso

Exemplo 1:
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

Exemplo 2:

<img width="900" height="150" alt="image" src="https://github.com/user-attachments/assets/70623b41-3980-4083-a67e-ec7b33885ee8" />
<img width="900" height="237" alt="image" src="https://github.com/user-attachments/assets/4e3d8a69-b1a1-4820-b0b2-c56901364817" />
<img width="900" height="338" alt="image" src="https://github.com/user-attachments/assets/39127131-c10c-4db3-8aeb-2a339da58c2a" />
<img width="733" height="488" alt="image" src="https://github.com/user-attachments/assets/cd1104eb-0668-4b77-8d69-904961e5da84" />
<img width="900" height="541" alt="image" src="https://github.com/user-attachments/assets/8a4e76de-ad19-4620-afef-f88379cbce49" />
<img width="900" height="564" alt="image" src="https://github.com/user-attachments/assets/5dd2d150-88d1-47aa-8537-426db52bd1f7" />
<img width="900" height="617" alt="image" src="https://github.com/user-attachments/assets/15f09b06-d141-4065-9755-89b3d79d4546" />
<img width="900" height="593" alt="image" src="https://github.com/user-attachments/assets/d50801fa-e1b2-4a7b-a763-83758b0a28df" />
<img width="900" height="579" alt="image" src="https://github.com/user-attachments/assets/a5b7fe48-b5e1-4305-857a-4ea1b735441e" />






   
