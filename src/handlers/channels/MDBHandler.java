package handlers.channels;

import filesystem.Chunk;
import messages.ParserHeader;
import messages.Stored;
import peer.Peer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class MDBHandler extends Thread{

    private DatagramPacket packet;
    private byte[] buffer;

    public MDBHandler(DatagramPacket packet) {
        this.packet = packet;

    }

    @Override
    public void run() {

        ParserHeader parserHeader = new ParserHeader();
        byte[] buffer = packet.getData();

        parserHeader.parseBody(buffer, packet.getLength());
        String[] fields = parserHeader.getFields();

       // ParserHeader.parse(packet.getData());
        //String[] fields = null;
        int senderID = Integer.parseInt(fields[2]);
        if(senderID == Peer.idPeer)
            return ;

      //  System.out.println("received(ch): " + received);

        String type = fields[0];
        if (type.equals("PUTCHUNK")) {

            String version = fields[1];

            String fileID = fields[3];
            int chunkNum = Integer.parseInt(fields[4]);


            byte[] buf = parserHeader.getBody();
            System.out.println(buf);

            int port = packet.getPort();
            InetAddress adress = packet.getAddress();

            Chunk chunk = new Chunk(chunkNum, fileID,  buf);

            //savechunk
            Peer.filesystem.saveChunk(chunk);

            System.out.println("before send stored");
            int sleepTime = (new Random()).nextInt(400);
            try {
                System.out.println("Sleeping: " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Stored stored = new Stored(version,Peer.idPeer,fileID,chunkNum);
            try {

                //send stored message, after saved putchunk
                Peer.controlChannel.getMc_socket().send(new DatagramPacket(stored.getBytes(),stored.getBytes().length,Peer.controlChannel.getAdress(),Peer.controlChannel.getPort()));

            } catch (IOException e) {
                e.printStackTrace();
            }
           // Peer.register.addRecord(type,chunkNum,senderID,fileID);
        }
        else
        {
            System.out.println("backupHandler: Cabeçalho errado: " + type);
        }
    }
}
