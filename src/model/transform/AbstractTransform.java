package model.transform;

/**
 * This is an abstract class that implements the Transform interface.
 * <p>
 * This is a new addition to the project. We opted for an abstraction design for this package that
 * has been renamed to transform to better represent the functionality of its contents, which
 * are image transformations.
 * The abstract transform class will be extended by all current and future image transformation
 * operations, making the code easily extensible, and also greatly reducing code duplication.
 * </p>
 * <p>
 * Every transformation will have a different matrix that provides the correct outputted pixel and
 * therefore image
 * </p>
 */
public class AbstractTransform implements Transform {

  /**
   * Matrix being applied to the pixel. Not set to final as it will be reassigned, however will
   * be set to protected so only classes that extend will have access.
   */
  protected double[][] matrix;

  /**
   * Construct for abstractTransform class. the matrix field will be applied to the pixel of
   * an image.
   */
  public AbstractTransform() {
    this.matrix = new double[][]{{1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}};
  }

  /**
   * Edit will be the operation that takes in a pixel from an image and applies the matrix of
   * each transformation type to its channels, therefore modifying the pixel and returning
   * a new pixel. this method will be called in the model method transformation(), and so
   * based on the transformation object passed in, the edit method will be delegated and the new
   * pixel returned will replace current pixel in an image.
   * @param pixel - the pixel being passed in.
   * @return new pixel with applied transformation.
   */
  @Override
  public int[] edit(int[] pixel) {
    return new int[]{applyMatrix(matrix[0], pixel),
    applyMatrix(matrix[1], pixel),
    applyMatrix(matrix[2], pixel)};
  }

  /**
   * Apply matrix is a helper method called in edit() that will take in a double and pixel array
   * and return a new channel value, it will be called three times in edit to create a new
   * pixel of type int[].
   * <p>
   * REMINDER: Edit will be called on a single pixel at a time, so it is applying the matrix row to
   * a single channel of the pixel in this method.
   * </p>
   * @param vector - the row from a transformation matrix
   * @param pixel - the pixel being operated on.
   * @return int representing a pixel CHANNEL value
   */
  int applyMatrix(double[] vector, int[] pixel) {
    return (int) ((pixel[0] * vector[0]) + (pixel[1] * vector[1]) + (pixel[2] * vector[2]));
  }
}
