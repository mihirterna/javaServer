import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;

public class Faded implements Runnable {
    private DataInputStream dis;
    private DataOutputStream dos;
    private String init="init";
    private Socket socket;

    File file = new File("C:\\");
    public Faded(DataInputStream dis, DataOutputStream dos, Socket socket) {
        this.dis = dis;
        this.dos=dos;
        this.socket=socket;
    }

    public void main(String[] args) throws IOException {
        while (true) run();

    }
    private void readFromClient(DataInputStream dis, DataOutputStream dos) throws IOException {
        System.out.println(socket.getRemoteSocketAddress());
        String word = dis.readUTF();
        if(word.equalsIgnoreCase(init)) sendToClient(dos,null);
        else sendToClient(dos,word);
    }

    private void sendToClient(DataOutputStream dos, String word) throws IOException {
        if (word!=null) file=new File(file,word);
        if (file.isDirectory()){
            File[] list = file.listFiles();
            dos.writeUTF(String.valueOf(list.length));
            int i =0;
            for (File aList : list) {
                System.out.println(i+"\t"+aList.getName());
                dos.writeUTF(aList.getName());
                i++;
            }
        }
        if(file.isFile()){
            System.out.println(file.getName()+" is a file.");
            dos.writeUTF("file");
            dos.writeUTF(file.getName());
            int BUF_SIZE = 64000,count;
            socket.setReceiveBufferSize(BUF_SIZE);
            socket.setSendBufferSize(BUF_SIZE);
            byte[] dataByte = new byte[BUF_SIZE];

            BufferedInputStream bufferData = new BufferedInputStream(new FileInputStream(new File(file.getAbsolutePath())));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            while ((count = bufferData.read(dataByte))>0){
                bufferedOutputStream.write(dataByte,0,count);
                bufferedOutputStream.flush();
            }
            bufferData.close();
            bufferedOutputStream.close();

         }

    }

    @Override
    public void run() {
        System.out.println("faded main ");
        String root;
        root=file.getAbsolutePath();
        try {
            readFromClient(dis,dos);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
