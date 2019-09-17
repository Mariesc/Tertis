package com.example.marie.projettetris;

import java.util.ArrayList;

public class MatriceBarre extends Matrice{

    ArrayList<Integer> ListCoordBarre1;
    ArrayList<Integer> ListCoordBarre2;
    int couleurBarre =0;
    int styleBarre = 0;
    boolean formePlacer = false;



    public MatriceBarre(){}

    public MatriceBarre(ArrayList<Integer> ListCase,int choixBarre, int couleurRandom){
        styleBarre = choixBarre;
        couleurBarre = couleurRandom;
        ListCoordBarre1 = new ArrayList<>();
        ListCoordBarre2 = new ArrayList<>();

        //ini list coordonnées  des deux style de barre
        ListCoordBarre1.add(1);
        ListCoordBarre1.add(5);
        ListCoordBarre1.add(9);
        ListCoordBarre1.add(13);

        ListCoordBarre2.add(4);
        ListCoordBarre2.add(5);
        ListCoordBarre2.add(6);
        ListCoordBarre2.add(7);

        if(styleBarre == 0){
            super.ListCoordonneesForme.add(ListCoordBarre1.get(0));
            super.ListCoordonneesForme.add(ListCoordBarre1.get(1));
            super.ListCoordonneesForme.add(ListCoordBarre1.get(2));
            super.ListCoordonneesForme.add(ListCoordBarre1.get(3));
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre1.get(0)),couleurBarre);
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre1.get(1)),couleurBarre);
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre1.get(2)),couleurBarre);
            ListCase.set(super.getListPositionMatriceIni(ListCoordBarre1.get(3)),couleurBarre);
        }

        if(styleBarre == 1){
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

    public int getStyleBarre() {
        return styleBarre;
    }

    public void setStyleBarre(int styleBarre) {
        this.styleBarre = styleBarre;
    }

    public void rotation(ArrayList<Integer> ListCase,ArrayList<Integer> ListCaseInterdite){

        if(styleBarre == 0){
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
        else if(styleBarre == 1){
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


