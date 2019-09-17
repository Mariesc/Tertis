package com.example.marie.projettetris;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Matrice {

    ArrayList<Integer> ListPositionMatriceIni;
    ArrayList<Integer> ListCoordonneesForme;
    private boolean finJeu;
    private ArrayList<Integer> ListCouleur;
    private ArrayList<Integer> ListFinDeJeu;
    private ArrayList<Integer> ListCoordonneesMatrice;
    private ArrayList<Integer> ListBordCaseDroit;
    private ArrayList<Integer> ListBordCaseGauche;
    private ArrayList<Integer> ListBordCaseBas;
    private ArrayList<Integer> ListBordCase;
    private int couleurFormRandom;
    private MatriceBarre Barre;
    private MatriceCarre Carre;
    private MatriceDiag2 Diag2;
    private MatriceDiag1 Diag1;
    private MatriceCroix Croix;
    private MatriceL1 L1;
    private MatriceL2 L2;

    public Matrice(){
        ListBordCaseDroit = new ArrayList<>();
        ListBordCaseGauche = new ArrayList<>();
        ListBordCaseBas = new ArrayList<>();
        ListBordCase = new ArrayList<>();
        ListFinDeJeu = new ArrayList<>();
        ListCoordonneesMatrice = new ArrayList<>();
        ListCoordonneesForme = new ArrayList<>();
        ListPositionMatriceIni = new ArrayList<>();

        finJeu = false;
        couleurFormRandom = 0;
        int debutCaseForm = 13;
        int finCaseForm = 16;
        int c = 9;

        //Case fin de grille//
        //Bord droit de la grille
        while( c <=169) {
            ListBordCaseDroit.add(c);
            c = c+10;
        }
        c = 0;
        //Bord gauche de la grille
        while( c <=160) {
            ListBordCaseGauche.add(c);
            c = c+10;
        }
        c = 160;
        //Bord bas de la grille
        while( c <=169) {
            ListBordCaseBas.add(c);
            c = c+1;
        }

        //liste pour vérifier les cases en rotation de la forme
        ListBordCase.addAll(ListBordCaseDroit);
        ListBordCase.addAll(ListBordCaseGauche);
        ListBordCase.addAll(ListBordCaseBas);

        for(int o = 0; o < 10 ;o++) {
            ListFinDeJeu.add(o);
        }
        for(int j = debutCaseForm; j< finCaseForm+1 ;j++) {
            ListPositionMatriceIni.add(j);
        }
        for(int l = debutCaseForm+10; l< finCaseForm+11 ;l++) {
            ListPositionMatriceIni.add(l);
        }
        for(int m = debutCaseForm+20; m< finCaseForm+21 ;m++) {
            ListPositionMatriceIni.add(m);
        }
        for(int p = debutCaseForm+30; p< finCaseForm+31 ;p++) {
            ListPositionMatriceIni.add(p);
        }
        //on initialise la matrice des coordonnées
        //elle va bouger avec la forme sur la grille
        ListCoordonneesMatrice = ListPositionMatriceIni;
    }

    public ArrayList<Integer> getListFinDeJeu() {
        return ListFinDeJeu;
    }

    public void setListFinDeJeu(ArrayList<Integer> listFinDeJeu) {
        ListFinDeJeu = listFinDeJeu;
    }

    public void setCouleurFormRandom(int couleurFormRandom) {
        this.couleurFormRandom = couleurFormRandom;
    }

    public int getCouleurFormRandom() {
        return couleurFormRandom;
    }

    public void creerFormeRadom(ArrayList<Integer> ListCase){
        int choixForm = 0;
        choixForm = (int)(Math.random() *(7 - 0));
        couleurFormRandom = getcouleurCaseRandom();

        if(choixForm == 0){
            this.Carre = new MatriceCarre(ListCase,couleurFormRandom);
        }else if(choixForm == 1){
            this.Barre = new MatriceBarre(ListCase, this.getstyleFormeRandom1(),couleurFormRandom);
        }else if(choixForm == 2) {
            this.Diag2 = new MatriceDiag2(ListCase, this.getstyleFormeRandom1(),couleurFormRandom);
        }else if(choixForm == 3) {
            this.L1 = new MatriceL1(ListCase, this.getstyleFormeRandom2(),couleurFormRandom);
        }else if(choixForm == 4) {
            this.Diag1 = new MatriceDiag1(ListCase, this.getstyleFormeRandom1(),couleurFormRandom);
        }else if(choixForm == 5){
            this.L2= new MatriceL2(ListCase, this.getstyleFormeRandom2(),couleurFormRandom);
        }else {
            this.Croix = new MatriceCroix(ListCase, this.getstyleFormeRandom2(),couleurFormRandom);
        }

        ListCoordonneesMatrice = ListPositionMatriceIni;
    }

    public MatriceDiag1 getDiag1() {
        return Diag1;
    }

    public MatriceDiag2 getDiag2() {
        return Diag2;
    }

    public MatriceL1 getL1() {
        return L1;
    }

    public MatriceL2 getL2() {
        return L2;
    }

    public MatriceCroix getCroix(){ return Croix;}

    public MatriceBarre getBarre() {
        return Barre;
    }

    public MatriceCarre getCarre() {
        return Carre;
    }

    public Integer getListPositionMatriceIni(int indexListeMatrice) {
        return ListPositionMatriceIni.get(indexListeMatrice);
    }

    public Integer getListCoordonneesMatrice(int indexListeMatrice) {
        return ListCoordonneesMatrice.get(indexListeMatrice);
    }

    public void setListCoordonneesMatrice(ArrayList<Integer> listCoordonnees) {
        ListCoordonneesMatrice = listCoordonnees;
    }

    public ArrayList<Integer> getListCoordonneesForme() {
        return ListCoordonneesForme;
    }

    public void setListCoordonneesForme(ArrayList<Integer> listCoordonnees) {
        ListCoordonneesForme = listCoordonnees;
    }

    public Integer getstyleFormeRandom1(){
        int choixStyle = 0;
        choixStyle = (int) (Math.random()+0.5);

        return choixStyle;
    }

    public Integer getstyleFormeRandom2(){
        int choixStyle = 0;
        choixStyle = (int)(Math.random() *(3 - 0));

        return choixStyle;
    }

    public boolean getfinJeu (){
        return finJeu;
    }

    public void setfinJeu (boolean finjeu){
        finJeu = finjeu;
    }

    public Integer getcouleurCaseRandom (){
        int imageChoixRandom = 0;
        ListCouleur = new ArrayList<>();
        ListCouleur.add(R.drawable.carrebleu);
        ListCouleur.add(R.drawable.carrered);
        ListCouleur.add(R.drawable.carreviolet);
        ListCouleur.add(R.drawable.carrejaune);
        ListCouleur.add(R.drawable.carrecyan);
        ListCouleur.add(R.drawable.carrevert);
        ListCouleur.add(R.drawable.carreorange);

        imageChoixRandom = ListCouleur.get((int)(Math.random() *(ListCouleur.size()- 0)));

        return imageChoixRandom;
    }

    //test deplacement si deplacement des trois sens de la forme
    public boolean deplacementValide(String sensdeplacement,ArrayList<Integer> ListCase){
        if(sensdeplacement.equals("droite")) {
            for (int j = 0; j < getListCoordonneesForme().size(); j++) {
                if (this.ListBordCaseDroit.contains(getListCoordonneesMatrice(ListCoordonneesForme.get(j)))) {
                    return false;
                }
            }
        }
        else if(sensdeplacement.equals("gauche")) {
            for (int j = 0; j < getListCoordonneesForme().size(); j++) {
                if (this.ListBordCaseGauche.contains(getListCoordonneesMatrice(ListCoordonneesForme.get(j)))) {
                    return false;
                }
            }
        }
        else if(sensdeplacement.equals("bas")) {
            for (int j = 0; j < getListCoordonneesForme().size(); j++) {
                if (this.ListBordCaseBas.contains(getListCoordonneesMatrice(ListCoordonneesForme.get(j)))) {
                    return false;
                }
            }
        }
        return true;
    }

    //test deplacement si rotation de la forme
    public boolean deplacementValide(ArrayList<Integer> ListeCoordonneesRotation,ArrayList<Integer> ListCaseInterdite){
        for(int j = 0; j < ListeCoordonneesRotation.size() ; j++) {
            if(this.ListBordCase.contains(getListCoordonneesMatrice(ListeCoordonneesRotation.get(j)))){
                return false;
            }
            if(ListCaseInterdite.contains(getListCoordonneesMatrice(ListeCoordonneesRotation.get(j)))){
                return false;
            }
        }
        return true;
    }



    public void mouvementForm(String sensdeplacement,ArrayList<Integer> ListCase,ImageAdapter imageAdapter, int couleur){
        //mise à blanc de la forme
        for (int f = 0; f < this.ListCoordonneesForme.size(); f++) {
            ListCase.set(this.getListCoordonneesMatrice(ListCoordonneesForme.get(f)), R.drawable.carreblancfond);
        }
        imageAdapter.notifyDataSetChanged();

        if(sensdeplacement.equals("droite")) {
            //déplacement de la matrice
            for (int d = 0; d < this.ListCoordonneesMatrice.size(); d++) {
                this.ListCoordonneesMatrice.set(d, this.ListCoordonneesMatrice.get(d) + 1);
            }
        }else if (sensdeplacement.equals("gauche")) {
            //déplacement de la matrice
            for (int d = 0; d < this.ListCoordonneesMatrice.size(); d++) {
                this.ListCoordonneesMatrice.set(d, this.ListCoordonneesMatrice.get(d) - 1);
            }
        }
        else if (sensdeplacement.equals("bas")){
            //déplacement de la matrice
            for (int d = 0; d < this.ListCoordonneesMatrice.size(); d++) {
                this.ListCoordonneesMatrice.set(d, this.ListCoordonneesMatrice.get(d) + 10);
            }
        }
        //deplacement des cases de la forme
        for (int t = 0; t < this.ListCoordonneesForme.size(); t++) {
            ListCase.set(this.getListCoordonneesMatrice(ListCoordonneesForme.get(t)), couleur);
        }
        imageAdapter.notifyDataSetChanged();

    }


    public boolean deplacementDroit(String sensdeplacement,ArrayList<Integer> ListCase,ArrayList<Integer> ListCaseInterdite){
        if(deplacementValide(sensdeplacement,ListCase)){
            if(pasImpactAutreForm(sensdeplacement,ListCaseInterdite)){
                return true;
            }
            return false;

        }return false;
    }

    public boolean pasImpactAutreForm(String sensdeplacement, ArrayList<Integer> ListCaseInterdite){
        int valeurCoordonnee = 0;
        if(ListCaseInterdite.size() != 0){
            for(int j = 0; j < getListCoordonneesForme().size() ; j++) {
                if(sensdeplacement.equals("droite")) {
                    valeurCoordonnee = getListCoordonneesMatrice(ListCoordonneesForme.get(j)) + 1;
                    if (ListCaseInterdite.contains(valeurCoordonnee)) {
                        return false;
                    }
                }else if (sensdeplacement.equals("gauche")){
                    valeurCoordonnee = getListCoordonneesMatrice(ListCoordonneesForme.get(j)) -1;
                    if (ListCaseInterdite.contains(valeurCoordonnee)) {
                        return false;
                    }

                }else if (sensdeplacement.equals("bas")){
                    valeurCoordonnee = getListCoordonneesMatrice(ListCoordonneesForme.get(j)) +10;
                    if (ListCaseInterdite.contains(valeurCoordonnee)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<Integer> MiseAJourCaseInterdite(ArrayList<Integer> ListeCaseInterdite){
        for(int i = 0;i< this.ListCoordonneesForme.size() ;i++){
            ListeCaseInterdite.add(this.getListCoordonneesMatrice(this.ListCoordonneesForme.get(i)));
        }

        return ListeCaseInterdite;
    }



}


