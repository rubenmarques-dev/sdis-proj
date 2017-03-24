package peer;

import channels.MCThread;
import channels.MC;
import rmi.RemoteInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by ei10117 on 16/03/2017.
 */
public class Peer implements RemoteInterface{

    private MC controlChannel;
    private int idPeer;

    public Peer(int idPeer) {
        this.idPeer = idPeer;
    }

    @Override
    public String backup() throws RemoteException {
        return "backup"+idPeer;

    }

    public MC getControlChannel() {
        return controlChannel;
    }

    public void setControlChannel(MC controlChannel) {
        this.controlChannel = controlChannel;
    }

    public static void main(String[] args) throws IOException {

        String version = null;
        int idPeer = -1;
        String remote = null;

        if(args.length > 2)//é suposter ter 6
        {
            version = args[0];
            idPeer = Integer.parseInt(args[1]);
            remote = args[2];
            System.out.println("version: " + version + "\nid: "+ idPeer + "\nremote: " + remote);
        }
        else
        {
            System.out.println("Parametros mal inseridos");
            return ;
        }

        MC mc = new MC("230.0.0.69", 4446);
        Peer peer = new Peer(idPeer);
        peer.setControlChannel(mc);
        /*MCThread controlChannel = new MCThread(peer);
        controlChannel.start();*/

        try {
            RemoteInterface remoteInterface = (RemoteInterface ) UnicastRemoteObject.exportObject(peer, 0);
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(idPeer);
            //Registry registry = LocateRegistry.getRegistry(peerId); //deprecated
            registry.rebind(remote, remoteInterface);
            System.err.println("Peer ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }



    }



}
