package model.transform;

import java.util.Arrays;
import java.util.NoSuchElementException;

import model.transform.Transform;

/**
 * CHANGE: This class now extends the abstract class due to shared functionality. It now will
 * perform its desired transformation using a matrix applied to pixel.
 * <p>
 * An image edit command implementation that will greyscale an image based on the value.
 * Extends the AbstractTransform as it shares functionality which can be extended.
 * Value is defined as: the maximum value of the three components for each pixel
 * </p>
 */
public class GreyscaleValue implements Transform {
  /**
   * Construct a greyScaleValue  object.
   */
  public GreyscaleValue() {
    // no parameters to initialize as this greyscale uses the max value of a channel so no
    // transformation matrix used.
  }

  @Override
  public int[] edit(int[] current) throws IllegalArgumentException {
    try {
      int value = Arrays.stream(current).max().getAsInt();
      //pixel will be the returned pixel meant to be the greyscale version of current.
      //Will require reassigning so just make it private.

      return new int[]{value, value, value};
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Error in reading pixel, check input.");
    }
  }
}
