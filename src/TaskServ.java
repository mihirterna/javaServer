import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TaskServ {
    public static void main(String[] args) throws IOException {
        Socket socket;
        int port = 8080;
        ServerSocket serverSocket= new ServerSocket(port);
        while (true){
            socket=serverSocket.accept();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
           // System.out.println(dis.readUTF());
            Faded faded = new Faded(dis,dos,socket);
            faded.main(null);

        }

    }
}
