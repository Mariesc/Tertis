package com.example.marie.projettetris;

import java.util.ArrayList;

public class MatriceCarre extends Matrice{

    ArrayList<Integer> ListCoordBarre;
    int couleurBarre =0;
    int styleBarre = 0;
    boolean formePlacer = false;

    public MatriceCarre(){}
    public MatriceCarre(ArrayList<Integer> ListCase,int couleurRandom){
        couleurBarre = couleurRandom;
        ListCoordBarre = new ArrayList<>();

        //ini list coordonnées  des deux style de barre
        ListCoordBarre.add(5);
        ListCoordBarre.add(6);
        ListCoordBarre.add(9);
        ListCoordBarre.add(10);

        super.ListCoordonneesForme.add(ListCoordBarre.get(0));
        super.ListCoordonneesForme.add(ListCoordBarre.get(1));
        super.ListCoordonneesForme.add(ListCoordBarre.get(2));
        super.ListCoordonneesForme.add(ListCoordBarre.get(3));
        ListCase.set(super.getListPositionMatriceIni(ListCoordBarre.get(0)),couleurBarre);
        ListCase.set(super.getListPositionMatriceIni(ListCoordBarre.get(1)),couleurBarre);
        ListCase.set(super.getListPositionMatriceIni(ListCoordBarre.get(2)),couleurBarre);
        ListCase.set(super.getListPositionMatriceIni(ListCoordBarre.get(3)),couleurBarre);

    }


    public void setFormePlacer(boolean formePlacer) {
        this.formePlacer = formePlacer;
    }

    public boolean isFormePlacer() {
        return formePlacer;
    }

    public int getcouleurBarre(){
        return couleurBarre;
    }
}


