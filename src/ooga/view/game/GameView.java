package ooga.view.game;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import ooga.controller.Controller;
import ooga.controller.KeyListener;
import ooga.view.factories.SceneFactory;

public class GameView {
  private final Stage stage;
  private Scene currScene;
  private Scene newScene;
  private final String gameName;
  private final KeyListener kl;
  private final SceneFactory sf;

  public GameView(String gameName, Stage stage, KeyListener kl, Controller controller) {
    this.stage = stage;
    this.gameName = gameName;
    this.kl = kl;
    sf = new SceneFactory(stage, controller);
  }

  public void start(String filePath) {
    try {
      currScene = sf.make(filePath);
      currScene.setOnKeyPressed(makeKeyAction());
      currScene.setOnKeyReleased(makeKeyAction());
      stage.setScene(currScene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void initializeLevel(double w, double h) {
    newScene = new Scene(new Group(), w, h);
    newScene.setOnKeyPressed(makeKeyAction());
    newScene.setOnKeyReleased(makeKeyAction());
    currScene = newScene;
  }

  public void addSprite(Sprite s) {
    ((Group) currScene.getRoot()).getChildren().add(s.getImageView());
  }

  public void startLevel() {
    stage.setScene(currScene);
    stage.show();
  }

  public String getGameName() { return this.gameName; }

  private EventHandler<KeyEvent> makeKeyAction() {
    return event -> kl.propertyChange(new PropertyChangeEvent(this, "currKey", null, event));
  }
}
