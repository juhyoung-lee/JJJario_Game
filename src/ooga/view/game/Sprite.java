package ooga.view.game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.view.launcher.ExceptionView;

public class Sprite implements PropertyChangeListener {

  private final ImageView imageView;

  public Sprite(String gameName, String imageName, double w, double h, double x, double y) {
    imageView = new ImageView();
    imageView.setId(imageName);
    ResourceBundle imgKeys = ResourceBundle
        .getBundle("view_resources/game/SpriteImageKeys/" + gameName + "SpriteKeys");
    String imgPath = imgKeys.getString(imageName.toUpperCase());
    Image img = new Image(
        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imgPath)));
    imageView.setImage(img);
    changeVisibility(false);
    changeWidth(w);
    changeHeight(h);
    changeX(x);
    changeY(y);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String mName = evt.getPropertyName();
    Object[] mArgs = new Object[]{evt.getNewValue()};
    try {
      new Statement(this, mName, mArgs).execute();
    } catch (Exception e) {
      new ExceptionView().displayError(e);
    }
  }

  public ImageView getImageView() {
    return this.imageView;
  }

  public void changeHeight(Double h) {
    imageView.setFitHeight(h);
  }

  public void changeWidth(Double w) {
    imageView.setFitWidth(w);
  }

  public void changeVisibility(Boolean b) {
    imageView.setVisible(b);
  }

  public void changeX(Double x) {
    imageView.setLayoutX(x);
  }

  public void changeY(Double y) {
    imageView.setLayoutY(y);
  }
}
