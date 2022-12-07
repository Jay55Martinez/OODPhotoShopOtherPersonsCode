package model.filter;

/**
 * Sharpen can be seen as the opposite of blur, in some ways as it accentuates the edges of
 * a photo, giving it a "sharper" look. The kernel/matrix used for a sharpening filter is different
 * to that of the blur filter, and so it will have its own class that extends the abstract class
 * The kernel used for filter is larger than that of blur, but follows the same filtering
 * rules where it will apply itself to any available positions in the image.
 *<p>
 * Method will call edit() with the initialized kernel.
 *</p>
 */
public class Sharpen extends AbstractFilter {

  /**
   * Create new Sharpen object that initializes the kernel. Will
   * call the edit method from the AbstractFilter class.
   */
  public Sharpen() {
    super.kernel = new double[][]{{-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1.0, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}};
  }
}
