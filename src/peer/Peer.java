package peer;

import channels.ControlThread;
import channels.DeleteThread;
import channels.MC;
import channels.RestoreThread;
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
    private MC backupChannel;
    private MC restoreChannel;
    private ControlThread controlThread;
    private DeleteThread deleteThread;
    private RestoreThread restoreThread;
    private int idPeer;

    public Peer(int idPeer) {
        this.idPeer = idPeer;
        this.controlThread = new ControlThread(this);
        this.restoreThread = new RestoreThread(this);
        this.deleteThread = new DeleteThread(this);
    }

    @Override
    public String backup() throws RemoteException {
        return "backup: "+idPeer;

    }

    @Override
    public String restore() throws RemoteException {
        return "restore: "+idPeer;
    }

    @Override
    public String delete() throws RemoteException {
        return "delete: "+idPeer;
    }

    @Override
    public String reclaim() throws RemoteException {
        return "reclaim: "+idPeer;
    }

    @Override
    public String state() throws RemoteException {
        return "state: "+idPeer;
    }

    //get-set controlChannel
    public MC getControlChannel() {
        return controlChannel;
    }
    public void setControlChannel(MC controlChannel) {
        this.controlChannel = controlChannel;
    }

    //get-set backupChannel
    public MC getBackupChannel() {return backupChannel;}
    public void setBackupChannel(MC backupChannel) {this.backupChannel = backupChannel;}

    //get-set crestoreChannel
    public MC getRestoreChannel() {return restoreChannel;}
    public void setRestoreChannel(MC restoreChannel) {this.restoreChannel = restoreChannel;}

    //get-set idPeer
    public int getIdPeer() {return idPeer;}
    public void setIdPeer(int idPeer) {this.idPeer = idPeer;}

    public void startChannels()
    {
        this.controlThread.start();
        this.deleteThread.start();
        this.restoreThread.start();

    }
    public static void main(String[] args) throws IOException {

        String version = null;
        int idPeer = -1;
        String remote = null;
        String control[], backup[], restore[];
        Peer peer;
        if(args.length < 2)//é suposter ter 6
        {
            System.out.println("Parametros mal inseridos");
            return ;
        }
        else
        {
            version = args[0];
            idPeer = Integer.parseInt(args[1]);
            remote = args[2];
            System.out.println("version: " + version + "\nid: "+ idPeer + "\nremote: " + remote);

            //creating peer
            peer = new Peer(idPeer);

            //creating control channel
            control = args[3].split(":");
            MC controlChannel = new MC(control[0],Integer.parseInt(control[1]));

            //creating backup channel
            backup = args[4].split(":");
            MC backupChannel = new MC(backup[0],Integer.parseInt(backup[1]));

            //creating restore channel
            restore = args[5].split(":");
            MC restoreChannel = new MC(restore[0],Integer.parseInt(restore[1]));


            peer.setControlChannel(controlChannel);
            peer.setBackupChannel(backupChannel);
            peer.setRestoreChannel(restoreChannel);
            peer.startChannels();

        }







        try {
            RemoteInterface remoteInterface = (RemoteInterface ) UnicastRemoteObject.exportObject(peer, 0);
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(idPeer);
            //Registry registry = LocateRegistry.getRegistry(peerId); //deprecated
            registry.rebind(remote, remoteInterface);
            System.out.println("Peer ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }



    }



}
