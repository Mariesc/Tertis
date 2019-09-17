package com.example.marie.projettetris;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;


public class GameActivity extends AppCompatActivity{

    private ArrayList<Integer> ListCase;
    private ArrayList<Integer> ListCaseInterdite;
    private ImageAdapter imageAdapter;
    private  TourForme tourforme;
    private ArrayList<Integer> ListLigneIni;
    private ArrayList<Integer> ListLigne;
    private ArrayList<Integer> ListMusique;
    Matrice matriceForme;
    MatriceBarre Barre = null;
    MatriceCarre Carre = null;
    MatriceDiag1 Diag1 = null;
    MatriceDiag2 Diag2 = null;
    MatriceL1 L1 = null;
    MatriceL2 L2 = null;
    MatriceCroix Croix = null;
    private MediaPlayer mPlayer;
    private Boolean isStartSon;
    private Boolean isStart;
    private Boolean Gagnant;
    private Button stopSon;
    private Button pause;
    private Button droite;
    private Button bas;
    private Button gauche;
    private Button rota;
    private TextView scoreText;
    private TextView lineText;
    private CountDownTimer TimeDeplacementForme;
    private int nbFormeFinDeJeu;
    private int nbForme;
    private int vitesse;
    private int interval;
    private int score;
    private int finscore;
    private int nbligne;
    private String difficultegame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final String difficulte = getIntent().getStringExtra("difficulte");
        //gérer le son de la musique
        final TextView scoreText = findViewById(R.id.textScore);
        final TextView lineText = findViewById(R.id.textLine);
        Button stopSon = findViewById(R.id.buttonPauseSon);
        Button pause = findViewById(R.id.buttonPause);
        Button droite = findViewById(R.id.buttonDroit);
        Button gauche = findViewById(R.id.buttonGauche);
        final Button bas = findViewById(R.id.buttonBas);
        Button rota = findViewById(R.id.buttonRotation);

        //Initialisation des variables
        isStartSon = true;
        isStart = true;
        vitesse = 0;
        interval = 1000;
        score = 0;
        nbFormeFinDeJeu = 0;
        nbForme = 0;
        nbligne = 0;
        ListMusique = new ArrayList<>();
        Gagnant = true;
        setdifficultegame(difficulte);
        scoreText.setText(String.valueOf(score));
        lineText.setText(String.valueOf(nbligne));


        //creation list pour gerer la suppression d'une ligne pleine
        ListLigneIni = new ArrayList<>();
        ListLigne = new ArrayList<>();
        for(int i=0; i< 10;i++){
            ListLigneIni.add(0);
        }
        ListLigne = ListLigneIni;
        //creation de la liste
        ListCase = new ArrayList<>();
        //boucle, ajout des cases blanches de fond
        for(int i=0; i< 170;i++){
            ListCase.add(R.drawable.carreblancfond);
        }

        //choix de musiqu,vitessee du game suivant la difficulté
        if(difficulte.equals("easy")){
            vitesse = 1;
            interval = 800;
            nbFormeFinDeJeu = 100;
            playSound(getListMusiqueRandom(difficulte));
        }
        else if(difficulte.equals("medium")){
            vitesse = 1;
            interval = 400;
            nbFormeFinDeJeu = 100;
            playSound(getListMusiqueRandom(difficulte));

        }else if(difficulte.equals("hard")){
            vitesse = 1;
            interval = 250;
            nbFormeFinDeJeu = 100;
            playSound(getListMusiqueRandom(difficulte));

        }else if(difficulte.equals("original")){
            vitesse = 1;
            interval = 700;
            nbFormeFinDeJeu = 230;
            playSound(getListMusiqueRandom(difficulte));
        }

