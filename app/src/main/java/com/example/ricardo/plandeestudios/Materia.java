package com.example.ricardo.plandeestudios;
//Clase que nos permite desplegar el ListView
public class Materia {

    private String clave, nombre;
    private int creditos, verano;

    public Materia(){

    }

    public Materia(String c, String n, int cr, int v){
        clave = c;
        nombre = n;
        creditos = cr;
        verano = v;
    }

    int getCreditos(){
        return creditos;
    }
    String getClave(){
        return clave;
    }

    String getNombre(){
        return nombre;
    }
    void setClave(String a){
        clave = a;
    }
    void setNombre(String a){
        nombre = a;
    }
    void setCreditos(int a){
        creditos = a;
    }
    void setVerano(int a){
        verano = a;
    }
}
