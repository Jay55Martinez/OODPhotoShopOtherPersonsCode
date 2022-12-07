package model.filter;

/**
 * *** NEW CLASS: This is a new class from the previous assignment. ***
 * <p>
 * Filtering is a basic image processing algorithm. A filter has a "kernel", which is a 2D array of
 * numbers with odd dimensions (3x3, 5x5, etc.).
 * </p>
 * <p>
 * The result of the filter is calculated by multiplying together corresponding numbers in the
 * kernel and the pixels and adding them. If some portions of the kernel do not overlap any pixels,
 * those pixels are not included in the computation.
 * </p>
 * <p>
 * Because there are many more filters that can be applied to images, than just the 2 we have been
 * tasked to complete in this assignment, we decided to create an abstract filter class that
 * implements the Filter Interface. This class will initialize a kernel in the constructor
 * and use this kernal which is a double[][] to apply the filter to a pixel in a photo.
 * This abstract class will be extended by any new filter types (blur, sharpen, etc.).
 * </p>
 * <p>
 * These classes will then be delegated to in our model when we call the filter() method and pass
 * in a filter type. The edit() method that is present in these classes that extend abstract filter
 * will be called on each pixel in a photo. This ensures our code is easily extendible, as adding
 * new filters requires just adding a new class in our filter package that extends the
 * AbstractFilter class.
 * </p>
 */
public class AbstractFilter implements Filter {

  /**
   * the kernel is the matrix that possesses the values of the filter being applied to a photo.
   * Each class that implements abstract class will possess its own kernel that will appropriately
   * filter the image.
   * need it to be accessible to classes that implement, and it will be reassigned
   * so that is why it is not final and set to protected.
   */
  protected double[][] kernel;

  /**
   * Construct a new AbstractFilter class. The kernel field is just a nested array of value 1.
   */
  public AbstractFilter() {
    this.kernel = new double[][]{{1.0}};
  }

  @Override
  public int[] edit(int row, int col, int[][][] img) {
    int[] pixel = new int[3]; // new pixel created
    int range = (kernel.length - 1) / 2; // set the range to the kernel length

    for (int i = -range; i <= range; i++) {
      for (int j = -range; j <= range; j++) {
        // if the kernel position exists in the photo, then loop through the pixel and apply
        // the given calculations from the kernel to the image pixel channels. If the kernel does
        // not have a correlated position in the photo, then skip that kernel position
        // and do nothing.
        if (       row + i > 0
                && row + i < img.length
                && col + j > 0
                && col + j < img[0].length ) {
          // perform pixel and kernel calculations for each channel of pixel
          for (int k = 0; k < 3; k++) {
            pixel[k] += (int) (kernel[i + range][j + range] * img[row + i][col + j][k]);
          }
        }
      }
    }
    // return new pixel, this pixel will become the pixel as we loop through the photo in the model
    // method filter()
    return pixel;
  }
}
