/**
 * @author ojoaofernandes
 * @created March 23th, 2017
 */

package server;

import filesystem.BackupFile;
import filesystem.BackupFileHandler;
import filesystem.Chunk;
import filesystem.FileSystemManager;
import java.io.File;
import java.util.ArrayList;

public class Peer {
    
    private int id;
    private FileSystemManager filesystem;
    private BackupFileHandler fileHandler;
    
    public Peer(int id) {
        this.id = id;
        this.initialize();
    }
    
    private boolean initialize() {
        this.filesystem = new FileSystemManager(this.id);  
        this.fileHandler = new BackupFileHandler();
        return true;
    }
    
    public void backup(String filename) {
        File file = this.filesystem.getOriginalFile(filename);
        
        if (file == null) {
            System.out.println("File not found.");
            System.out.println("File must be in " + this.filesystem.getPathToOriginals());
            return;
        }
        
        BackupFile bkFile = new BackupFile(file);
        ArrayList<Chunk> chunks = this.fileHandler.split(bkFile);
        bkFile.setChunks(chunks);
        System.out.println(bkFile.getChunks().size());
    }
    
    public static void main(String[] args) {
        
        if (args.length != 2) {
            System.out.println("Usage: java server.Peer <peerID> <filename>");
            System.out.println("\t- peerID: must be an unique integer number");
            System.out.println("\t- filename: name of a file to be splitted");
            return;
        }
        
        int id = Integer.parseInt(args[0]);
        Peer peer = new Peer(id);
        peer.backup(args[1]);
    }
    
}
