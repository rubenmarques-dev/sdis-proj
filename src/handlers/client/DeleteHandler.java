package handlers.client;

import messages.Delete;
import peer.Peer;

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

        Delete msg = new Delete("1.0", Peer.idPeer, filename);
        DatagramPacket packet = new DatagramPacket(msg.getHeader().getBytes(), msg.getHeader().getBytes().length, Peer.backupChannel.getAdress(), Peer.backupChannel.getPort());

        Boolean again;

        int trys = Peer.data.getFile(filename).getChunks().size();

        for (int i = 0; i < trys ;i++){


            try {
                Peer.controlChannel.sendMessage(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Peer.data.removeFile(filename);
    }
}
