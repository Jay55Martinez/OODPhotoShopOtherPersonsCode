package model.transform;

import model.transform.AbstractTransform;

/**
 * CHANGE: This class now extends the abstract class due to shared functionality. It now will
 * perform its desired transformation using a matrix applied to pixel.
 * <p>
 * An image edit command implementation that will greyscale an image based on the luma
 * value of a pixel. Extends the AbstractTransform as it shares functionality which can be extended.
 * Luma is defined as weighted sum of : 0.2126r + 0.7152g + 0.0722b
 * </p>
 */
public class GreyscaleLuma extends AbstractTransform {

  /**
   * Construct a greyScaleLuma object, the formula above will essentially be applied to each channel
   * of the pixel.
   */
  public GreyscaleLuma() {
    super.matrix = new double[][]{{0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}};
  }
}
