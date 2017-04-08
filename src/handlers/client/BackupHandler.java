package handlers.client;

import filesystem.BackupFile;
import filesystem.Chunk;
import handlers.chunkHandler.PutchunkHandler;
import peer.Peer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ei10117 on 29/03/2017.
 */
public class BackupHandler extends Thread{
       private String filename;
       private int replicationDegree;

    public BackupHandler(String filename, int replicationDegree ) {
        this.filename = filename;
        this.replicationDegree = replicationDegree;

    }

    @Override
    public void run() {

        File file = Peer.filesystem.getOriginalFile(filename);

        System.out.println("after addFile");

        if (file == null) {
            System.out.println("File not found.");
            System.out.println("File must be in " + Peer.filesystem.getPathToOriginals());
        }

        BackupFile bkFile = new BackupFile(file);

        Peer.data.addFile(replicationDegree,bkFile.getFileId(),0);
        ArrayList<Chunk> chunks = Peer.fileHandler.split(bkFile);
        bkFile.setChunks(chunks);

        System.out.println("tamanho do chunk: " +chunks.get(0).getContent().length);
        System.out.println();

        for(int i= 0; i< chunks.size(); i++)
            (new PutchunkHandler(chunks.get(i),i,bkFile.getFileId())).run();

    }
}
