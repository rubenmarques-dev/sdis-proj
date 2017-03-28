package handlers;

import messages.ParserHeader;

import java.net.DatagramPacket;

/**
 * Created by ei10117 on 28/03/2017.
 */
public class ControlHandler extends Thread{

    private DatagramPacket packet;

    public ControlHandler(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {

        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("received(ch): " + received);
        String fields[] = ParserHeader.parse(received);
        String type = fields[0];

        if(type.equals("STORED"))
        {
            System.out.println("type.equals(STORED)");
        }
        else if(type.equals("GETCHUNK"))
        {
            System.out.println("type.equals(GETCHUNK)");
        }
        else if(type.equals("DELETE"))
        {
            System.out.println("type.equals(DELETE)");
        }
        else if(type.equals("REMOVED"))
        {
            System.out.println("type.equals(REMOVED)");
        }
        else
        {
            System.out.println("controlHandler .type-> " + type);
        }

    }
}
