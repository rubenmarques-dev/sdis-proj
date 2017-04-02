package metadata;

import java.util.ArrayList;

/**
 * Created by ei10117 on 29/03/2017.
 */
public class Metadata {
    private ArrayList<Integer> savers;
    private int size;

    public Metadata() {
        this.size = 0;
        savers = new ArrayList<Integer>();

    }

    public ArrayList<Integer> getSavers() {
        return savers;
    }

    public void setSavers(ArrayList<Integer> savers) {
        this.savers = savers;
    }

    public void addSaver(int peerID)
    {

        if( !savers.contains(peerID))
                this.savers.add(peerID);
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void print()
    {
        System.out.print("savers: ");
        for(int i=0; i < savers.size(); i++)
        {
            System.out.print( savers.get(i));
            if(i != savers.size()-1)
                System.out.print(", ");
        }
        System.out.println();
        System.out.println("Size: " + size );
    }
}
