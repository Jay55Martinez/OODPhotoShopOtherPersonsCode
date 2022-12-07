package model.filter;

/**
 * Blur is a type of photo filtering that applies a matrix of doubles to a photo.
 * It uses a "Gaussian" blur to apply its kernel to every channel of every pixel to create a
 * blurrier looking image. Can be applied repeatedly to further blur a photo.
 * <p>
 * This class will extend the abstract filter class as it will use the same edit method, only
 * passing in a different kernel, which is the Gaussian blur kernel mentioned above.
 * </p>
 */
public class Blur extends AbstractFilter {

  /**
   * Create new Blur object that initializes the kernel to be a Gaussian blur. Will
   * call the edit method from the AbstractFilter class.
   */
  public Blur() {
    super.kernel = new double[][]{{0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};
  }
}