        //creation de la grille de jeu
        GridView gridview = (GridView) findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this,ListCase);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(GameActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        //creation du timer de déplacement de la forme
        TimeDeplacementForme = new CountDownTimer(vitesse * interval, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                //on deplacement la forme vers le bas
                bas.callOnClick();
            }
        };


        //////DEBUT DU JEU //////

        //premier tour
        //creation du tour qui va gérer tout les tours d'apparition des formes de la game
        //creation  de la matrice d'apparition des formes sur la grille
        tourforme = new TourForme();
        ListCaseInterdite = new ArrayList<>();
        matriceForme = new Matrice();
        nbForme = 1;
        //creation forme aléatoire
        matriceForme.creerFormeRadom(ListCase);
        Carre = matriceForme.getCarre();
        Barre = matriceForme.getBarre();
        Diag1 = matriceForme.getDiag1();
        Diag2 = matriceForme.getDiag2();
        L1 = matriceForme.getL1();
        L2 = matriceForme.getL2();
        Croix = matriceForme.getCroix();

        //si toutes les formes ne sont pas passees
        //on deplacement automatiquement la forme
        if(isStart) {
            if(nbForme < nbFormeFinDeJeu){
                TimeDeplacementForme.start();
            }
            //Sinon fin du jeu
            if(nbForme == nbFormeFinDeJeu){
                matriceForme.setfinJeu(true);
                accueil(score,matriceForme.getfinJeu(),difficulte);
            }
        }

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playSound(getListMusiqueRandom(getdifficultegame()));
            }
        });

        //deplacement vers la droite au click du bouton
        droite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                if (isStart) {
                    if (Barre != null && !Barre.isFormePlacer()) {
                        if (Barre.deplacementDroit("droite", ListCase, ListCaseInterdite)) {
                            Barre.mouvementForm("droite", ListCase, imageAdapter, Barre.getcouleurBarre());
                        }
                    } else if (Carre != null && !Carre.isFormePlacer()) {
                        if (Carre.deplacementDroit("droite", ListCase, ListCaseInterdite)) {
                            Carre.mouvementForm("droite", ListCase, imageAdapter, Carre.getcouleurBarre());
                        }
                    } else if (Diag1 != null && !Diag1.isFormePlacer()) {
                        if (Diag1.deplacementDroit("droite", ListCase, ListCaseInterdite)) {
                            Diag1.mouvementForm("droite", ListCase, imageAdapter, Diag1.getcouleurBarre());
                        }
                    } else if (Diag2 != null && !Diag2.isFormePlacer()) {
                        if (Diag2.deplacementDroit("droite", ListCase, ListCaseInterdite)) {
                            Diag2.mouvementForm("droite", ListCase, imageAdapter, Diag2.getcouleurBarre());
                        }
                    } else if (L1 != null && !L1.isFormePlacer()) {
                        if (L1.deplacementDroit("droite", ListCase, ListCaseInterdite)) {
                            L1.mouvementForm("droite", ListCase, imageAdapter, L1.getcouleurBarre());
                        }
                    } else if (L2 != null && !L2.isFormePlacer()) {
                        if (L2.deplacementDroit("droite", ListCase, ListCaseInterdite)) {
                            L2.mouvementForm("droite", ListCase, imageAdapter, L2.getcouleurBarre());
                        }
                    }else if (Croix != null && !Croix.isFormePlacer()) {
                        if (Croix.deplacementDroit("droite", ListCase, ListCaseInterdite)) {
                            Croix.mouvementForm("droite", ListCase, imageAdapter, Croix.getcouleurBarre());
                        }
                    }
                    imageAdapter.notifyDataSetChanged();
                }
            }
        });

        //deplacement vers la gauche au click du bouton
        gauche.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                if (isStart) {
                    if (Barre != null && !Barre.isFormePlacer()) {
                        if (Barre.deplacementDroit("gauche", ListCase, ListCaseInterdite)) {
                            Barre.mouvementForm("gauche", ListCase, imageAdapter, Barre.getcouleurBarre());
                        }
                    } else if (Carre != null && !Carre.isFormePlacer()) {
                        if (Carre.deplacementDroit("gauche", ListCase, ListCaseInterdite)) {
                            Carre.mouvementForm("gauche", ListCase, imageAdapter, Carre.getcouleurBarre());
                        }
                    } else if (Diag1 != null && !Diag1.isFormePlacer()) {
                        if (Diag1.deplacementDroit("gauche", ListCase, ListCaseInterdite)) {
                            Diag1.mouvementForm("gauche", ListCase, imageAdapter, Diag1.getcouleurBarre());
                        }
                    } else if (Diag2 != null && !Diag2.isFormePlacer()) {
                        if (Diag2.deplacementDroit("gauche", ListCase, ListCaseInterdite)) {
                            Diag2.mouvementForm("gauche", ListCase, imageAdapter, Diag2.getcouleurBarre());
                        }
                    } else if (L1 != null && !L1.isFormePlacer()) {
                        if (L1.deplacementDroit("gauche", ListCase, ListCaseInterdite)) {
                            L1.mouvementForm("gauche", ListCase, imageAdapter, L1.getcouleurBarre());
                        }
                    } else if (L2 != null && !L2.isFormePlacer()) {
                        if (L2.deplacementDroit("gauche", ListCase, ListCaseInterdite)) {
                            L2.mouvementForm("gauche", ListCase, imageAdapter, L2.getcouleurBarre());
                        }
                    }else if (Croix != null && !Croix.isFormePlacer()) {
                        if (Croix.deplacementDroit("gauche", ListCase, ListCaseInterdite)) {
                            Croix.mouvementForm("gauche", ListCase, imageAdapter, Croix.getcouleurBarre());
                        }
                    }
                    imageAdapter.notifyDataSetChanged();
                }
            }
        });

         //deplacement vers le bas au click du bouton
          bas.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View V) {
                  if (isStart) {
                      if (Barre != null && !Barre.isFormePlacer()) {
                          if (Barre.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Barre.mouvementForm("bas", ListCase, imageAdapter, Barre.getcouleurBarre());
                              imageAdapter.notifyDataSetChanged();
                          } else if (!Barre.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Barre.MiseAJourCaseInterdite(ListCaseInterdite);
                              Barre.setFormePlacer(true);
                              tourforme.setArretForme(true);
                          }
                      } else if (Carre != null && !Carre.isFormePlacer()) {
                          if (Carre.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Carre.mouvementForm("bas", ListCase, imageAdapter, Carre.getcouleurBarre());
                              imageAdapter.notifyDataSetChanged();
                          } else if (!Carre.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Carre.MiseAJourCaseInterdite(ListCaseInterdite);
                              Carre.setFormePlacer(true);
                              tourforme.setArretForme(true);
                          }
                      } else if (Diag1 != null && !Diag1.isFormePlacer()) {
                          if (Diag1.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Diag1.mouvementForm("bas", ListCase, imageAdapter, Diag1.getcouleurBarre());
                              imageAdapter.notifyDataSetChanged();
                          } else if (!Diag1.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Diag1.MiseAJourCaseInterdite(ListCaseInterdite);
                              Diag1.setFormePlacer(true);
                              tourforme.setArretForme(true);
                          }
                      } else if (Diag2 != null && !Diag2.isFormePlacer()) {
                          if (Diag2.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Diag2.mouvementForm("bas", ListCase, imageAdapter, Diag2.getcouleurBarre());
                              imageAdapter.notifyDataSetChanged();
                          } else if (!Diag2.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Diag2.MiseAJourCaseInterdite(ListCaseInterdite);
                              Diag2.setFormePlacer(true);
                              tourforme.setArretForme(true);
                          }
                      } else if (L1 != null && !L1.isFormePlacer()) {
                          if (L1.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              L1.mouvementForm("bas", ListCase, imageAdapter, L1.getcouleurBarre());
                              imageAdapter.notifyDataSetChanged();
                          } else if (!L1.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              L1.MiseAJourCaseInterdite(ListCaseInterdite);
                              L1.setFormePlacer(true);
                              tourforme.setArretForme(true);

                          }
                      } else if (L2 != null && !L2.isFormePlacer()) {
                          if (L2.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              L2.mouvementForm("bas", ListCase, imageAdapter, L2.getcouleurBarre());
                              imageAdapter.notifyDataSetChanged();
                          } else if (!L2.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              L2.MiseAJourCaseInterdite(ListCaseInterdite);
                              L2.setFormePlacer(true);
                              tourforme.setArretForme(true);
                          }
                      }else if (Croix != null && !Croix.isFormePlacer()) {
                          if (Croix.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Croix.mouvementForm("bas", ListCase, imageAdapter, Croix.getcouleurBarre());
                              imageAdapter.notifyDataSetChanged();
                          } else if (!Croix.deplacementDroit("bas", ListCase, ListCaseInterdite)) {
                              Croix.MiseAJourCaseInterdite(ListCaseInterdite);
                              Croix.setFormePlacer(true);
                              tourforme.setArretForme(true);
                          }
                      }


                      //si forme poser on recreer une matrice et on recreer une forme
                      if (tourforme.verifArretForme()) {
                          //vérif si ligne complete sur la grille
                          while(verifLignePleine()) {
                              //si ligne pleine ajout score et lignes
                              ligneGrille(scoreText, lineText);
                          }
                          //creer nouvelle matrice
                          matriceForme = tourforme.newMatrice(matriceForme);
                          nbForme = nbForme + 1;
                          //creer nuvelle forme
                          matriceForme.creerFormeRadom(ListCase);
                          Carre = matriceForme.getCarre();
                          Barre = matriceForme.getBarre();
                          Diag1 = matriceForme.getDiag1();
                          Diag2 = matriceForme.getDiag2();
                          L1 = matriceForme.getL1();
                          L2 = matriceForme.getL2();
                          Croix = matriceForme.getCroix();

                          imageAdapter.notifyDataSetChanged();
                      }

                      //augmente la vitesse dans le niveau
                      if(difficulte.equals("original")) {
                          if(nbForme == 30) {
                              interval = interval - 100;
                          }
                          if(nbForme == 60) {
                              interval = interval - 100;
                          }
                          if(nbForme == 90) {
                              interval = interval - 100;
                          }
                          if(nbForme == 120) {
                              interval = interval - 100;
                          }
                          if(nbForme == 150) {
                              interval = interval - 100;
                          }
                          if(nbForme == 180) {
                              interval = interval - 100;
                          }
                          if(nbForme == 210) {
                              interval = interval - 100;
                          }
                      }
                      else if(nbForme == nbFormeFinDeJeu%2){
                          interval = interval -100;
                      }

                      //si forme bloque la matrice d apparition de la forme = fin du jeu
                      if (bloqueApparitionForme()) {
                          Gagnant = false;
                          matriceForme.setfinJeu(true);
                      }

                      //fin de jeu si de 0 à 9 il y a une case interdite
                      //fin de jeu si le block touche le haut de la grille
                      if (toucheHautGrille()) {
                          Gagnant = false;
                          matriceForme.setfinJeu(true);
                      }

                      //si toutes les formes ne sont pas passees
                      //on deplacement automatiquement la forme
                      if (nbForme < nbFormeFinDeJeu) {
                          TimeDeplacementForme.start();
                      }
                      //Sinon fin du jeu
                      if (nbForme == nbFormeFinDeJeu) {
                          Gagnant = true;
                          matriceForme.setfinJeu(true);
                          accueil(score,matriceForme.getfinJeu(),difficulte);
                      }

                      //si fin du jeu retour page d'accueil et affiche le score
                      if (matriceForme.getfinJeu()) {
                          //retour page
                          accueil(score,matriceForme.getfinJeu(),difficulte);
                      }
                  }
              }
          });

        //rotation au click du bouton
        rota.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                if (isStart) {
                    if (Barre != null && !Barre.isFormePlacer()) {
                        Barre.rotation(ListCase,ListCaseInterdite);
                        imageAdapter.notifyDataSetChanged();
                    } else if (Diag1 != null && !Diag1.isFormePlacer()) {
                        Diag1.rotation(ListCase,ListCaseInterdite);
                        imageAdapter.notifyDataSetChanged();

                    } else if (Diag2 != null && !Diag2.isFormePlacer()) {
                        Diag2.rotation(ListCase,ListCaseInterdite);
                        imageAdapter.notifyDataSetChanged();
                    } else if (L1 != null && !L1.isFormePlacer()) {
                        L1.rotation(ListCase,ListCaseInterdite);
                        imageAdapter.notifyDataSetChanged();
                    } else if (L2 != null && !L2.isFormePlacer()) {
                        L2.rotation(ListCase,ListCaseInterdite);
                        imageAdapter.notifyDataSetChanged();
                    }else if (Croix != null && !Croix.isFormePlacer()) {
                        Croix.rotation(ListCase,ListCaseInterdite);
                        imageAdapter.notifyDataSetChanged();
                    }

                }

            }
        });

        //musique pause et start après click
        stopSon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                initPlaySound();
            }
        });


        //musique pause et start après click
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                playGame();
            }
        });

    }

