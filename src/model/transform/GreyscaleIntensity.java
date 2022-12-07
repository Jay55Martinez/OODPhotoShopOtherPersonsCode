package model.transform;


/**
 * CHANGE: This class now extends the abstract class due to shared functionality. It now will
 * perform its desired transformation using a matrix applied to pixel.
 * <p>
 * An image edit command implementation that will greyscale an image based on the intensity
 * value of a pixel. Extends the AbstractTransform as it shares functionality which can be extended.
 * Intensity is defined as: the average of the three components for each pixel
 * </p>
 */
public class GreyscaleIntensity extends AbstractHistogram {


  /**
   * Construct a greyScaleIntensity object. The matrix will simply be the matrix used in the
   * abstract class, so it does not need to be initialized here in constructor.
   */
  public GreyscaleIntensity() {
    // uses abstract matrix
  }

  /**
   * This method will be overridden for the intensity transformation as it will just apply the
   * average of the 3 channels to all the channels.
   * @param vector - the row from a transformation matrix
   * @param pixel - the pixel being operated on.
   * @return int = the RGB channel,it will be the average of the 3 channels, applied to each channel
   */
  @Override
  int applyMatrix(double[] vector, int[] pixel) {
    return (int) (((pixel[0] * vector[0]) + (pixel[1] * vector[1]) + (pixel[2] * vector[2])) / 3);
  }
}
