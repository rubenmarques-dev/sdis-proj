package handlers.chunkHandler;

import messages.GetChunk;
import peer.Peer;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by ei10117 on 03/04/2017.
 */
public class GetchunkHandler extends Thread {

    private final int chunkNum;
    private final String filename;

    public GetchunkHandler(int chunkNum, String filename) {
        this.chunkNum = chunkNum;
        this.filename = filename;
    }

    @Override
    public void run() {
        System.out.println("GetchunkHandler: chunk" + chunkNum);

        GetChunk msg = new GetChunk("1.0", Peer.idPeer,filename,chunkNum);
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, Peer.controlChannel.getAdress(), Peer.controlChannel.getPort());

        try {
            Peer.controlChannel.sendMessage(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
