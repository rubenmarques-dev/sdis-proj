package fileSystem;

import java.io.*;

/**
 * Created by ei10117 on 10/03/2017.
 */
public class Chunk  implements Serializable{

    private String fileId;
    private String senderId;
    private int chunkNo;
    private byte[] content;
    private static  String basePath = "../Proj/files/";

    public Chunk(String fileId, String senderId, int chunkNo) {
        this.fileId = fileId;
        this.senderId = senderId;
        this.chunkNo = chunkNo;
    }

    public void save() {
        String path = basePath + fileId + "/";
        if(FileManager.fileExists(path)){
            try {
                FileOutputStream fileOut = new FileOutputStream(path  + chunkNo + ".ser");

                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this);
                out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved in"  + path + chunkNo + ".ser");
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
        else
        {
            System.out.println("Directory doesnt exists");
        }
    }

    public static Chunk load(String fileId, int chunkNo) throws FileNotFoundException, ClassNotFoundException {
        Chunk e;
        String path = basePath + new String(fileId)+ "/" + chunkNo;
        try
        {
            //FileInputStream fileIn = new FileInputStream("../Desktop/temp/outgoing/" +new String(fileid)+"/"+chunkNo+".ser");
            FileInputStream fileIn = new FileInputStream(path+".ser");
            System.out.println(path+".ser");

            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Chunk) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
        return e;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFileId() {
        return fileId;
    }

    public String getSenderId() {
        return senderId;
    }

    public int getChunkNo() {
        return chunkNo;
    }
}
