package wish.wurmatron.api.interfaces;

public interface IDimensionFall {

  /**
   * Dimension The Player is Currently in
   */
  int getStartingDimensionID();

  /**
   * Level at wich the player will be telepoprted
   */
  int getY();


  /**
   * Height at witch the player will spawn at
   */
  int getSpawnHeight();

  /*
  Dimension ID
   */
  int getDimension();

  /**
   * Fall Damage upon Impact
   */
  int getFallDamage();
}
