
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Running {

    public static final int registryPort = 1099;

    public static void main(String args[]){

        try{
            Server ticTacTeoServer = new Server();

            final Registry reg = LocateRegistry.createRegistry(registryPort);
            reg.rebind("TcTacTeo",ticTacTeoServer);

            System.err.println("Server is Running now");

            System.in.read();

        }
        catch (IOException e){ e.printStackTrace();	}
    }
}

