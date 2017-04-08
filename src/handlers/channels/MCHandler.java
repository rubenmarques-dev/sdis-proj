package handlers.channels;

import messages.ParserHeader;
import peer.Peer;

import javax.swing.text.html.parser.Parser;
import java.net.DatagramPacket;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class MCHandler extends Thread{

    private DatagramPacket packet;

    public MCHandler(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {

        ParserHeader parserHeader = new ParserHeader();
        byte[] buffer = packet.getData();
        parserHeader.parse(buffer);
        String[] fields = parserHeader.getFields();

        int senderID = Integer.parseInt(fields[2]);
        if(senderID == Peer.idPeer)
            return ;

      //  System.out.println("received(ch): " + received);

        String type = fields[0];
        if(type.equals("STORED"))
        {
         //   System.out.println("type.equals(STORED)");
            String version = fields[1];
            String fileID = fields[3];
            int chunkNum = Integer.parseInt(fields[4]);

            if(!Peer.data.fileExist(fileID))
            {}
            else if(!Peer.data.chunkExists(fileID,chunkNum))
            {}
            else
            {
                Peer.data.getFile(fileID).getChunks().get(chunkNum).addSaver(senderID);
            }

            Peer.register.addRecord(type,chunkNum,senderID,fileID);

        }
        else if(type.equals("GETCHUNK"))
        {
            System.out.println("type.equals(GETCHUNK)");
        }
        else if(type.equals("DELETE"))
        {
            //TODO apaga da DBS dando um fileID
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
