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
