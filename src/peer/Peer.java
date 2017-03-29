package peer;

import channels.ThreadMC;
import channels.ThreadMDB;
import channels.MulticastChannel;
import channels.ThreadMDR;
import filesystem.BackupFile;
import filesystem.BackupFileHandler;
import filesystem.Chunk;
import filesystem.FileSystemManager;
import handlers.client.PutChunkHandler;
import messages.Delete;
import messages.GetChunk;
import messages.PutChunk;
import messages.Removed;
import metadata.Data;
import rmi.RemoteInterface;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by ei10117 on 16/03/2017.
 */
public class Peer implements RemoteInterface{


    public static MulticastChannel controlChannel;
    public static MulticastChannel backupChannel;
    public static MulticastChannel restoreChannel;
    public static ThreadMC controlThread;
    public static ThreadMDB deleteThread;
    public static ThreadMDR restoreThread;
    public static int idPeer;
    public  static FileSystemManager filesystem;
    public static BackupFileHandler fileHandler;
    public static Data data;

    public Peer(int idPeer) {
        this.idPeer = idPeer;
        this.controlThread = new ThreadMC(this);
        this.restoreThread = new ThreadMDR(this);
        this.deleteThread = new ThreadMDB(this);
        this.data = new Data();
        initialize();
    }

    private boolean initialize() {
        this.filesystem = new FileSystemManager(this.idPeer);
        this.fileHandler = new BackupFileHandler();
        return true;
    }

    @Override
    public String backup(String filename,int replicationDegree) throws RemoteException {
        //parse ficheiros

        (new PutChunkHandler(filename,replicationDegree)).run();

        return "success";
    }

    @Override
    public String restore() throws RemoteException {
        GetChunk msg = new GetChunk("1.0",idPeer,"teste.txt", 13);

        DatagramPacket packet = new DatagramPacket(msg.getHeader().getBytes(),msg.getHeader().getBytes().length,controlChannel.getAdress(),controlChannel.getPort());
        try {
            controlChannel.getMc_socket().send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @Override
    public String delete() throws RemoteException {
        Delete msg = new Delete("1.0",idPeer,"teste.txt");

        DatagramPacket packet = new DatagramPacket(msg.getHeader().getBytes(),msg.getHeader().getBytes().length,controlChannel.getAdress(),controlChannel.getPort());
        try {
            controlChannel.getMc_socket().send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @Override
    public String reclaim() throws RemoteException {
        Removed msg = new Removed("1.0",idPeer,"teste.txt", 13);

        DatagramPacket packet = new DatagramPacket(msg.getHeader().getBytes(),msg.getHeader().getBytes().length,controlChannel.getAdress(),controlChannel.getPort());
        try {
            controlChannel.getMc_socket().send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "reclaim: "+idPeer;
    }

    @Override
    public String state() throws RemoteException {

        data.print();
        return "state: "+idPeer;
    }

    //get-set controlChannel
    public MulticastChannel getControlChannel() {
        return controlChannel;
    }
    public void setControlChannel(MulticastChannel controlChannel) {
        this.controlChannel = controlChannel;
    }

    //get-set backupChannel
    public MulticastChannel getBackupChannel() {return backupChannel;}
    public void setBackupChannel(MulticastChannel backupChannel) {this.backupChannel = backupChannel;}

    //get-set crestoreChannel
    public MulticastChannel getRestoreChannel() {return restoreChannel;}
    public void setRestoreChannel(MulticastChannel restoreChannel) {this.restoreChannel = restoreChannel;}

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
            //System.out.println("version: " + version + "\nid: "+ idPeer + "\nremote: " + remote);

            //creating peer
            peer = new Peer(idPeer);


            //creating control channel
            control = args[3].split(":");
            MulticastChannel controlChannel = new MulticastChannel(control[0],Integer.parseInt(control[1]));

            //creating backup channel
            backup = args[4].split(":");
            System.out.println(backup[0]);
            MulticastChannel backupChannel = new MulticastChannel(backup[0],Integer.parseInt(backup[1]));

            //creating restore channel
            restore = args[5].split(":");
            MulticastChannel restoreChannel = new MulticastChannel(restore[0],Integer.parseInt(restore[1]));


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
            System.out.println("Peer remotely ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }


    }



}
