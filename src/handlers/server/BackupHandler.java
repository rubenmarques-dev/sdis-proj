package handlers.server;

import filesystem.Chunk;
import messages.ParserHeader;
import messages.Stored;
import peer.Peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class BackupHandler extends Thread{

    private DatagramPacket packet;

    public BackupHandler(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {

        String received = new String(packet.getData(), 0, packet.getLength());
        String fields[] = ParserHeader.parse(received);
        String type = fields[0];

        //System.out.println("received: " + received);

        if (type.equals("PUTCHUNK")) {

            String version = fields[1];
            int senderID = Integer.parseInt(fields[2]);
            String fileID = fields[3];
            int chunkNum = Integer.parseInt(fields[4]);
            String body = fields[6];
            int port = packet.getPort();
            InetAddress adress = packet.getAddress();

            Chunk chunk = new Chunk(chunkNum, fileID,  body.getBytes());

          //  System.out.println("Metadata " + chunk.getNumber() + " : " + fileID +". Content : " + chunk.getContent().toString());

            //savechunk

            System.out.println("before send stored");

            Stored stored = new Stored(version,Peer.idPeer,fileID,chunkNum);
            try {

                //send stored message, after saved putchunk
                Peer.controlChannel.getMc_socket().send(new DatagramPacket(stored.getBytes(),stored.getBytes().length,Peer.controlChannel.getAdress(),Peer.controlChannel.getPort()));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            System.out.println("backupHandler: Cabeçalho errado: " + type);
        }
    }
}
