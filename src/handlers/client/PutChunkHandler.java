package handlers.client;

import filesystem.BackupFile;
import filesystem.Chunk;
import messages.PutChunk;
import metadata.Metadata;
import peer.Peer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;

/**
 * Created by ei10117 on 29/03/2017.
 */
public class PutChunkHandler extends Thread{
       private String filename;
       private int replicationDegree;

    public PutChunkHandler(String filename,int replicationDegree ) {
        this.filename = filename;

    }

    @Override
    public void run() {

        File file = Peer.filesystem.getOriginalFile(filename);
        Peer.data.addFile(replicationDegree,filename,0);
        System.out.println("after addFile");
        if (file == null) {
            System.out.println("File not found.");
            System.out.println("File must be in " + Peer.filesystem.getPathToOriginals());
        }

        BackupFile bkFile = new BackupFile(file);
        ArrayList<Chunk> chunks = Peer.fileHandler.split(bkFile);
        bkFile.setChunks(chunks);
        System.out.println(bkFile.getChunks().size());



        PutChunk msg = new PutChunk("1.0",Peer.idPeer,filename, 1, chunks.get(0).getContent(),2);
        Peer.data.getFile(filename).getChunks().put(1,new Metadata());
        DatagramPacket packet = new DatagramPacket(msg.getHeader().getBytes(),msg.getHeader().getBytes().length,Peer.backupChannel.getAdress(),Peer.backupChannel.getPort());
        try {
            Peer.backupChannel.getMc_socket().send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
