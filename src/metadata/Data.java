package metadata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ei10117 on 29/03/2017.
 */
public class Data {


    private HashMap<String, File> files;

    public Data() {

        files = new HashMap<String, File>();
    }

    public void addFile(int repDegree,String fileID,int size)
    {
        files.put(fileID,new File(repDegree,fileID,size));
    }
    public File getFile(String fileId)
   {
       return files.get(fileId);
   }

    public void removeFile(String fileID){
        files.remove(fileID);
    }
    public HashMap<String, File> getFiles() {
        return files;
    }

    public void setFiles(HashMap<String, File> files) {
        this.files = files;
    }

    public boolean fileExist(String fileID)
    {
        return files.containsKey(fileID);
    }

    public boolean chunkExists(String fileID, int chunkNum){
        return files.get(fileID).getChunks().containsKey(chunkNum);
    }

    public void print()
    {
        System.out.println("-Printing Info");
        Set set = files.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            HashMap.Entry mentry = (HashMap.Entry)iterator.next();
            System.out.println(mentry.getKey());
            ((File)mentry.getValue()).print();
        }
    }

}
