package com.example.marie.projettetris;

import java.util.ArrayList;

public class MatriceDiag1 extends Matrice{

    ArrayList<Integer> ListCoordBarre1;
    ArrayList<Integer> ListCoordBarre2;
    int couleurBarre =0;
    int styleDiag1 = 0;
    boolean formePlacer = false;


    public MatriceDiag1(){}

    public MatriceDiag1(ArrayList<Integer> ListCase, int choixBarre, int couleurRandom){
        styleDiag1 = choixBarre;
        couleurBarre = couleurRandom;
        ListCoordBarre1 = new ArrayList<>();
        ListCoordBarre2 = new ArrayList<>();

        //ini list coordonnées  des deux style de barre
        ListCoordBarre1.add(8);
        ListCoordBarre1.add(9);
        ListCoordBarre1.add(5);
        ListCoordBarre1.add(6);

        ListCoordBarre2.add(5);
        ListCoordBarre2.add(9);
        ListCoordBarre2.add(10);
        ListCoordBarre2.add(14);

        if(styleDiag1 == 0){
            super.ListCoordonneesForme.add(ListCoordBarre1.get(0));
            super.ListCoordonneesForme.add(ListCoordBarre1.get(1));
            super.ListCoordonneesForme.add(ListCoordBarre1.get(2));
            super.ListCoordonneesForme.add(ListCoordBarre1.get(3));
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre1.get(0)),couleurBarre);
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre1.get(1)),couleurBarre);
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre1.get(2)),couleurBarre);
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre1.get(3)),couleurBarre);
        }

        if(styleDiag1 == 1){
            super.ListCoordonneesForme.add(ListCoordBarre2.get(0));
            super.ListCoordonneesForme.add(ListCoordBarre2.get(1));
            super.ListCoordonneesForme.add(ListCoordBarre2.get(2));
            super.ListCoordonneesForme.add(ListCoordBarre2.get(3));
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre2.get(0)),couleurBarre);
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre2.get(1)),couleurBarre);
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre2.get(2)),couleurBarre);
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre2.get(3)),couleurBarre);
        }

    }


    public void setFormePlacer(boolean formePlacer) {
        this.formePlacer = formePlacer;
    }
    

    public boolean isFormePlacer() {
        return formePlacer;
    }

    public int getStyleDiag1() {
        return styleDiag1;
    }

    public void setStyleBarre(int styleBarre) {
        this.styleDiag1 = styleBarre;
    }

    public void rotation(ArrayList<Integer> ListCase, ArrayList<Integer> ListCaseInterdite){
        if(styleDiag1 == 0){
            if(super.deplacementValide(ListCoordBarre2,ListCaseInterdite)){
                //remise à blanc
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(0)), R.drawable.carreblancfond);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(1)), R.drawable.carreblancfond);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(2)), R.drawable.carreblancfond);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(3)), R.drawable.carreblancfond);

                super.setListCoordonneesForme(ListCoordBarre2);
                //nouvelles cases
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(0)), couleurBarre);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(1)), couleurBarre);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(2)), couleurBarre);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(3)), couleurBarre);
                this.setStyleBarre(1);
            }
        }
        else if(styleDiag1 == 1){
            if(super.deplacementValide(ListCoordBarre1,ListCaseInterdite)){
                //remise à blanc
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(0)),R.drawable.carreblancfond);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(1)),R.drawable.carreblancfond);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(2)),R.drawable.carreblancfond);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(3)),R.drawable.carreblancfond);

                super.setListCoordonneesForme(ListCoordBarre1);
                //nouvelles cases
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(0)),couleurBarre);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(1)),couleurBarre);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(2)),couleurBarre);
                ListCase.set(super.getListCoordonneesMatrice(super.ListCoordonneesForme.get(3)),couleurBarre);
                this.setStyleBarre(0);
            }
        }

    }

    public int getcouleurBarre(){
        return couleurBarre;
    }


}


