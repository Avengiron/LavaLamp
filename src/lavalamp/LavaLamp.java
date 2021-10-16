package lavalamp;

import processing.core.PApplet;

/**
 * Class LavaLamp pour projet LavaLamp
 * @see http://jamie-wong.com/2014/08/19/metaballs-and-marching-squares/
 * @author Xavier
 */
public class LavaLamp extends PApplet {
  /** Tableau de cercles qui se baladent dans le canvas */
  Circle[] cercles;
  /**
   * Division du canvas en points critiques auquel on associe une valeur de
   * proximite d'un cercle
   */
  float[][] proxValues;
  /** Nombre de lignes et de colonnes du tableau */
  int rows, cols;
  /** Espace entre les points critiques */
  final int spacing = 5;

  /** Point d'entree de l'application */
  public static void main(String[] args) {
    PApplet.main(LavaLamp.class.getName());
  }

  /** Setup du PApplet */
  @Override
  public void settings() {
    size(600, 600);
  }

  /** Setup de la fenetre */
  @Override
  public void setup() {
    surface.setLocation(1200, 220);

    // 10 cercles, valeur arbitraire 
    cercles = new Circle[10];
    for (int i = 0; i < cercles.length; i++) {
      cercles[i] = new Circle(this, random(height / 8f, height / 4.8f));
    }

    // Creation du tableau. Plus le spacing est petit, plus il y  
    // aura de calcul a faire mais la precision sera plus grande.
    rows = width / spacing;
    cols = height / spacing;
    proxValues = new float[rows + 1][cols + 1];
  }

  /** Gere l'animation */
  @Override
  public void draw() {
    background(39, 39, 34);

    // Calcul de la valeur definissant la proximite
    // du point i, j par rapport a tous les cercles
    for (int i = 0; i <= rows; i++) {
      for (int j = 0; j <= cols; j++) {
        float prox = 0;
        for (Circle c : cercles) {
          float num = (c.dia / 2) * (c.dia / 2);
          float den1 = (i * spacing - c.pos.x) * (i * spacing - c.pos.x);
          float den2 = (j * spacing - c.pos.y) * (j * spacing - c.pos.y);
          prox += num / (den1 + den2);
        }
        proxValues[i][j] = prox;
      }
    }

    // Trace des formes suivant la proximite des cercles
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {

        // Recuperation des valeurs des 4 coins du carre
        float a = proxValues[i][j];
        float b = proxValues[i + 1][j];
        float c = proxValues[i + 1][j + 1];
        float d = proxValues[i][j + 1];

        // Scaling des valeurs pour conversion binaire
        int a_ = a > 1 ? 1 : 0;
        int b_ = b > 1 ? 1 : 0;
        int c_ = c > 1 ? 1 : 0;
        int d_ = d > 1 ? 1 : 0;

        // Calcul des coordonnees pour tracer les lignes (linear interpolation)
        float Ax = i * spacing;
        float Ay = j * spacing;
        float Bx = (i + 1) * spacing;
        float By = j * spacing;
        float Cx = (i + 1) * spacing;
        float Cy = (j + 1) * spacing;
        float Dx = i * spacing;
        float Dy = (j + 1) * spacing;

        // Equivalent lerp(i*spacing, (i+1)*spacing, (1 - a) / (b - a));
        float Ox = (i * spacing) + spacing * ((1 - a) / (b - a));
        float Oy = j * spacing;
        float Px = (i + 1) * spacing;
        float Py = (j * spacing) + spacing * ((1 - b) / (c - b));
        float Qx = (i * spacing) + spacing * ((1 - d) / (c - d));
        float Qy = (j + 1) * spacing;
        float Rx = i * spacing;
        float Ry = (j * spacing) + spacing * ((1 - a) / (d - a));

        // A O B
        // R   P
        // D Q C
        
        // Trace des formes selon la valeur binaire (smoothed marching squares)
        fill(191, 231, 0);
        noStroke();
        switch (getState(a_, b_, c_, d_)) {
          case 1:
            beginShape();
            vertex(Dx, Dy);
            vertex(Qx, Qy);
            vertex(Rx, Ry);
            endShape(CLOSE);
            break;
          case 2:
            beginShape();
            vertex(Cx, Cy);
            vertex(Px, Py);
            vertex(Qx, Qy);
            endShape(CLOSE);
            break;
          case 3:
            beginShape();
            vertex(Dx, Dy);
            vertex(Cx, Cy);
            vertex(Px, Py);
            vertex(Rx, Ry);
            endShape(CLOSE);
            break;
          case 4:
            beginShape();
            vertex(Bx, By);
            vertex(Ox, Oy);
            vertex(Px, Py);
            endShape(CLOSE);
            break;
          case 5:
            beginShape();
            vertex(Bx, By);
            vertex(Px, Py);
            vertex(Qx, Qy);
            vertex(Dx, Dy);
            vertex(Rx, Ry);
            vertex(Ox, Oy);
            endShape(CLOSE);
            break;
          case 6:
            beginShape();
            vertex(Bx, By);
            vertex(Cx, Cy);
            vertex(Qx, Qy);
            vertex(Ox, Oy);
            endShape(CLOSE);
            break;
          case 7:
            beginShape();
            vertex(Bx, By);
            vertex(Cx, Cy);
            vertex(Dx, Dy);
            vertex(Rx, Ry);
            vertex(Ox, Oy);
            endShape(CLOSE);
            break;
          case 8:
            beginShape();
            vertex(Ax, Ay);
            vertex(Ox, Oy);
            vertex(Rx, Ry);
            endShape(CLOSE);
            break;
          case 9:
            beginShape();
            vertex(Ax, Ay);
            vertex(Ox, Oy);
            vertex(Qx, Qy);
            vertex(Dx, Dy);
            endShape(CLOSE);
            break;
          case 10:
            beginShape();
            vertex(Ax, Ay);
            vertex(Ox, Oy);
            vertex(Px, Py);
            vertex(Cx, Cy);
            vertex(Qx, Qy);
            vertex(Rx, Ry);
            endShape(CLOSE);
            break;
          case 11:
            beginShape();
            vertex(Ax, Ay);
            vertex(Ox, Oy);
            vertex(Px, Py);
            vertex(Cx, Cy);
            vertex(Dx, Dy);
            endShape(CLOSE);
            break;
          case 12:
            beginShape();
            vertex(Ax, Ay);
            vertex(Bx, By);
            vertex(Px, Py);
            vertex(Rx, Ry);
            endShape(CLOSE);
            break;
          case 13:
            beginShape();
            vertex(Ax, Ay);
            vertex(Bx, By);
            vertex(Px, Py);
            vertex(Qx, Qy);
            vertex(Dx, Dy);
            endShape(CLOSE);
            break;
          case 14:
            beginShape();
            vertex(Ax, Ay);
            vertex(Bx, By);
            vertex(Cx, Cy);
            vertex(Qx, Qy);
            vertex(Rx, Ry);
            endShape(CLOSE);
            break;
          case 15:
            square(Ax, Ay, spacing);
            break;
          default:
            break;
        }
      }
    }

    // Mouvement des cercles
    for (Circle c : cercles) {
      c.update();
      //c.show();
    }
  }

  /**
   * Obtient l'etat binaire d'un carre de 4 valeurs
   * @param a Valeur du coin haut gauche (poids fort)
   * @param b Valeur du coin haut droit
   * @param c Valeur du coin bas droit
   * @param d Valeur du coin bas gauche (poids faible)
   * @return Valeur binaire du carre
   */
  public static int getState(int a, int b, int c, int d) {
    return a << 3 | b << 2 | c << 1 | d;
  }

}
