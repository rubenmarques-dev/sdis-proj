package handlers.channels;

import filesystem.BackupFile;
import filesystem.Chunk;
import messages.ParserHeader;
import peer.Peer;

import java.net.DatagramPacket;
/**
 * Created by ei10117 on 28/03/2017.
 */
public class MDRHandler extends Thread {

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

        int senderID = Integer.parseInt(fields[2]);
        if (senderID == Peer.idPeer) {
            return;
        }

        String type = fields[0];
        if (type.equals("CHUNK"))
        {
            System.out.println("received chunk");
            String version = fields[1];
            String fileID = fields[3];



            int chunkNum = Integer.parseInt(fields[4]);

            Peer.register.add(fileID,chunkNum);
            byte[] buf = parserHeader.getBody();
            Chunk chunk = new Chunk(chunkNum, fileID,  buf);

            BackupFile bkFile = Peer.restoredFiles.get(fileID);

            if (bkFile != null) {
                bkFile.addChunk(chunk);

                int chunkQty = Peer.data.getBackupFile(fileID).getChunks().size();

                if (chunkQty == bkFile.getChunks().size()) {
                    System.out.println("DEVE FAZER MERGE");
                    bkFile.sortChunks();
                    byte[] fileContent = Peer.fileHandler.merge(bkFile.getChunks());
                    Peer.filesystem.saveRestoredFile(fileContent, Peer.data.getFilepath(bkFile.getFileId()));
                    Peer.endRestore(bkFile.getFileId());
                }
            }

         /*   int num = Peer.data.getMyStores().get(fileID).getSize();
            if(chunkNum == Peer.data.getMyStores().g)*/


        }

    }
}
