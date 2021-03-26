
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Service extends Remote{

    String Board() throws RemoteException;

    boolean play(int row, int col, int player) throws RemoteException;

    int gameOver() throws RemoteException;
}
