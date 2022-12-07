package model;



/**
 * CHANGE: Class name has been changed from PpmImage --> ImageImpl. this is due to new ability to
 * handle multiple image types and so this is a more appropriate naming convention.
 * <p>
 * This class implements the image interface and represents an Image in the form of a 3D pixel
 * array (int[][][]). An image also has height, width, and max (RGB) values.
 * NOTE ABOUT PIXEL REPRESENTATION: it is represented as int[3] where index 0 = red, index 1 = green
 * and index 2 = blue channel. This is the chosen representation of a pixel for our program as it
 * felt intuitive and programmatically simple to implement.
 * </p>
 * <p>
 * The decision to use an image class was a simple one. We knew that at some point our program will
 * need to be able to handle multiple image types, and so an image class is a stable approach to
 * handling and storing image data.
 * </p>
 */
public class ImageImpl implements Image {

  /**
   * Pixel array of image. Set to private and final to avoid reassignment and outside access.
   */
  private final int[][][] image;

  /**
   * Height of image. Set to private and final to avoid reassignment and outside access.
   */
  private final int height;

  /**
   * Width of image. Set to private and final to avoid reassignment and outside access.
   */
  private final int width;

  /**
   * Max value of pixel channel of image.
   * Set to private and final to avoid reassignment and outside access.
   */
  private final int max;


  /**
   * Filename is the name where image will be saved in the model map. This can be the same or equal
   * to the path name, but regardless, a different input is required to keep it in on our system
   * in order to access it.
   * Set to private and final to avoid reassignment and outside access.
   */
  private final String filename;

  /**
   * Construct a new image with a filename and pixel array representing the image.
   * @param filename - the file name of object in the model.
   * @param arr - the pixel array of an image.
   * @throws IllegalArgumentException ?????
   *
   */
  public ImageImpl(String filename, int[][][] arr) throws IllegalArgumentException {
    this.image = arr;
    this.height = image.length;
    this.width = image[0].length;
    this.max = 255;
    this.filename = filename;
  }

  /**
   * Construct a new image with a filename and pixel array, and max RGB value.
   * @param filename - the file name of object in the model.
   * @param arr - the pixel array of an image.
   * @param max - max val of pixel array.
   * @throws IllegalArgumentException ????
   *
   */
  public ImageImpl(String filename, int[][][] arr, int max) throws IllegalArgumentException {
    if (filename == null || arr == null) {
      throw new IllegalArgumentException("Null input given");
    }
    this.image = arr;
    this.height = image.length;
    this.width = image[0].length;
    this.max = max;
    this.filename = filename;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getMaxValue() {
    return this.max;
  }

  @Override
  public String getFilename() {
    return this.filename;
  }

  @Override
  public int[] getPixel(int r, int c) throws IllegalArgumentException {
    try {
      return this.image[r][c];
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalStateException("Invalid pixel request.");
    }
  }

  @Override
  public int[][][] getImage() {
    // deep copy of image.
    int[][][] copy = new int[this.height][this.width][3];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        int[] current = this.image[i][j]; // current pixel
        copy[i][j] = current;
      }
    }
    return copy;
  }
}
