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

    private HashMap<String, Vector<Record>> received;

    public Register() {
        this.received = new HashMap<String, Vector<Record>>();
        this.config();
    }

    public void config(){

        this.received.put("PUTCHUNK", new Vector<Record>());
        this.received.put("STORED", new Vector<Record>());
        this.received.put("GETCHUNK",new Vector<Record>());
        this.received.put("CHUNK",new Vector<Record>());
        this.received.put("REMOVED",new Vector<Record>());
        this.received.put("REMOVE",new Vector<Record>());
    }



    public void addRecord(String type,int chunkNum,int sender,String filename){
        this.received.get(type).add(new Record(sender,chunkNum,filename));
    }

    public boolean hasCopy(String type,int chunkNum,int sender,String filename) {
        Vector<Record> records = this.received.get(type);

        for(int i = 0; i < records.size() ; i++){
            if(records.get(i).isTheSame(sender,chunkNum,filename))
                return true;
        }

        return false;
    }
    public void print()
    {
        System.out.println("-Printing Record");
        Set set = received.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            HashMap.Entry mentry = (HashMap.Entry)iterator.next();
            System.out.println(mentry.getKey());
            printType((Vector<Record>) mentry.getValue());
        }
    }

    public void printType(Vector<Record> records)
    {
        for (int i = 0; i < records.size(); i++) {
            records.get(i).print();
        }
    }




}
