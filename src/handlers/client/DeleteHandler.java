package handlers.client;

import filesystem.BackupFile;
import messages.Delete;
import peer.Peer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by ei10117 on 03/04/2017.
 */
public class DeleteHandler extends  Thread {

    private String filename;

    public DeleteHandler(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        File file = Peer.filesystem.getOriginalFile(filename);

        if (file == null) {
            System.out.println("File not found.");
            System.out.println("File must be in " + Peer.filesystem.getPathToOriginals());
            return;
        }
        
        BackupFile bkFile = new BackupFile(file);


        Delete msg = new Delete("1.0", Peer.idPeer, bkFile.getFileId());
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, Peer.controlChannel.getAdress(), Peer.controlChannel.getPort());

        int trys = Peer.data.getBackupFile(bkFile.getFileId()).getReplicationDegree();
        //int trys = 5;

        try {
            for (int i = 0; i < trys; i++){
                Peer.controlChannel.sendMessage(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Peer.data.removeBackup(filename);
        Peer.filesystem.deleteOriginalFile(filename);

    }
}
