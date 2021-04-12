package ooga.model.gameobjects;

import java.util.List;
import ooga.Observable;
import ooga.model.gameobjectcomposites.Rectangle;
import ooga.model.util.Vector;

/**
 * Represents GameObject - any component of the game that has position and associate movement
 * properties.
 *
 * @author Jin Cho, Juhyoung Lee, Jessica Yang
 */
public class GameObject extends Observable {
  private List<String> entityTypes;
  private int id;
  private boolean isActive;
  protected Rectangle rect;

  /**
   * Default constructor
   */
  public GameObject(List<String> entityTypes, Vector position, int id, Vector size) {
    this.entityTypes = entityTypes;
    this.id = id;
    isActive = false;
    rect = new Rectangle(size, position);
  }

  /**
   * Returns x coordinate of position.  // TODO return the position vector instead?
   *
   * @return x coordinate
   */
  public Vector getPosition() {
    return rect.getPosition();
  }

  protected void setPosition(Vector newPosition) {
    rect.setPosition(newPosition);
  }

  /**
   *
   */
  public List<String> getEntityType() {
    // TODO implement here
    return entityTypes;
  }

  public int getId() {
    return id;
  }

  public Vector getSize() {
    return rect.getSize();
  }

  /**
   *
   * @param elapsedTime
   * @param gameGravity
   */
  public void step(double elapsedTime, double gameGravity) {

  }

  public Vector getVelocity() {
    return new Vector(0,0);
  }

  public void setActive(boolean activeState) {
    isActive = activeState;
    notifyListeners("changeVisibility", null, isActive);
  }

  /**
   * Converts model coordinates to view coordinates, and sends GameObject position.
   */
  public void sendToView(Vector frameTopL) {
    // TODO LOGIC
    double viewPositionX;
    double viewPositionY;
    viewPositionX = rect.getPosition().getX() - frameTopL.getX();
    viewPositionY = rect.getPosition().getY() - frameTopL.getY();
    notifyListeners("changeX", null, viewPositionX);
    notifyListeners("changeY", null, viewPositionY);
  }
}
