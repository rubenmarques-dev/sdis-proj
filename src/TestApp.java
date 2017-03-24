import channels.MC;
import messages.StoredMsg;
import rmi.RemoteInterface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by ei10117 on 16/03/2017.
 */
public class TestApp {

    public static final String patternOperation = "^backup$|^restore$|^delete$|^reclaim$|^state$";
    static String opnd_1 = "Is either the path name of the file to backup/restore/delete, " +
            "for the respective 3 subprotocols, or the amount of space to reclaim (in KByte). " +
            "In the latter case, the peer should execute the RECLAIM protocol, upon deletion of any chunk." +
            " The STATE operation takes no operands.";
    static String opnd_2 = "This operand is an integer that specifies the desired replication degree " +
            "and applies only to the backup protocol (or its enhancement)";

    protected static String operation;
    protected static int repDegree;
    protected static String filePath;
    protected static int availableSpace;
    protected static String protocolResponse;
    static RemoteInterface remoteInterface;



    public static void main(String[] args) throws IOException {

        String remotePoint = null;
        int peer_ap = -1;
        if(args.length  < 1){
            System.out.println("<peer_ap> <sub_protocol> <opnd_1> <opnd_2>");
            return ;
        }else
        {
            try {
                peer_ap =Integer.parseInt(args[0]);
                System.out.println("<peer_ap>" + peer_ap);
                Registry registry = LocateRegistry.getRegistry(peer_ap);
                remoteInterface = (RemoteInterface) registry.lookup("hello");
            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }

            protocolResponse = remoteInterface.backup();
            System.out.println(protocolResponse);
        }



    }
}
