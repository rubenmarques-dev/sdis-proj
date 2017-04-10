package metadata;

import com.sun.org.apache.regexp.internal.RE;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * Created by ei10117 on 04/04/2017.
 */
public class Register {

    private HashMap<String, Vector<Integer>> received;

    public Register() {
        this.received = new HashMap<>();

    }
   public void add(String file,int chunkNum)
   {
       createRegistry(file);
       received.get(file).add(chunkNum);
   }

   public void createRegistry(String file)
   {
       if(!received.containsKey(file))
           received.put(file,new Vector<>());
   }

   public Boolean hasIt(String file,int chunkNum)
   {

       if(received.containsKey(file))
       {
           if(received.get(file).contains(chunkNum))
           {
               return true;
           }
       }
       return false;
   }


    public void clean(String fileID) {
        received.remove(fileID);

    }
}
