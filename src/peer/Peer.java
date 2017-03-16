package peer;

import channels.MCThread;
import channels.MC;

import java.io.IOException;

/**
 * Created by ei10117 on 16/03/2017.
 */
public class Peer {

    private MC controlChannel;

    public Peer() {
    }

    public MC getControlChannel() {
        return controlChannel;
    }

    public void setControlChannel(MC controlChannel) {
        this.controlChannel = controlChannel;
    }

    public static void main(String[] args) throws IOException {

        MC mc = new MC("230.0.0.69", 4446);
        Peer peer = new Peer();
        peer.setControlChannel(mc);
        MCThread controlChannel = new MCThread(peer);
        controlChannel.start();



        /*
        MulticastSocket socket = new MulticastSocket(4446);
        InetAddress address = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(address);

        DatagramPacket packet;

        // get a few quotes
        for (int i = 0; i < 5; i++) {

            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Quote of the Moment: " + received);
        }

        socket.leaveGroup(address);
        socket.close();
    */
    }

}
