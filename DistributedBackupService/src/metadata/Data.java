package metadata;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ei10117 on 29/03/2017.
 */
public class Data{


    private HashMap<String, File> myBackups;
    private HashMap<String, File> myStores;

    public Data() {

        myBackups = new HashMap<String, File>();
        myStores = new HashMap<String, File>();
    }

    public void addBackupFile(int repDegree, String fileID, String filepath, int size)
    {
        myBackups.put(fileID,new File(repDegree,fileID,filepath,size));
    }
    public File getBackupFile(String fileId)
   {
       return myBackups.get(fileId);
   }

    public void addStoredFile(int repDegree, String fileID,String filepath, int size)
    {
        myStores.put(fileID,new File(repDegree,fileID,filepath,size));
    }
    public File getStoredFile(String fileId)
    {
        return myStores.get(fileId);
    }


    public void removeBackup(String fileID){
        myBackups.remove(fileID);
    }
    public void removeStored(String fileID){
        myStores.remove(fileID);
    }

    public HashMap<String, File> getMyBackups() {
        return myBackups;
    }

    public void setMyBackups(HashMap<String, File> myBackups) {
        this.myBackups = myBackups;
    }

    public HashMap<String, File> getMyStores() {
        return myStores;
    }

    public void setMyStores(HashMap<String, File> myStores) {
        this.myStores = myStores;
    }

    public boolean backupExists(String fileID)
    {
        return myBackups.containsKey(fileID);
    }

    public boolean storesExists(String fileID)
    {
        return myStores.containsKey(fileID);
    }

    public boolean chunkExistsBackup(String fileID, int chunkNum){
        return myBackups.get(fileID).getChunks().containsKey(chunkNum);
    }

    public boolean chunkExistsStored(String fileID, int chunkNum){
        return myStores.get(fileID).getChunks().containsKey(chunkNum);
    }

    public void print()
    {
        System.out.println("-Backups");
        Set set = myBackups.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            HashMap.Entry mentry = (HashMap.Entry)iterator.next();
            System.out.println(mentry.getKey());
            ((File)mentry.getValue()).printBackup();
        }

        System.out.println("-Stores");
        set = myStores.entrySet();
        iterator = set.iterator();
        while (iterator.hasNext()){
            HashMap.Entry mentry = (HashMap.Entry)iterator.next();
            System.out.println(mentry.getKey());
            ((File)mentry.getValue()).printStored();
        }
    }


    public String getFilepath(String fileSHA)
    {
        return myBackups.get(fileSHA).getFilepath();
    }




}
