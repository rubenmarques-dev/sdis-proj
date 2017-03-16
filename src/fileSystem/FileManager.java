package fileSystem;


import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by zabrn on 29/03/2016.
 */
public class FileManager {

    static private String basePath = "data/";


    public FileManager() throws IOException {
        if(!fileExists(""))
        {
            createDirectory("");
        }
    }

    public static boolean fileExists(String path)
    {
        return (new File(path)).exists();
    }



    public static String getPath(String fileID)
    {
        String path = basePath + fileID;
        return path;
    }

    public static void deleteDirectory (String fileID)
    {
        String path = basePath + 23 + "/" + fileID;
        System.out.println("deleting dir"+ path);
        if(fileExists(path))
        {
            File directory = new File(path);

            File[] files = directory.listFiles();
            if(null!=files){
                for(int i=0; i<files.length; i++) {
                    files[i].delete();
                }
            }
        }
        else
            System.out.println("--SDirectorio não existe");

    }


    public static Boolean createDirectory(String fileID) throws IOException
    {
        File file = new File(getPath(fileID));

        if(!file.exists() || !file.isDirectory()){
            return file.mkdir();
        }
        return true;
    }


    /*
    public static void saveChunk(Chunk chunk) throws IOException {
        chunk.save();
    }

    public static Chunk loadChunk(String fileId,int chunkNo) throws FileNotFoundException, ClassNotFoundException
    {
        return Chunk.load(fileId,chunkNo);
    }
    */


    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.createDirectory("pasta1");
    }



}
