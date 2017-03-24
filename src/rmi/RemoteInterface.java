package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ei10117 on 24/03/2017.
 */
 public interface RemoteInterface extends Remote{

    String backup() throws RemoteException;
    String restore() throws RemoteException;
    String delete() throws RemoteException;
    String reclaim() throws RemoteException;
    String state() throws RemoteException;

}
