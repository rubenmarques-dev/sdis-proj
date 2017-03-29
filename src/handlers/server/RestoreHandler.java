package handlers.server;

import messages.ChunkMsg;
import messages.ParserHeader;
import peer.Peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class RestoreHandler extends Thread{

    private DatagramPacket packet;

    public RestoreHandler(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {

        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("received: " + received);
        String fields[] = ParserHeader.parse(received);
        String type = fields[0];

        if(type.equals("Metadata"))
        {
            System.out.println("type.equals(Metadata)");
            String version = fields[1];
            int senderID = Integer.parseInt(fields[2]);
            String fileID = fields[3];
            int chunkNum = Integer.parseInt(fields[4]);
            String body = fields[6];
            int port = packet.getPort();
            InetAddress adress = packet.getAddress();
            //save chunk
            System.out.println("before send stored");
            ChunkMsg stored = new ChunkMsg(version,senderID,fileID,chunkNum,"ola".getBytes());
            try {
                Peer.restoreChannel.getMc_socket().send(new DatagramPacket(stored.getBytes(),stored.getBytes().length,Peer.controlChannel.getAdress(),Peer.controlChannel.getPort()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
