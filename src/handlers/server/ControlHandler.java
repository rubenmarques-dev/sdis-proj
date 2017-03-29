package handlers.server;

import messages.ParserHeader;
import peer.Peer;

import java.net.DatagramPacket;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class ControlHandler extends Thread{

    private DatagramPacket packet;

    public ControlHandler(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {

        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("received(ch): " + received);
        String fields[] = ParserHeader.parse(received);
        String type = fields[0];

        if(type.equals("STORED"))
        {
            String version = fields[1];
            int senderID = Integer.parseInt(fields[2]);
            String fileID = fields[3];
            int chunkNum = Integer.parseInt(fields[4]);

            System.out.println("controlHandler: fileid ->  " +  fileID);
            System.out.println("controlHandler: chunkNum -> " +  chunkNum);

            if(!Peer.data.fileExist(fileID))
                System.out.println("ficheiro não foi criado");
            else if(!Peer.data.chunkExists(fileID,chunkNum))
                System.out.println("Ficheiro criado, mas não foi criado o chunk");
            else
            {
                Peer.data.getFile(fileID).getChunks().get(chunkNum).addSaver(senderID);
            }

            System.out.println("type.equals(STORED)");


        }
        else if(type.equals("GETCHUNK"))
        {
            System.out.println("type.equals(GETCHUNK)");
        }
        else if(type.equals("DELETE"))
        {
            System.out.println("type.equals(DELETE)");
        }
        else if(type.equals("REMOVED"))
        {
            System.out.println("type.equals(REMOVED)");
        }
        else
        {
            System.out.println("controlHandler .type-> " + type);
        }

    }
}
