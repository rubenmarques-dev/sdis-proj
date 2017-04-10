package handlers.channels;

import filesystem.Chunk;
import messages.ChunkMsg;
import messages.ParserHeader;
import peer.Peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Random;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class MCHandler extends Thread {

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
        String type = fields[0];

        if (senderID == Peer.idPeer) {
            return;
        }

        if (type.equals("STORED"))
        {
            String version = fields[1];
            String fileID = fields[3];
            int chunkNum = Integer.parseInt(fields[4]);

            if(!Peer.data.backupExists(fileID))
            {}
            else if(!Peer.data.chunkExistsBackup(fileID,chunkNum))
            {}
            else
            {
                Peer.data.getBackupFile(fileID).getChunks().get(chunkNum).addSaver(senderID);
            }


        }
        else if(type.equals("GETCHUNK"))
        {
            String version = fields[1];
            String fileID = fields[3];
            int chunkNum = Integer.parseInt(fields[4]);

            Chunk chunk = Peer.filesystem.getChunk(fileID, chunkNum);

            if (chunk != null) {
                ChunkMsg msg = new ChunkMsg(version, Peer.idPeer, fileID, chunkNum);

                byte[] buf = msg.getBytes();
                byte[] c = new byte[buf.length + chunk.getContent().length];

                System.arraycopy(buf, 0, c, 0, buf.length);
                System.arraycopy(chunk.getContent(), 0, c, buf.length, chunk.getContent().length);

                try {

                    DatagramPacket packet = new DatagramPacket(c, c.length,Peer.restoreChannel.getAdress(),Peer.restoreChannel.getPort());

                    int sleepTime = (new Random()).nextInt(400);
                    try {
                        System.out.println("Sleeping: " + sleepTime);
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(!Peer.register.hasIt(fileID,chunkNum)) {
                        Peer.restoreChannel.getMc_socket().send(packet);
                        Peer.register.add(fileID,chunkNum);
                    }
                    else
                    {
                        if(Peer.data.getMyStores().get(fileID).getChunks().get(chunkNum).getSize() == chunk.getContent().length)
                            Peer.register.clean(fileID);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (type.equals("DELETE"))
        {

            String fileID = fields[3];
            Peer.filesystem.deleteBackupFile(fileID);
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
