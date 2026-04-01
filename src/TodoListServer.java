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
