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

    // Marca uma tarefa como concluída (e remove ela da lista)
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


    //main
    public static void main(String[] args) {
        TodoListClient client = new TodoListClient();
        client.run();
    }
}
