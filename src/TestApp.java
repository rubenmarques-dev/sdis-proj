import rmi.RemoteInterface;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by ei10117 on 16/03/2017.
 */
public class TestApp {

    public static final String patternOperation = "^backup$|^restore$|^delete$|^reclaim$|^state$";

    protected static String opnd_1 = "Is either the path name of the file to backup/restore/delete, " +
            "for the respective 3 subprotocols, or the amount of space to reclaim (in KByte). " +
            "In the latter case, the peer should execute the RECLAIM protocol, upon deletion of any chunk." +
            " The STATE operation takes no operands.";

   protected static String opnd_2 = "This operand is an integer that specifies the desired replication degree " +
            "and applies only to the backup protocol (or its enhancement)";
    protected static int peer_ap;
    protected static String subProtocol;
    protected static int repDegree;
    protected static String filePath;
    protected static int availableSpace;
    protected static String protocolResponse;
    protected static RemoteInterface remoteInterface;



    public static void main(String[] args) throws IOException {

        String remotePoint = null;

        if(args.length  < 1){
            System.out.println("<peer_ap> <sub_protocol> <opnd_1> <opnd_2>");
            return ;
        }else
        {
            //peer_ap
            try {
                peer_ap =Integer.parseInt(args[0]);
                System.out.println("<peer_ap>: " + peer_ap);
                Registry registry = LocateRegistry.getRegistry(peer_ap);
                remoteInterface = (RemoteInterface) registry.lookup("hello");
            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }

            //subProtocol
            subProtocol  = args[1];

            System.out.println("subProtocol: " + subProtocol);
            if(subProtocol.equals("BACKUP")) {
                filePath = args[2];
                repDegree = Integer.parseInt(args[3]);
                protocolResponse = remoteInterface.backup(filePath, repDegree);
            }
            else if(subProtocol.equals("RESTORE"))
                protocolResponse = remoteInterface.restore();
            else if(subProtocol.equals("DELETE")) {
                filePath = args[2];
                protocolResponse = remoteInterface.delete(filePath);
            }
            else if(subProtocol.equals("REMOVED"))
                protocolResponse = remoteInterface.reclaim();
            else if(subProtocol.equals("STATE"))
                protocolResponse = remoteInterface.state();

            System.out.println(protocolResponse);
        }



    }
}
