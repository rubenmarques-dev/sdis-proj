package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ei10117 on 24/03/2017.
 */
 public interface RemoteInterface extends Remote{

    String backup(String filename,int replicationDegree) throws RemoteException;
    String restore(String filename) throws RemoteException;
    String delete(String filename) throws RemoteException;
    String reclaim() throws RemoteException;
    String state() throws RemoteException;


}
