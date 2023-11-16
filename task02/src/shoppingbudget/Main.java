package shoppingbudget;
import java.net.Socket;

public class Main{
    public static void main (String[] args) throws Exception{
        String server = "localhost";
        Integer port = 3000;

        if (args.length > 2){
            System.err.println("Too many arguments provided!");
            return;
        }
        else{
            if (args.length == 1){
                port = Integer.parseInt(args[0]);
            }
            else if (args.length == 2){
                server = args[0];
                port = Integer.parseInt(args[1]);
            }
        }

        System.out.printf("Connecting to %s on port %d...\n", server, port);
        Socket client = new Socket(server, port);
        System.out.println("Connected!");
        
        Client session = new Client(client);
        session.start();
    }
}