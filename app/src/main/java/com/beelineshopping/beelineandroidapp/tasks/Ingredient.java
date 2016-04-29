package com.beelineshopping.beelineandroidapp.tasks;

/**
 * Created by ggenz on 2/18/2016.
 */
public class Ingredient {

    private String Name;
    private String Aisle;
    private String Section;

    public Ingredient(String IngName, String IngAisle, String IngSection)
    {
        Name = IngName;
        Aisle = IngAisle;
        Section = IngSection;
    }

    public String getName()
    {
        return Name;
    }

    public String getAisle()
    {
        return Aisle;
    }

    public String getSection()
    {
        return Section;
    }
}
