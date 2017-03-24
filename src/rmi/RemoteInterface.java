package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ei10117 on 24/03/2017.
 */
 public interface RemoteInterface extends Remote{

    String backup() throws RemoteException;
}
