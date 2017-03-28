package channels;

import messages.ParserHeader;
import peer.Peer;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by ei10117 on 16/03/2017.
 */
public class ThreadMC extends Thread{
    private Peer peer;


    public ThreadMC(Peer peer) {
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
                String fields[] = ParserHeader.parse(packet.getData().toString());
                String type = fields[0];

                if(type.equals("STORED")){


                }
                else if(type.equals("GETCHUNK")) {

                }
                else if(type.equals("DELETE")) {

                }
                else if(type.equals("REMOVED")) {

                }
                else
                {
                    System.out.println("MENSAGEM MAL RECEBIDA");
                    System.out.println("MENSAGEM: " + packet.getData().toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }

}