/*    private void cancel(){
        if(TimeDeplacementForme != null){
            TimeDeplacementForme.cancel();
            TimeDeplacementForme = null;
        }
    }*/

    public void ligneGrille(TextView scoreText,TextView lineText) {
        int compteurLigne = 0;
        //avoir les valeurs de la ligne a tester
        ArrayList<Integer> lignegrille = new ArrayList();
        //avoir les coordonnées de debut et de fin d'une ligne pleine
        ArrayList<Integer> LigneASupp = new ArrayList();

        int j = 0;
        int debut = 0;
        int fin = 0;

        //en partant du bas de la grille
        for (int k = ListCase.size() - 1; k >= 0; k--) {
            lignegrille.add(k);
            if (j == 9) {
                debut = k;
                fin = debut + 9;
                int p = 0;

                for (int m = 0; m < lignegrille.size(); m++) {
                    if (ListCaseInterdite.contains(lignegrille.get(m))) {
                        p = p + 1;
                    }
                }
                if (p == 10) {
                    compteurLigne = compteurLigne+1;
                    LigneASupp.add(debut);
                    LigneASupp.add(fin);
                    supressionLignes(LigneASupp, scoreText, lineText);
                    //vide liste ligne à supprimer
                    for (int h = 0; h <= LigneASupp.size(); h++) {
                        LigneASupp.remove(0);
                    }
                }
                //vide listeligne
                for (int o = 0; o < 10; o++) {
                    lignegrille.remove(0);
                }
                p = 0;
                j = -1;
                debut = 0;
                fin = 0;
            }

            j = j + 1;
        }
        //bonus de score si plusieurs lignes pleines
        if(compteurLigne > 1){
            score = score + (50*compteurLigne);
            scoreText.setText(String.valueOf(score));
            imageAdapter.notifyDataSetChanged();
        }

        compteurLigne = 0;
    }


    public void supressionLignes(ArrayList<Integer> LigneASupp,TextView scoreText,TextView lineText){
        int compteur = 0;
        int debutligne = 0;
        int finligne = 0;
        int indexDebutdeLigne = 0;
        int indexCase = 0;
        int indexGrille = 0;
        int valeurcase = 0;
        int c = 0;
        int f = 0;
        int h = 0;

        while( h < LigneASupp.size()){
            compteur = compteur+1;
            //toutes les deux valeurs
            if(compteur == 2) {
                debutligne = LigneASupp.get(h - 1);
                finligne = LigneASupp.get(h);
                indexDebutdeLigne = 0;
                indexCase = 0;
                indexGrille = 0;
                valeurcase = 0;
                c = 0;
                f = 0;

                Collections.sort(ListCaseInterdite);
                //récupère l'index de la valeur de debut de ligne

                while( c < ListCaseInterdite.size()){
                    if(ListCaseInterdite.get(c)==debutligne){
                        indexDebutdeLigne = c;
                        c = ListCaseInterdite.size();
                    }
                    c= c+1;
                }
                //récupère l'index de la grille
                indexCase = ListCaseInterdite.get(indexDebutdeLigne);
                while( f < ListCase.size()){
                    if(f == indexCase){
                        indexGrille = f-1;
                    }
                    f=f+1;
                }
                //supprime les valeurs des cases interdites de la ligne supprimée
                for(int y = indexDebutdeLigne; y <= indexDebutdeLigne+9; y++){
                    ListCaseInterdite.remove(indexDebutdeLigne);
                }
                //decale les cases interdites à la ligne dans dessous
                for(int u = 0; u < indexDebutdeLigne; u++){
                    ListCaseInterdite.set(u,ListCaseInterdite.get(u)+10);
                }

                //mise à blanc de la ligne
                for (int i = debutligne; i <= finligne; i++) {
                    ListCase.set(i, R.drawable.carreblancfond);
                }
                //decale la grille
                for(int g = indexGrille ; g >=0 ; g--){
                    valeurcase = ListCase.get(g);
                    //ListCase.set(g,R.drawable.carreblancfond);
                    ListCase.set(g+10,valeurcase);
                }

                //ajout score
                score = score + 50;
                scoreText.setText(String.valueOf(score));
                //ajout nb ligne
                nbligne = nbligne + 1;
                lineText.setText(String.valueOf(nbligne));
                //mise à jour visuel
                imageAdapter.notifyDataSetChanged();
                compteur = 0;
                debutligne = 0;
                finligne = 0;
            }
            h = h+1;
        }

    }


    public boolean verifLignePleine(){
        //avoir les valeurs de la ligne a tester
        ArrayList<Integer> lignegrille = new ArrayList();
        //avoir les coordonnées de debut et de fin d'une ligne pleine
        ArrayList<Integer> LigneASupp = new ArrayList();
        boolean lignepleine = false;
        int j = 0;
        int debut = 0;
        int fin = 0;

        //en partant du bas de la grille
        for (int k = 0; k < ListCase.size(); k++) {
            lignegrille.add(k);
            if (j == 9) {
                debut = k;
                fin = debut + 9;
                int p = 0;

                for (int m = 0; m < lignegrille.size(); m++) {
                    if (ListCaseInterdite.contains(lignegrille.get(m))) {
                        p = p + 1;
                    }
                }
                if (p == 10) {
                    lignepleine =  true;
                }
                //vide listeligne
                for (int o = 0; o < 10; o++) {
                    lignegrille.remove(0);
                }
                p = 0;
                j = -1;
                debut = 0;
                fin = 0;
            }
            j = j + 1;
        }
        return lignepleine;
    }

    public void accueil(int score,Boolean findeJeu,String difficulte){
        TimeDeplacementForme.cancel();
        isStartSon = false;
        mPlayer.pause();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("score",score);
        intent.putExtra("difficulteGame",difficulte);
        intent.putExtra("gagnant",Gagnant);
        intent.putExtra("finJeu",findeJeu);
        onDestroy();
        startActivity(intent);
    }
    public void setCarre(MatriceCarre carre){
        Carre = carre;
    }

    public void setBarre(MatriceBarre barre) {
        Barre = barre;
    }

    public void setDiag1(MatriceDiag1 diag1) {
        Diag1 = diag1;
    }

    public void setDiag2(MatriceDiag2 diag2) {
        Diag2 = diag2;
    }

    public void setL1(MatriceL1 l1) {
        L1 = l1;
    }

    public void setL2(MatriceL2 l2) {
        L2 = l2;
    }

    public void setCroix(MatriceCroix Croix) {
        Croix = Croix;
    }

    private ArrayList<Integer> getListCaseInterdite() {
        return ListCaseInterdite;
    }

    public Integer getListCaseInterdite(int indexListeMatrice) {
        return ListCaseInterdite.get(indexListeMatrice);
    }

    public boolean bloqueApparitionForme() {
        if (ListCaseInterdite.size() != 0) {
            for (int j = 0; j < ListCaseInterdite.size(); j++) {
                if (matriceForme.ListPositionMatriceIni.contains(getListCaseInterdite(j))) {
                    return true;
                }
            }

        }return false;
    }

    public boolean toucheHautGrille() {
        if (ListCaseInterdite.size() != 0) {
            for (int j = 0; j < ListCaseInterdite.size(); j++) {
                if (matriceForme.getListFinDeJeu().contains(getListCaseInterdite(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    private void playSound(int resId) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
    }

    public void initPlaySound()
    {
        if(isStartSon)
        {
            mPlayer.pause();
            isStartSon = false;
        } else{
            mPlayer.start();
            isStartSon = true;
        }
    }

    public void playGame()
    {
        if(isStart)
        {
            mPlayer.pause();
            isStartSon = false;
            isStart = false;

        } else{
            mPlayer.start();
            TimeDeplacementForme.start();
            isStart = true;
        }
    }


    public Integer getListMusiqueRandom (String difficulte){
        int sonChoixRandom = 0;
        ListMusique = new ArrayList<>();
        if(difficulte.equals("easy")){
            ListMusique.add(R.raw.easyevolutionofgamemusic);
            ListMusique.add(R.raw.easyoriginaltetris);

        }
        if(difficulte.equals("medium")){
            ListMusique.add(R.raw.mediumlennycodefiction);
            ListMusique.add(R.raw.mediumsonic);
            ListMusique.add(R.raw.mediumbison);
        }
        if(difficulte.equals("hard")){
            ListMusique.add(R.raw.hardmanuelgas);
            ListMusique.add(R.raw.hardsonicspeed);
            ListMusique.add(R.raw.hardinitialdejavu);
        }
        if(difficulte.equals("original")){
            ListMusique.add(R.raw.easyevolutionofgamemusic);
            ListMusique.add(R.raw.hardmanuelgas);
            ListMusique.add(R.raw.mediumlennycodefiction);
            ListMusique.add(R.raw.mediumsonic);
            ListMusique.add(R.raw.hardsonicspeed);
            ListMusique.add(R.raw.mediumbison);
            ListMusique.add(R.raw.easyoriginaltetris);
            ListMusique.add(R.raw.hardinitialdejavu);
        }


        sonChoixRandom = ListMusique.get((int)(Math.random() *(ListMusique.size()- 0)));

        return sonChoixRandom;
    }

    public void setdifficultegame(String difficulte){
        difficultegame = difficulte;

    }

    public String getdifficultegame(){
        return difficultegame;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
