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

        String fields[] = ParserHeader.parse(packet.getData().toString());
        String type = fields[0];

        if(type.equals("STORED"))
        {
            System.out.println("stored received");
        }

    }
}
