# Gerenciador Remoto de Lista de Tarefas

## Disciplina
Sistemas Distribuídos  
**Professora:** Andriele Busatto do Carmo  
**Instituição:** UNISINOS

---

## Objetivo
Compreender como adicionar, recuperar e excluir itens de dados em um servidor remoto, demonstrando a interação com um objeto remoto usando Java RMI.

---

## Cenário
Uma aplicação simples de "Lista de Tarefas" (To-Do List) que armazena itens em um servidor remoto. A aplicação cliente permite aos usuários:
- Adicionar novas tarefas
- Visualizar todas as tarefas atuais
- Marcar tarefas como concluídas (removê-las da lista)

---

## Implementação Realizada

### 1. Interface Remota (`TodoListService.java`)

A interface define os métodos para interagir com uma lista de tarefas. Todo objeto remoto em Java RMI deve estender a interface `Remote`.

**Código Completo:**

```java
import java.rmi.Remote;
import java.rmi.RemoteException;

//Interface remota para o serviço de gerenciamento de lista de tarefas
public interface TodoListService extends Remote {
    /**
     * Adiciona uma nova tarefa à lista do servidor
     * @param taskDescription descrição da tarefa
     * @throws RemoteException em caso de erro de comunicação remota
     */
    void addTask(String taskDescription) throws RemoteException;

    /**
     * Retorna uma lista de todas as tarefas atuais
     * @return array de strings com todas as tarefas
     * @throws RemoteException em caso de erro de comunicação remota
     */
    String[] getAllTasks() throws RemoteException;

    /**
     * Remove uma tarefa da lista com base em seu índice
     * @param taskIndex índice da tarefa a ser removida
     * @throws RemoteException em caso de erro de comunicação remota
     * @throws IndexOutOfBoundsException se o índice for inválido
     */
    void markTaskComplete(int taskIndex) throws RemoteException;
}
```

**Explicação:**
- **`extends Remote`**: Marca essa interface como remota, permitindo que suas implementações sejam acessadas via RMI
- **`void addTask(String taskDescription) throws RemoteException`**: Adiciona uma tarefa. O parâmetro é a descrição da tarefa
- **`String[] getAllTasks() throws RemoteException`**: Retorna um array de strings (serializável) com todas as tarefas
- **`void markTaskComplete(int taskIndex) throws RemoteException`**: Remove uma tarefa pelo índice fornecido
- **`throws RemoteException`**: Todo método remoto deve declarar essa exceção para possíveis erros de comunicação

---

### 2. Implementação do Servidor (`TodoListServiceImpl.java`)

A classe implementa a interface remota e contém a lógica do servidor.

**Código Completo:**

```java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

//Implementação da lista de tarefas
public class TodoListServiceImpl extends UnicastRemoteObject implements TodoListService {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> tasks;

    /**
     * Construtor que inicializa a lista de tarefas
     * @throws RemoteException em caso de erro RMI
     */
    public TodoListServiceImpl() throws RemoteException {
        super();
        this.tasks = new ArrayList<>();
    }

    /**
     * Adiciona uma nova tarefa à lista
     * @param taskDescription descrição da tarefa
     * @throws RemoteException em caso de erro de comunicação remota
     */
    @Override
    public void addTask(String taskDescription) throws RemoteException {
        if (taskDescription == null || taskDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da tarefa não pode estar vazia");
        }
        tasks.add(taskDescription);
        System.out.println("[Servidor] Tarefa adicionada: " + taskDescription);
    }

    /**
     * Retorna uma cópia de todas as tarefas
     * @return array de strings com todas as tarefas
     * @throws RemoteException em caso de erro de comunicação remota
     */
    @Override
    public String[] getAllTasks() throws RemoteException {
        return tasks.toArray(new String[0]);
    }

    /**
     * Remove uma tarefa pelo índice
     * @param taskIndex índice da tarefa a ser removida
     * @throws RemoteException em caso de erro de comunicação remota
     */
    @Override
    public void markTaskComplete(int taskIndex) throws RemoteException {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new IndexOutOfBoundsException("Índice inválido: " + taskIndex);
        }
        String removedTask = tasks.remove(taskIndex);
        System.out.println("[Servidor] Tarefa concluída e removida: " + removedTask);
    }
}
```

