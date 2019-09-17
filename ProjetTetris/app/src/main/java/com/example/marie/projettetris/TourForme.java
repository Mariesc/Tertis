package com.example.marie.projettetris;

import java.util.ArrayList;

public class TourForme {

    private boolean arretForme ;
    private Matrice matriceIni ;

    public TourForme(){
        arretForme = false;
        matriceIni = new Matrice();
    }


    public void setArretForme(boolean arretForme) {
        this.arretForme = arretForme;
    }

    public boolean isArretForme() {
        return arretForme;
    }

    public Matrice newMatrice(Matrice ancienneMatrice){
        ancienneMatrice = matriceIni;
        setArretForme(false);
        return ancienneMatrice;
    }

    public boolean verifArretForme(){
        if(arretForme){
            return true;
        }else{
            return false;
        }
    }


}
