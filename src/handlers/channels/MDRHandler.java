package handlers.channels;

import messages.ParserHeader;
import peer.Peer;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class MDRHandler extends Thread{

    private DatagramPacket packet;

    public MDRHandler(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {


        ParserHeader parserHeader = new ParserHeader();
        byte[] buffer = packet.getData();
        parserHeader.parseBody(buffer,packet.getLength());
        String[] fields = parserHeader.getFields();

       // String fields[] = null;
        int senderID = Integer.parseInt(fields[2]);
        if(senderID == Peer.idPeer)
            return;

      //  System.out.println("received: " + received);

        String type = fields[0];
        if(type.equals("CHUNK"))
        {

            String version = fields[1];
            String fileID = fields[3];
            int chunkNum = Integer.parseInt(fields[4]);
            String body = fields[6];
            int port = packet.getPort();
            InetAddress adress = packet.getAddress();

        }

    }
}
