package channels;

import handlers.server.BackupHandler;
import peer.Peer;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by ei10117 on 24/03/2017.
 */
public class ThreadMDB extends Thread{
    private Peer peer;


    public ThreadMDB(Peer peer) {
        this.peer = peer;
    }

    @Override
    public void run() {
        //System.out.println("Backup Thread");
        while (true) {


            try {
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                peer.getBackupChannel().getMc_socket().receive(packet);
                if(packet.getData() != null)
                {
                    (new BackupHandler(packet)).run();
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