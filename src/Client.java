import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client
{
    private static Socket mSocket,mListen;
    public static void main(String[] args)
    {
        try
        {
            String host = "192.168.31.246";
            int port = 8080;
            InetAddress ad = InetAddress.getByName(host);
            mSocket = new Socket(ad,port);
            DataOutputStream dataOutputStream = new DataOutputStream(mSocket.getOutputStream());
            dataOutputStream.writeUTF("Client Is Requesting");
            while(true)
            {
                DataInputStream in = new DataInputStream(mSocket.getInputStream());
                System.out.println(in.readUTF());

            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                mSocket.close();
            }
            catch(Exception e){}
        }
    }
}