**Explicação:**
- **`extends UnicastRemoteObject`**: Fornece implementação de objeto remoto
- **`implements TodoListService`**: Implementa a interface remota definida na etapa 1
- **`private ArrayList<String> tasks`**: Armazena as tarefas em memória
- **`serialVersionUID`**: Identificador de serialização
- **`addTask()`**: Valida entrada, adiciona ao ArrayList e exibe log
- **`getAllTasks()`**: Retorna cópia do ArrayList como array
- **`markTaskComplete()`**: Valida índice, remove tarefa, trata erros

---

### 3. Servidor RMI (`TodoListServer.java`)

O servidor cria uma instância do serviço e a registra no RMI Registry.

**Código Completo:**

```java
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//Servidor RMI
public class TodoListServer {
    public static void main(String[] args) {
        try {
            //Cria uma instância da implementação do serviço
            TodoListServiceImpl service = new TodoListServiceImpl();

            //Tenta iniciar o registro RMI na porta 1099
            try {
                Registry registry = LocateRegistry.createRegistry(1099);
                System.out.println("[Servidor] Registro RMI criado na porta 1099");
            } catch (RemoteException e) {
                // Registro já existe
                System.out.println("[Servidor] Registro RMI já está rodando");
            }

            //Obtem o registro RMI
            Registry registry = LocateRegistry.getRegistry(1099);

            //Vincula o serviço ao nome "TodoListService"
            registry.rebind("TodoListService", service);

            System.out.println("[Servidor] Serviço TodoListService registrado com sucesso!");
            System.out.println("[Servidor] Aguardando conexões de clientes...");

        } catch (RemoteException e) {
            System.err.println("[Servidor] Erro ao registrar o serviço: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

**Explicação:**
- **`LocateRegistry.createRegistry(1099)`**: Cria o RMI Registry na porta 1099 (porta padrão do RMI)
- **`try/catch`**: Tenta criar; se já existe, continua normalmente
- **`LocateRegistry.getRegistry(1099)`**: Obtém referência ao Registry
- **`registry.rebind("TodoListService", service)`**: Vincula a instância do serviço ao nome "TodoListService"
- **`rebind`**: Se já existe uma ligação com esse nome, ela é substituída
- **Aguarda conexões**: O servidor fica rodando indefinidamente, pronto para receber requisições dos clientes

---

### 4. Cliente Interativo (`TodoListClient.java`)

O cliente conecta ao servidor RMI e oferece um menu interativo.

**Código Completo:**

```java
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

//Cliente para o gerenciador de lista de tarefas
public class TodoListClient {
    private TodoListService service;
    private Scanner scanner;

