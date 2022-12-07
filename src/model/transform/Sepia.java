package model.transform;

/**
 * CHANGE: This class now extends the abstract class due to shared functionality. It now will
 * perform its desired transformation using a matrix applied to pixel.
 * <p>
 * An image edit command implementation that will transform an image to sepia
 * value of a pixel. Extends the AbstractTransform as it shares functionality which can be extended.
 * Sepia is referred to as the reddish brown tone found in photos in early 19th and 20th century.
 * </p>
 */
public class Sepia extends AbstractTransform {

  /**
   * Construct a Sepia transformation  object.
   */
  public Sepia() {
    super.matrix = new double[][]{{.393, .769, .189}, {.349, .686, .168}, {.272, .534, .131}};
  }
}