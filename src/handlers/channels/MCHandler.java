package handlers.channels;

import filesystem.Chunk;
import messages.ChunkMsg;
import messages.ParserHeader;
import peer.Peer;

import java.io.IOException;
import java.net.DatagramPacket;

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

            Peer.register.addRecord(type,chunkNum,senderID,fileID);
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
                    Peer.restoreChannel.getMc_socket().send(packet);
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
