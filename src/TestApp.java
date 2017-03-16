import channels.MC;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by ei10117 on 16/03/2017.
 */
public class TestApp {

    private MC mc;

    public TestApp(MC mc) {
        this.mc = mc;
    }

    public MC getMc() {
        return mc;
    }

    public void setMc(MC mc) {
        this.mc = mc;
    }

    public static void main(String[] args) throws IOException {
        MC mc = new MC("230.0.0.69", 4446);
        TestApp testApp = new TestApp(mc);
        byte[] buf = new byte[256];

        String str = "mensagem random";
        buf = str.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("230.0.0.69"),4446);
        testApp.getMc().getMc_socket().send(packet);
        testApp.getMc().getMc_socket().close();

    }
}
