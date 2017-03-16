package channels;

import java.io.IOException;
import java.net.*;

/**
 * Created by ei10117 on 16/03/2017.
 */
public class MC extends Thread{
    protected MulticastSocket mc_socket;
    protected InetAddress mc_addr;
    protected int mc_port;
    protected int MAX_SIZE= 65536;

    public MC(String addr, int port/*, String type*/) {


        try {
            mc_addr = InetAddress.getByName(addr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        mc_port = port;
        try {
            mc_socket = new MulticastSocket(mc_port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mc_socket.joinGroup(mc_addr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mc_socket.setTimeToLive(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mc_socket.setLoopbackMode(false);
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        //System.out.println("channel :" + type + "    on addr: " + mc_addr.getHostName() + "  port:" + mc_port);
    }


    public MulticastSocket getMc_socket(){
        return mc_socket;
    }

    public int getMc_port(){
        return mc_port;
    }

    public InetAddress getMc_addr(){
        return mc_addr;
    }

}
