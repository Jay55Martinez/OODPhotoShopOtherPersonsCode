package model.filter;

/**
 * Filtering is a basic image processing algorithm. A filter has a "kernel", which is a 2D array of
 * numbers with odd dimensions (3x3, 5x5, etc.).
 * <p>
 * The result of the filter is calculated by multiplying together corresponding numbers in the
 * kernel and the pixels and adding them. If some portions of the kernel do not overlap any pixels,
 * those pixels are not included in the computation.
 * </p>
 * <p>
 * The classes that implement this interface represent different implementations of filters that
 * will be delegated to by the Filter method in the model method filter(). This design ensures
 * an easily extensible design, as adding new filters just requires creating a new class that
 * has the needed functionality to filter an image.
 * </p>
 */
public interface Filter {


  /**
   * the edit method of this interface is designed to be applicable to all future types of filtering
   * operations. It will take a row, column, and image pixel array and use a given kernel matrix
   * that belongs to each filter class and apply that matrix to the pixel in an image.
   * @param row - row of the pixel being edited.
   * @param col - column of the pixel being edited.
   * @param img - image being operated on, as pixel array
   * @return - new pixel
   */
  int[] edit(int row, int col, int[][][] img);
}
