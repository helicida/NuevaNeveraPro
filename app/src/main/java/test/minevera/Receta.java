package test.minevera;

import java.io.Serializable;

/**
 * Created by mireia on 2/01/17.
 */

public class Receta implements Serializable {

    public String imagen;
    public String area;
    public String nombreReceta;
    public String ingredientes = "" ; // la inicializo para que no me haga cosas raras como ponerme null y esas cosas
    public String textoReceta;
    public String categoria;

    //Getters

    public String getImagen() {
        return imagen;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public String getTextoReceta() {
        return textoReceta;
    }

    public String getArea() {
        return area;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    //Setters

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public void setTextoReceta(String textoReceta) {
        this.textoReceta = textoReceta;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = this.ingredientes + ingredientes;//Así consigo que se guarde la cadena
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
