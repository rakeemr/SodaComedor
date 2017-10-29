package crmoviles.ac.tec.AppSodaTEC;

import java.util.ArrayList;

/**
 * Created by Josue on 22/10/2016.
 */
public class dataProviderAD {

    public String name;
    public String image;
    public ArrayList<String> datos = new ArrayList<String>();

    public dataProviderAD(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }

}
