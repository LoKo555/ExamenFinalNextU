package com.nextu.loginfacebook_nextu;

import com.facebook.login.widget.ProfilePictureView;

/**
 * Created by LoKo555 on 06/02/2017.
 */

public class User_Facebook {
    public static String id;

    public static String getUrl_foto() {
        return url_foto;
    }

    public static void setUrl_foto(String url_foto) {
        User_Facebook.url_foto = url_foto;
    }

    public static String url_foto;

    public static String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfilePictureView getFoto() {
        return foto;
    }

    public void setFoto(ProfilePictureView foto) {
        this.foto = foto;
    }

    public String correo;
    public String name;
    public ProfilePictureView foto;

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    private String genero;
    private String fecha;

}
