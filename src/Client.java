
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    Service client;
    Scanner keyboardSc;
    int end = 0;
    int player = 1;

    //RMI Client gamer
    public Client() throws MalformedURLException, RemoteException, NotBoundException{
        client = (Service) Naming.lookup("TcTacTeo");
        keyboardSc = new Scanner(System.in);
    }
//to start playing the game and moving around locations
    public int Play() {
        int location;
        do {
            System.out.printf(
                    "\nPlayer %d, Enter square Number"
                            + " to place your move %c (or 0 for refreshing board): \n",
                    player, (player == 1) ? 'X' : 'O');
            location = keyboardSc.nextInt();
        } while (location > 9 || location < 0);
        return location;
    }
    //play and check if moving location is accepted or nor
    public void Game() throws RemoteException{
        int location;
        boolean isAccepted;
        do {
            player = ++player % 2;
            do {
                System.out.println(client.Board());
                location = Play();
                if (location != 0) {
                    isAccepted = client.play(--location / 3, location % 3, player);
                    if (!isAccepted)
                        System.out.println("Invalid try please, Try again.");
                } else
                    isAccepted = false;
            } while (!isAccepted);
            end = client.gameOver();
        } while (end == -1);
        System.out.println(client.Board());
    }
//when winning game and exit 
    public void winGame() {
        if (end == 2)
            System.out.printf("\nHow boring, it is a draw\n");
        else
            System.out.printf("\nWinner, player %d, You win the game!\n", end);
    }
    public static void main(String[] args) {
        try{
            Client c = new Client();
            c.Game();
            c.winGame();
        }
        catch (MalformedURLException e){ e.printStackTrace();}
        catch (RemoteException e){ e.printStackTrace();}
        catch (NotBoundException e){ e.printStackTrace();}
    }
}

