package model.transform;

/**
 * CHANGE: This class now extends the abstract class due to shared functionality. It now will
 * perform its desired transformation using a matrix applied to pixel.
 * <p>
 * An image edit command implementation that will greyscale an image based on the RED value of a
 * pixel. Extends the AbstractTransform as it shares functionality which can be extended.
 * </p>
 */
public class GreyscaleRed extends AbstractHistogram {

  /**
   * New GreyscaleBlue object will have its matrix be 0s except for the red channel.
   */
  public GreyscaleRed() {
    super.matrix = new double[][]{{1.0, 0.0, 0.0}, {1.0, 0.0, 0.0}, {1.0, 0.0, 0.0}};
  }
}