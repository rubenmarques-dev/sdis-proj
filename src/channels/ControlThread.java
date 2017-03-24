package channels;

import peer.Peer;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by ei10117 on 16/03/2017.
 */
public class ControlThread extends Thread{
    private Peer peer;


    public ControlThread(Peer peer) {
        this.peer = peer;
    }

    @Override
    public void run() {
        System.out.println("controlThread");
        while (true) {


            try {
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                peer.getControlChannel().getMc_socket().receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Message: " + received);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }

}
