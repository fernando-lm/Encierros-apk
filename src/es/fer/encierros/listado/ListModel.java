package es.fer.encierros.listado;

public class ListModel {
    
    private  String Location="";
    private  String Description="";
    private  String Date="";
    private  String Id="";
     
    /*********** Set Methods ******************/
     
    public void setLocation(String Location)
    {
        this.Location = Location;
    }
     
    public void setDescription(String Description)
    {
        this.Description = Description;
    }
     
    public void setDate(String Date)
    {
        this.Date = Date;
    }
    
    public void setId(String Id)
    {
    	this.Id = Id;
    }
    /*********** Get Methods ****************/
     
    public String getLocation()
    {
        return this.Location;
    }
     
    public String getDescription()
    {
        return this.Description;
    }
 
    public String getDate()
    {
        return this.Date;
    }     
    
    public String getId()
    {
        return this.Id;
    }    
}