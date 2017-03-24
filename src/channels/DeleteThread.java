package channels;

import peer.Peer;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by ei10117 on 24/03/2017.
 */
public class DeleteThread extends Thread{
    private Peer peer;


    public DeleteThread(Peer peer) {
        this.peer = peer;
    }

    @Override
    public void run() {
        System.out.println("deleteThread");
        while (true) {


            try {
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                peer.getBackupChannel().getMc_socket().receive(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }

}