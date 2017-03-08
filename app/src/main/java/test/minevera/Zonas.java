package test.minevera;

/**
 * Created by anfer on 08/03/2017.
 */

public class Zonas {

    String zoneName;
    
    public Zonas() {}

    public String getZone() {
        return null;
    }
    
    public double returnLat(){
        if(zoneName.equalsIgnoreCase("Irish")){
           return 53.41291;
        }
        if(zoneName.equalsIgnoreCase("Indian")){
            return  20.593684;
        }
        if(zoneName.equalsIgnoreCase("Italian")){
            return 41.87194 ;
        }
        if(zoneName.equalsIgnoreCase("Thai")){
            return 15.870032 ;
        }
        if(zoneName.equalsIgnoreCase("British")){
            return 52.3555177 ;
        }
        if(zoneName.equalsIgnoreCase("Japanese")){
            return 36.204824;
        }
        if(zoneName.equalsIgnoreCase("American")){
            return 54.5259614;
        }
        if(zoneName.equalsIgnoreCase("African")){
            return -8.783195;
        }
        if(zoneName.equalsIgnoreCase("Spain")){
            return 40.4167754;
        }
        else{
            return 40.4167754;
        }
    }

    public double returnLong(){
        if(zoneName.equalsIgnoreCase("Irish")){
            return -8.243889999999965;
        }
        if(zoneName.equalsIgnoreCase("Indian")){
            return 78.96288000000004;
        }
        if(zoneName.equalsIgnoreCase("Italian")){
            return 12.567379999999957;
        }
        if(zoneName.equalsIgnoreCase("Thai")){
            return 100.99254100000007;
        }
        if(zoneName.equalsIgnoreCase("British")){
            return -1.1743197000000691;
        }
        if(zoneName.equalsIgnoreCase("Japanese")){
            return 138.252924;
        }
        if(zoneName.equalsIgnoreCase("American")){
            return -105.25511870000003;
        }
        if(zoneName.equalsIgnoreCase("African")){
            return 34.50852299999997;
        }
        if(zoneName.equalsIgnoreCase("Spain")){
            return -3.7037901999999576;
        }else {
            return -3.7037901999999576;
        }
    }

    public void setZone(String zone){
        this.zoneName = zone;
    }
}
