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
public class PutchunkHandler extends Thread{

    private final Chunk chunk;
    private final int chunkNum;
    private final String filename;
    private long sleepTime;

    public PutchunkHandler(Chunk chunk, int chunkNum, String filename) {
        this.chunk = chunk;
        this.chunkNum = chunkNum;
        this.filename = filename;
        this.sleepTime = 1000;

    }

    @Override
    public void run() {
        System.out.println("PutchunkHandler: chunk" + chunkNum);

        int repDegree = Peer.data.getFile(filename).getReplicationDegree();
        PutChunk msg = new PutChunk("1.0", Peer.idPeer,filename, chunkNum, chunk.getContent(),repDegree);
        Peer.data.getFile(filename).getChunks().put(chunkNum, new Metadata());

        byte[] buf = msg.getBytes();

        System.out.println(buf.length);
        System.out.println(chunk.getContent().length);

        byte[] c = new byte[buf.length + chunk.getContent().length];

        System.arraycopy(buf, 0, c, 0, buf.length);
        System.arraycopy(chunk.getContent(), 0, c, buf.length, chunk.getContent().length);
        System.out.println("c: " + c.length);

        DatagramPacket packet = new DatagramPacket(c, c.length, Peer.backupChannel.getAdress(), Peer.backupChannel.getPort());

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
                this.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            storedReceived = Peer.data.getFile(filename).getChunks().get(chunkNum).getSavers().size();

            if(repDegree > storedReceived)
            {
               // System.out.println("rep degree: " + repDegree + " - storesReceived: "+ storedReceived);
                sleepTime = sleepTime * 2;
                count++;
                if(count == 5)
                    repeat = false;
                else
                    repeat = true;
            }
        } while(repeat);
        System.out.println("chunkHandler: chunk" + chunkNum + " finish.");
    }
}
