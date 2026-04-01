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
