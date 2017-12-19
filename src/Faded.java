import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Faded {
    private DataInputStream dis;
    private DataOutputStream dos;
    private String init="init",back="back";
    private Socket socket;

    File file = new File("H:\\");
    public Faded(DataInputStream dis, DataOutputStream dos, Socket socket) {
        this.dis = dis;
        this.dos=dos;
        this.socket=socket;
    }

    public void main(String[] args) throws IOException {
        System.out.println("faded main ");
        String root;
        root=file.getAbsolutePath();
        readFromClient(dis,dos);

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
            byte[] dataByte = new byte[32 * 1024];
            // byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            BufferedInputStream bufferData = new BufferedInputStream(new FileInputStream(new File(file.getAbsolutePath())));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            //dos.writeInt(bytes.length);
            dos.writeUTF(file.getName());
            int count;
            while ((count = bufferData.read(dataByte))>0){
                bufferedOutputStream.write(dataByte,0,count);
            }
            bufferData.close();
            bufferedOutputStream.close();
            // bufferedOutputStream.write(bytes,0,bytes.length);
           // bufferedOutputStream.flush();
            //dos.write(bytes);
        }

    }

}