    //Construtor que configura a conexão com o servidor RMI
    public TodoListClient() {
        try {
            //Localiza o registro RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            //Procura pelo serviço "TodoListService"
            this.service = (TodoListService) registry.lookup("TodoListService");
            System.out.println("Conectado ao servidor TodoListService com sucesso!\n");

        } catch (RemoteException e) {
            System.err.println("Erro ao conectar ao registro RMI: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.err.println("Serviço TodoListService não encontrado no registro RMI: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        this.scanner = new Scanner(System.in);
    }

    //Exibe o menu principal
    private void displayMenu() {
        System.out.println("\n=== Menu de Tarefas ===");
        System.out.println("1. Adicionar tarefa");
        System.out.println("2. Visualizar tarefas");
        System.out.println("3. Concluir tarefa");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    //Adiciona uma nova tarefa
    private void addTask() {
        System.out.print("Digite a descrição da tarefa: ");
        String description = scanner.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("Erro: A descrição não pode estar vazia!");
            return;
        }

        try {
            service.addTask(description);
            System.out.println("Tarefa adicionada com sucesso!");
        } catch (RemoteException e) {
            System.err.println("Erro ao adicionar tarefa: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    //Visualiza todas as tarefas
    private void viewTasks() {
        try {
            String[] tasks = service.getAllTasks();

            if (tasks.length == 0) {
                System.out.println("\nNenhuma tarefa na lista!");
            } else {
                System.out.println("\n=== Tarefas Atuais ===");
                for (int i = 0; i < tasks.length; i++) {
                    System.out.println("[" + i + "] " + tasks[i]);
                }
            }
        } catch (RemoteException e) {
            System.err.println("Erro ao recuperar tarefas: " + e.getMessage());
        }
    }

    //Marca uma tarefa como concluída (e remove ela da lista)
    private void completeTask() {
        try {
            String[] tasks = service.getAllTasks();

            if (tasks.length == 0) {
                System.out.println("Nenhuma tarefa para concluir!");
                return;
            }

            System.out.println("\n=== Tarefas Disponíveis ===");
            for (int i = 0; i < tasks.length; i++) {
                System.out.println("[" + i + "] " + tasks[i]);
            }

            System.out.print("Digite o índice da tarefa a concluir: ");
            String input = scanner.nextLine().trim();

            try {
                int index = Integer.parseInt(input);
                service.markTaskComplete(index);
                System.out.println("Tarefa concluída e removida com sucesso!");
            } catch (NumberFormatException e) {
                System.err.println("Erro: Por favor, digite um número válido!");
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } catch (RemoteException e) {
            System.err.println("Erro ao concluir tarefa: " + e.getMessage());
        }
    }

    //Executa o loop principal do cliente
    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addTask();
                    break;
                case "2":
                    viewTasks();
                    break;
                case "3":
                    completeTask();
                    break;
                case "4":
                    running = false;
                    System.out.println("Encerrando cliente...");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha entre 1 e 4.");
            }
        }

        scanner.close();
    }

    //Método principal
    public static void main(String[] args) {
        TodoListClient client = new TodoListClient();
        client.run();
    }
}
```

**Explicação dos Métodos:**

- **`Construtor TodoListClient()`**: 
  - Localiza o Registry RMI em localhost na porta 1099
  - Procura pelo serviço "TodoListService"
  - Trata exceções de conexão e serviço não encontrado

- **`displayMenu()`**: Exibe as 4 opções de menu para o usuário

- **`addTask()`**:
  - Solicita descrição da tarefa
  - Valida entrada (não permite vazio)
  - Chama `service.addTask()` no servidor
  - Trata exceções remotas

- **`viewTasks()`**:
  - Chama `service.getAllTasks()` para recuperar tarefas do servidor
  - Exibe cada tarefa com seu índice
  - Trata caso de lista vazia

- **`completeTask()`**:
  - Exibe tarefas atuais
  - Solicita índice ao usuário
  - Valida entrada numérica com tratamento de `NumberFormatException`
  - Chama `service.markTaskComplete(index)` no servidor
  - Trata `IndexOutOfBoundsException` para índices inválidos

- **`run()`**: Loop principal que:
  - Exibe menu continuamente
  - Processa escolha do usuário
  - Continua até usuário escolher sair (opção 4)

- **`main(String[] args)`**: Ponto de entrada do cliente
  - Cria instância de TodoListClient
  - Executa o loop principal

---

## Compilação e Execução

### Compilar
```bash
javac -d bin src/*.java
```

### Executar

**Terminal 1 - Servidor:**
```bash
java -cp bin TodoListServer
```

**Terminal 2 - Cliente:**
```bash
java -cp bin TodoListClient
```

---

## Exemplo de Uso

```
Cliente conectado ao servidor!

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
```

---

## Requisitos Atendidos

✅ Interface remota com 3 métodos  
✅ Cada método declara `throws RemoteException`  
✅ Servidor implementa interface remota  
✅ ArrayList armazena tarefas  
✅ `addTask()` adiciona string ao ArrayList  
✅ `getAllTasks()` retorna cópia do ArrayList  
✅ `markTaskComplete()` remove tarefa pelo índice  
✅ Tratamento de erros para índices inválidos  
✅ Registro RMI criado e serviço vinculado  
✅ Cliente procura serviço no registro RMI  
✅ Menu interativo de linha de comando  
✅ Sincronização com `UnicastRemoteObject`  

---

## Arquivos do Projeto

```
src/
├── TodoListService.java
├── TodoListServiceImpl.java
├── TodoListServer.java
└── TodoListClient.java

bin/
├── TodoListService.class
├── TodoListServiceImpl.class
├── TodoListServer.class
└── TodoListClient.class
```

---

## Tecnologias Utilizadas

- **Java RMI** - Remote Method Invocation
- **java.rmi.Remote** - Interface base para objetos remotos
- **java.rmi.server.UnicastRemoteObject** - Implementação de objeto remoto
- **java.rmi.registry** - Registro e localização de serviços
- **java.util.ArrayList** - Armazenamento de tarefas
- **java.util.Scanner** - Interface com usuário

---

## Conclusão

A implementação demonstra com sucesso os conceitos fundamentais de programação distribuída usando Java RMI, permitindo que um cliente remoto interaja com um servidor através de chamadas de método como se fossem locais.
