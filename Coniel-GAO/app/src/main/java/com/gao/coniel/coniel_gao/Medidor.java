package com.gao.coniel.coniel_gao;

import java.util.Date;

/**
 * Created by Jhonsson on 02/11/2014.
 */
public class Medidor {

    Integer numFabrica, numSerie, fase, hilos, digitos, lecturaInst, lecturaDesinst;
    String marca, tipoMedidor, tecnologia, tension, amperaje;
    Date fechaInst,  fechaDesinst;

    public Date getFechaDesinst() {
        return fechaDesinst;
    }

    public void setFechaDesinst(Date fechaDesinst) {
        this.fechaDesinst = fechaDesinst;
    }

    public Date getFechaInst() {
        return fechaInst;
    }

    public void setFechaInst(Date fechaInst) {
        this.fechaInst = fechaInst;
    }

    public String getAmperaje() {
        return amperaje;
    }

    public void setAmperaje(String amperaje) {
        this.amperaje = amperaje;
    }

    public String getTension() {
        return tension;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getTipoMedidor() {
        return tipoMedidor;
    }

    public void setTipoMedidor(String tipoMedidor) {
        this.tipoMedidor = tipoMedidor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getLecturaDesinst() {
        return lecturaDesinst;
    }

    public void setLecturaDesinst(Integer lecturaDesinst) {
        this.lecturaDesinst = lecturaDesinst;
    }

    public Integer getLecturaInst() {
        return lecturaInst;
    }

    public void setLecturaInst(Integer lecturaInst) {
        this.lecturaInst = lecturaInst;
    }

    public Integer getDigitos() {
        return digitos;
    }

    public void setDigitos(Integer digitos) {
        this.digitos = digitos;
    }

    public Integer getHilos() {
        return hilos;
    }

    public void setHilos(Integer hilos) {
        this.hilos = hilos;
    }

    public Integer getFase() {
        return fase;
    }

    public void setFase(Integer fase) {
        this.fase = fase;
    }

    public Integer getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(Integer numSerie) {
        this.numSerie = numSerie;
    }

    public Integer getNumFabrica() {
        return numFabrica;
    }

    public void setNumFabrica(Integer numFabrica) {
        this.numFabrica = numFabrica;
    }

}
