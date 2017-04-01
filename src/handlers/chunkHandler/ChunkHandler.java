package handlers.chunkHandler;

import filesystem.Chunk;
import messages.PutChunk;
import metadata.Metadata;
import peer.Peer;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by ruben on 01/04/2017.
 */
public class ChunkHandler extends Thread{

    private final Chunk chunk;
    private final int chunkNum;
    private final String filename;
    private long sleepTime;

    public ChunkHandler(Chunk chunk,int chunkNum,String filename) {
        this.chunk = chunk;
        this.chunkNum = chunkNum;
        this.filename = filename;
        this.sleepTime = 1000;

    }

    @Override
    public void run() {
        System.out.println("ChunkHandler");
        int repDegree = Peer.data.getFile(filename).getReplicationDegree();
        PutChunk msg = new PutChunk("1.0", Peer.idPeer,filename, chunkNum, chunk.getContent(),repDegree);
        Peer.data.getFile(filename).getChunks().put(chunkNum,new Metadata());
        DatagramPacket packet = new DatagramPacket(msg.getHeader().getBytes(), msg.getHeader().getBytes().length, Peer.backupChannel.getAdress(), Peer.backupChannel.getPort());

        int storedReceived = 0;
        boolean repeat;
        int count = 0;
        do {

            repeat = false;
            try {
                Peer.backupChannel.getMc_socket().send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                System.out.println("before sleep");
                this.sleep(sleepTime);
                System.out.println("after sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            storedReceived = Peer.data.getFile(filename).getChunks().get(chunkNum).getSavers().size();
            System.out.println("rep degree: " + repDegree + " - storesReceived: "+ storedReceived);
            if(repDegree > storedReceived)
            {
                System.out.println("inside condition");
                sleepTime = sleepTime * 2;
                count++;
                if(count == 5)
                    repeat = false;
                else
                    repeat = true;
            }
        }while(repeat);
        System.out.println("fora do while");
    }
}
