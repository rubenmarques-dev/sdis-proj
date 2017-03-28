package handlers;

import messages.ParserHeader;
import messages.StoredMsg;
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

        String fields[] = ParserHeader.parse(packet.getData().toString());
        String type = fields[0];

        if (type.equals("PUTCHUNK")) {
            String version = fields[1];
            int senderID = Integer.parseInt(fields[2]);
            String fileID = fields[3];
            int chunkNum = Integer.parseInt(fields[4]);
            String body = fields[6];
            int port = packet.getPort();
            InetAddress adress = packet.getAddress();
            //save chunk
            System.out.println("before send stored");
            StoredMsg stored = new StoredMsg(version,senderID,fileID,chunkNum);
            try {
                Peer.controlChannel.getMc_socket().send(new DatagramPacket(stored.getBytes(),stored.getBytes().length,adress,port));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            System.out.println("Cabeçalho errado: " + type);
        }
    }
}
