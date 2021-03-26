
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements Service {
    //intialize gane board locations
    private char gameBoard[][] = {
            { '1', '2', '3' },
            { '4', '5', '6' },
            { '7', '8', '9' }
    };
    private int Player = 0;
    private int num = 0;
    public Server() throws RemoteException{
        super();
    }

    @Override
    public String Board() throws RemoteException{
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n ");
//draw the board
        synchronized (this) {
            builder.append(gameBoard[0][0]).append(" || ");
            builder.append(gameBoard[0][1]).append(" || ");
            builder.append(gameBoard[0][2]).append(" ");
            builder.append("\n---+---+---\n ");
            builder.append(gameBoard[1][0]).append(" || ");
            builder.append(gameBoard[1][1]).append(" || ");
            builder.append(gameBoard[1][2]).append(" ");
            builder.append("\n---+---+---\n ");
            builder.append(gameBoard[2][0]).append(" || ");
            builder.append(gameBoard[2][1]).append(" || ");
            builder.append(gameBoard[2][2]).append(" \n");
        }return builder.toString();}

    @Override
    //play in cmd and move around rows and columns
    public boolean play(int row, int col, int player) throws RemoteException{
        if (!(row >= 0 && row < 3 && col >= 0 && col < 3))
            return false;
        synchronized (this) {
            if (gameBoard[row][col] > '9')
                return false;
            if (player != Player)
                return false;
            if (num == 9)
                return false;
            gameBoard[row][col] = (player == 1) ? 'X' : 'O';
            Player = (Player + 1) % 2;
            num++;
            return true;
        }}
    
    @Override
    //intialize game over conditions and winning
    public synchronized int gameOver() throws RemoteException{
        int i;
        if ((gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2])
                || (gameBoard[0][2] == gameBoard[1][1] && gameBoard[0][2] == gameBoard[2][0])) {
            if (gameBoard[1][1] == 'X')
                return 1;
            else
                return 0;
        } else
            
            for (i = 0; i <= 2; i++) {
                if ((gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][0] == gameBoard[i][2])) {
                    if (gameBoard[i][0] == 'X')
                        return 1;
                    else
                        return 0;
                }
                if ((gameBoard[0][i] == gameBoard[1][i] && gameBoard[0][i] == gameBoard[2][i])) {
                    if (gameBoard[0][i] == 'X')
                        return 1;
                    else
                        return 0;
                }}
        if (num == 9)
            return 2;
        else
            return -1;
    }}

