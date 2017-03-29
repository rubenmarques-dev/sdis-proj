package channels;

import handlers.server.RestoreHandler;
import peer.Peer;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by ei10117 on 24/03/2017.
 */
public class ThreadMDR extends Thread{
    private Peer peer;


    public ThreadMDR(Peer peer) {
        this.peer = peer;
    }

    @Override
    public void run() {
        // System.out.println("restore Thread");
        while (true) {

            try {
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                peer.getBackupChannel().getMc_socket().receive(packet);
                if (packet.getData() != null) {
                    System.out.println("-Chamada BackupHanler()");
                    (new RestoreHandler(packet)).run();
                } else {
                    System.out.println("MENSAGEM MAL RECEBIDA");
                    System.out.println("MENSAGEM: " + packet.getData().toString());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
