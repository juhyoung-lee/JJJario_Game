package ooga.model.gameobjectcomposites;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import ooga.JjjanException;
import ooga.model.gameobjects.Player;
import ooga.model.util.MethodBundle;
import ooga.model.gameobjects.Destroyable;
import ooga.model.gameobjects.GameObject;

/**
 *
 */
public class WorldCollisionHandling {

  private Map<String, Map<String, List<MethodBundle>>> collisionMethods;
  private List<GameObject> activeGameObjects;
  private List<GameObject> activeDestroyable;
  private Player player;
  private Set<Destroyable> collisions;

  /**
   * Default constructor
   */
  public WorldCollisionHandling(Map<String, Map<String, List<MethodBundle>>> collisionMethodsMap,
      List<GameObject> activeGameObjectsList, List<GameObject> activeActorsList, Player gamePlayer) {
    collisionMethods = collisionMethodsMap;
    activeGameObjects = activeGameObjectsList;
    activeDestroyable = activeActorsList;
    player = gamePlayer;
    collisions = new HashSet<>();
  }

  /**
   *
   * @param gameObjects
   * @param destroyables
   */
  public void updateActiveGameObjects(List<GameObject> gameObjects, List<GameObject> destroyables) {
    activeGameObjects = gameObjects;
    activeDestroyable = destroyables;
  }

  /**
   *
   */
  public void detectAllCollisions() throws NoSuchMethodException, JjjanException {
    // TODO implement here
    activeDestroyable.add(player);
    activeGameObjects.add(player);
    for (GameObject actor : activeDestroyable) {
      for (GameObject collisionObject : activeGameObjects) {
        if (actor.equals(collisionObject)) {
          continue;
        }
        if (!((Destroyable) actor).determineCollision(collisionObject).isEmpty()) {
          List<MethodBundle> actorCollisionMethods = handleTagHierarchy(actor.getEntityType(), collisionObject.getEntityType());
          ((Destroyable) actor).addCollision(actorCollisionMethods);
          collisions.add(((Destroyable) actor));
        }
      }
    }
    activeDestroyable.remove(player);
    activeGameObjects.remove(player);
  }

  /**
   *
   */
  public List<Integer> executeAllCollisions()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    List<Integer> toDelete = new ArrayList<>();
    for (Destroyable destroyable : collisions) {
      destroyable.executeCollisions();
      if (!destroyable.isAlive()) {
        toDelete.add(destroyable.getId());
      }
    }
    return toDelete;
  }

//  /*
//  TODO: DELETE THIS LATER THIS IS FOR TESTING
//   */
//  public void collidedActors() {
//    StringBuilder
//    for (Destroyable a : collisions) {
//      for (String tag : a.getEntityType()) {
//
//      }
//    }
//  }

  private List<MethodBundle> handleTagHierarchy(List<String> destroyableTags, List<String> collidedTags)
      throws JjjanException {
    for (int d = destroyableTags.size() - 1; d >= 0 ; d--) {
      String dTag = destroyableTags.get(d);
      if (collisionMethods.containsKey(dTag)) {
        for (int c = collidedTags.size() - 1; c>=0; c--) {
          Map<String, List<MethodBundle>> destroyableCollisionMap = collisionMethods.get(dTag);
          if(destroyableCollisionMap.containsKey(collidedTags.get(c))) {
            return destroyableCollisionMap.get(collidedTags.get(c));
          }
        }
      }
    }
    throw new JjjanException("collision handling not defined for destroyable");
  }
}