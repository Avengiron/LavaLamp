package lavalamp;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Class Circle pour projet LavaLamp
 * @author Xavier
 */
public class Circle {
  /** PApplet parent */
  PApplet app;
  /** Position du cercle */
  PVector pos;
  /** Velocite du cercle */
  PVector vel;
  /** Diametre du cercle */
  float dia;

  /** 
   * Constructeur du cercle. Initialise au centre du canvas, avec direction
   * aleatoire
   * @param diameter Diametre du cercle.
   */
  Circle(PApplet app, float diameter) {
    this.app = app;
    dia = diameter;
    pos = new PVector(app.width / 2, app.height / 2);
    vel = new PVector(app.random(-2.5f, 2.5f), app.random(-2.5f, 2.5f));
  }

  /** 
   * Fait evoluer la postion du cercle. Gere les rebonds contre les bords du
   * canvas.
   */
  void update() {
    pos.add(vel);
    
    if (pos.x < dia / 2) {
      pos.x = dia / 2;
      vel.x *= -1;
    } else if (pos.x > app.width - dia / 2) {
      pos.x = app.width - dia / 2;
      vel.x *= -1;
    }
    
    if (pos.y < dia / 2) {
      pos.y = dia / 2;
      vel.y *= -1;
    } else if (pos.y > app.height - dia / 2) {
      pos.y = app.height - dia / 2;
      vel.y *= -1;
    }
  }

  /** Affiche le cercle */
  void show() {
    app.stroke(255 - 191, 255 - 231, 255 - 0);
    app.noFill();
    app.ellipse(pos.x, pos.y, dia, dia);
  }
}
