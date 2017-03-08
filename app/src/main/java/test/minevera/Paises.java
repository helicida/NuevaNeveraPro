package test.minevera;

/**
 * Created by anfer on 08/03/2017.
 */

public class Paises {
    String Nombre ;
    double lat =40.4167754;
    double longi = -3.7037901999999576;
    String Coordenadas;


    public Paises() {
    }

    public String getNombre() {
        return Nombre;
    }

    public String Coordenadas() {
        if(Nombre.equalsIgnoreCase("Irish")){
            lat = 53.41291;
            longi = -8.243889999999965;
        }
        if(Nombre.equalsIgnoreCase("Indian")){
            lat = 20.593684 ;
            longi = 78.96288000000004;
        }
        if(Nombre.equalsIgnoreCase("Italian")){
            lat = 41.87194 ;
            longi = 12.567379999999957;
        }
        if(Nombre.equalsIgnoreCase("Thai")){
            lat = 15.870032 ;
            longi = 100.99254100000007;
        }
        if(Nombre.equalsIgnoreCase("British")){
            lat = 52.3555177 ;
            longi = -1.1743197000000691;
        }
        if(Nombre.equalsIgnoreCase("Japanese")){
            lat = 36.204824;
            longi = 138.252924;
        }
        if(Nombre.equalsIgnoreCase("American")){
            lat = 54.5259614;
            longi = -105.25511870000003;
        }
        if(Nombre.equalsIgnoreCase("African")){
            lat = -8.783195;
            longi = 34.50852299999997;
        }
        if(Nombre.equalsIgnoreCase("Spain")){
            lat =40.4167754;
            longi = -3.7037901999999576;
        }
        Coordenadas = lat + ","+ longi;
        return Coordenadas;
    }

    public double getLat() {
        return lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
