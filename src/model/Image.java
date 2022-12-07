package model;

/**
 * NO CHANGES to report for this interface or any classes that implement the interface.
 * <p>
 * Interface that represents an image object. Can be implemented as different
 * image types. This design decision came down to being able to read in and store different
 * image types, this creates a more abstracted and comprehensive photo processing system.
 * Additionally, the Image class is the best approach for storing images as it contains fields
 * and operations that will be called repeatedly.
 * </p>
 */
public interface Image {

  /**
   * Get the height of an image. Pass in filename, so it can be indexed in our map of images.
   * @return int height
   * @throws IllegalStateException - trying to access image that does not exist
   */
  int getHeight() throws IllegalStateException;

  /**
   * Get the width of an image. Pass in filename, so it can be indexed in our map of images.f
   * @return int - width of image
   * @throws IllegalStateException - trying to access image that does not exist
   */
  int getWidth() throws IllegalStateException;

  /**
   * Get the max value of a pixel of an image.
   * Pass in filename, so it can be indexed in our map of images.f
   * @return int - max color value (usually 255)
   * @throws IllegalStateException - trying to access image that does not exist
   */
  int getMaxValue() throws IllegalStateException;


  /**
   * Get the filename as string of an image.
   * @return String - file name
   * @throws IllegalStateException - trying to access image that does not exist
   */
  String getFilename() throws IllegalStateException;


  /**
   * Get the pixel at a given position in an image.
   * Pass in filename,  it can be indexed in our map of images.f
   * Additionally, pass in the row and column we are looking to get from our int[][][] array.
   *
   * @return int - new updated pixel
   * @throws IllegalStateException - trying to access image that does not exist
   */
  int[] getPixel(int r, int c) throws IllegalStateException;

  /**
   * Get image in form of 3D array. Is a deep copy.
   *
   * @return image represented as array.
   * @throws IllegalStateException - trying to access image that does not exist
   */
  int[][][] getImage() throws IllegalStateException;

}
