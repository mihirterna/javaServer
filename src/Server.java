
import java.net.*;
import java.io.*;

public class Server
{
    private static Socket mSocket,mSend;
    public static void main(String[] args)
    {
        File file = new File("H:\\");
        String root;
        root=file.getAbsolutePath();
        int i = 2;
        try
        {
            int port = 8080;
            ServerSocket ss = new ServerSocket(port);
            while(i>1)
            {
                mSocket = ss.accept();
                    DataInputStream dis = new DataInputStream(mSocket.getInputStream());
                    System.out.println(dis.readUTF());
                    sendUTF(file,mSocket);
                    System.gc();
                i--;
            }
            while (true){
                mSocket = ss.accept();
                DataInputStream dis1= new DataInputStream(mSocket.getInputStream());
                String path = dis1.readUTF();
                System.out.println("User Clicked -> "+path);

                    File f1 = new File(file,path);
                    if(f1.isDirectory()){
                        try {
                            if(f1.listFiles().length>0){
                                sendUTF(f1,mSocket);
                            }
                            else {
                                System.out.println("Empty Directory");
                                File newf = new File(f1,"temp");
                                sendUTF(newf,mSocket);

                            }

                        }
                        catch (NullPointerException ne) {
                            System.out.println(ne.getMessage());
                        }
                }
                else {
                        System.out.println("Emp Dir");
                        File newf = new File(f1,"temp");
                        sendUTF(newf,mSocket);

                    }

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

    private static void sendUTF(File file,Socket mSocket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(mSocket.getOutputStream());
        File[] list = file.listFiles();
        dataOutputStream.writeUTF(String.valueOf(list.length));
        int i =0;
        for (File aList : list) {
            System.out.println(i+"\t"+aList.getName());
            dataOutputStream.writeUTF(aList.getName());
            i++;
        }

    }
}