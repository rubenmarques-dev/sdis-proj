package metadata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ei10117 on 29/03/2017.
 */
public class File {

    private int replicationDegree;
    private String fileID;
    private HashMap<Integer, Metadata>  chunks;
    private int size;

    public File(int replicationDegree,String fileID, int size) {
        this.replicationDegree = replicationDegree;
        this.fileID = fileID;
        this.size = size;
        chunks = new HashMap<Integer,Metadata>();
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public HashMap<Integer, Metadata> getChunks() {
        return chunks;
    }

    public void setChunks(HashMap<Integer, Metadata> chunks) {
        this.chunks = chunks;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addSaver(int chunkNo, int peerIdD){
        chunks.get(chunkNo).addSaver(peerIdD);
    }

    public int getReplicationDegree() {
        return replicationDegree;
    }

    public void setReplicationDegree(int replicationDegree) {
        this.replicationDegree = replicationDegree;
    }

    public  void printBackup()
    {
        Set set = chunks.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            HashMap.Entry mentry = (HashMap.Entry)iterator.next();
            System.out.println(mentry.getKey());
            ((Metadata)mentry.getValue()).printBackups();
        }
    }

    public  void printStored()
    {
        Set set = chunks.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            HashMap.Entry mentry = (HashMap.Entry)iterator.next();
            System.out.println(mentry.getKey());
            ((Metadata)mentry.getValue()).printStored();
        }
    }



}
