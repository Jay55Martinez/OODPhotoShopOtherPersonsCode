package model.transform;

/**
 * Abstract Histogram extends the abstract class and implements the Hisrogram Interface.
 * Calls
 */
public class AbstractHistogram extends AbstractTransform implements Histogram {

  /**
   * Gets the greyscale value of a pixel as an int.
   * @param pixel - a greyscaled pixel
   * @return the greyscale value of a pixel as an int
   */
  @Override
  public int value(int[] pixel) {
    return edit(pixel)[0];
  }

}
