package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.TreeMap;
import model.filter.Filter;
import model.transform.Histogram;
import model.transform.Transform;

/**
 * CHANGE: Name changed from ImageModelPPM --> ImageModelImpl. This is due to now being able to
 * store multiple image types in addition to PPM Images, using the java ImageIO library.
 *<p>
 * CHANGE: Removed the greyscale() method and replaced with transform()
 *</p>
 * <p>
 * CHANGE: in brightness method, removed the adjustBrightness() helper and instead use checkMax()
 * ADDITION: checkMax is a private helper method made to make sure that RGB values stay between 0
 * and the max of an image.
 * </p>
 *<p>
 * ADDITION: Added a method called filter() that will apply a filter object to an image.
 *</p>
 * <p>
 * ADDITION: Added a method called transform() that will apply a transformation
 * object to an image.
 * </p>
 * <p>
 * Class represents an image model for images. The model acts mainly as a storage system
 * for photos that are loaded, saved, and operated. The model has only 1 field which is a hashmap
 * of < String, Image> representing the images in the model.
 * </p>
 * <p>
 * CHANGES: added 2 new methods to our model: getHistData() and getImages(), these were added in
 * to support the GUI View recently added. the getHistData() method is particularly used for the
 * histogram of the image pixel color frequencies in the GUI. getImages() primary usage is in the
 * dropdown menu of available images in the model in the GUI.
 * </p>
 */
public class ImageModelImpl implements ImageModel {

  /**
   * Images will hold the photo objects that have been uploaded and saved by the user. The string
   * will represent the filename and Image object contains the actual contents of the image. Make
   * images field private and final because we will need to reassign image pointers.
   *
   */
  private final Map<String, Image> images;


  /**
   * Construct an implementation of this class. Will receive a filename as a string. The constructor
   * will call the ImageUtil class methods in order to initialize the image, and the
   * height/width/max. This can be done by simply passing the filename as a string.
   *
   * @param path - the filename/path
   * @throws IllegalArgumentException - cannot construct image
   */
  public ImageModelImpl(String path, String filename) throws IllegalArgumentException {

    this.images = new HashMap<>();
    this.images.put(filename, ImageUtil.readImage(path, filename));
  }

  /**
   * An empty constructor used to start the controller when no images have been loaded yet.
   */
  public ImageModelImpl() {
    this.images = new HashMap<>();
  }

  public ImageModelImpl(Image image) {
    this.images = new HashMap<>();
    this.images.put(image.getFilename(), image);
  }

  @Override
  public void flip(String filename, String newName, boolean axis) {
    // boolean parameter: false = horizontal, true = vertical

    Image img = images.get(filename);
    // temp represents essentially an empty image that will be filled in and returned as flipped
    // image at end of method. Size is set to the
    int[][][] temp = new int[img.getHeight()][img.getWidth()][3];

    // double for loop to iterate through image
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        if (axis) { // if vertical flip
          temp[i][img.getWidth() - j - 1] = img.getPixel(i, j);
        } else { // if horizontal flip
          temp[img.getHeight() - i - 1][j] = img.getPixel(i, j);
        }
      }
    }

    // put the temp as a new PPM image in the images map.
    // use the newName as the file name, temp as the pixel array, and then the max value
    // from the img as the new max value.
    this.images.put(newName, new ImageImpl(newName, temp));
  }

  @Override
  public int[][][] getImage(String filename) throws IllegalStateException {

    try {
      return images.get(filename).getImage();
    } catch (NullPointerException e) {
      throw new IllegalStateException("Cannot find image.");
    }
  }

  @Override
  public Image getObject(String filename) {
    return images.get(filename);
  }

  @Override
  public void transform(String filename, String newName, Transform option)
      throws IllegalArgumentException {
    // temp represents essentially an empty image that will be filled in and returned as flipped
    // image at end of method.
    Image img = images.get(filename);

    int[][][] temp = new int[img.getHeight()][img.getWidth()][3];
    int[] current;

    // iterate through image using nested for loop
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        current = img.getPixel(i, j);// current pixel
        // call edit on the option type and it will delegate
        temp[i][j] = checkMax(filename, option.edit(current));
      }
    }
    this.images.put(newName, new ImageImpl(newName, temp));
  }

  @Override
  public void filter(String filename, String newName, Filter option) {
    Image img = images.get(filename);
    int[][][] temp = new int[img.getHeight()][img.getWidth()][3];

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {            // for each pixel in the image
        temp[i][j] = checkMax(filename, option.edit(i, j, img.getImage()));     // apply the filter
        System.out.println("infant loop?");
        System.out.println(i);
        System.out.println(j);
      }
    }
    this.images.put(newName, new ImageImpl(newName, temp));
  }

  @Override
  public void brightness(String filename, String newName, int adjustment) {
    // temp represents essentially an empty image that will be filled in and returned as flipped
    // image at end of method.
    Image img = images.get(filename);

    int[][][] temp = new int[img.getHeight()][img.getWidth()][3];

    // nested for loop to iterate through image
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {

        // alter each pixel value by the given adjustment and call checkMax on the new pixel
        temp[i][j] = checkMax(filename,
            new int[]{img.getPixel(i, j)[0] + adjustment,
                img.getPixel(i, j)[1] + adjustment,
                img.getPixel(i, j)[2] + adjustment});
      }
    }

    // image gets overwritten to the new adjusted image
    this.images.put(newName, new ImageImpl(newName, temp));
  }

  /**
   * Check that the given pixel of a file adheres to its max pixel value and does not go below 0.
   *
   * @param filename - the image being references
   * @param pixel    - the pixel
   * @return - a new pixel based on adhering to max and min pixel rules (0 < pixel channel < max)
   */
  private int[] checkMax(String filename, int[] pixel) {
    int max = getMax(filename);
    for (int i = 0; i < 3; i++) {
      if (pixel[i] > max) {
        pixel[i] = max;
      } else if (pixel[i] < 0) {
        pixel[i] = 0;
      }
    }
    return pixel;
  }

  @Override
  public boolean isEmpty() {
    return images.isEmpty();
  }

  /**
   * Add an image to our images list.
   *
   * @param path     - the file path
   * @param filename - the name of the image
   */
  @Override
  public void addImage(String path, String filename) {
    this.images.put(filename, ImageUtil.readImage(path, filename));

  }

  @Override
  public int getHeight(String filename) throws IllegalStateException {
    try {
      return this.images.get(filename).getHeight();
    } catch (NullPointerException e) {
      throw new IllegalStateException("No Images Found in Map");
    }
  }

  @Override
  public int getWidth(String filename) throws IllegalStateException {
    try {
      return this.images.get(filename).getWidth();
    } catch (NullPointerException e) {
      throw new IllegalStateException("No Images Found in Map");
    }
  }

  @Override
  public int getMax(String filename) throws IllegalStateException {
    try {
      return this.images.get(filename).getMaxValue();
    } catch (NullPointerException e) {
      throw new IllegalStateException("No Images Found in Map");
    }
  }

  @Override
  public int[] getPixel(String filename, int r, int c) throws IllegalStateException {
    try {
      return this.images.get(filename).getPixel(r, c);
    } catch (NullPointerException e) {
      throw new IllegalStateException("No Images Found in Map");
    }
  }

  @Override
  public  Map<Integer, Integer> getHistData(String filename, Histogram type) {
    Image img = images.get(filename);
    Map<Integer, Integer> frequencyDistribution = new TreeMap<>(); // treemap -> distribution rep
    int total = img.getHeight() * img.getWidth();
    int max = 0;

    frequencyDistribution.put(-1, total);
    for (int i = 0; i < 256; i++) {
      frequencyDistribution.put(i, 0);
    }

    // nested for loop to iterate through image
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int pixelValue = type.value(img.getImage()[i][j]);
        int newFrequency = frequencyDistribution.get(pixelValue) + 1;
        frequencyDistribution.put(pixelValue, newFrequency);
        if (newFrequency > max) {
          max = newFrequency;
        }
      }
    }

    frequencyDistribution.put(-2, max);

    return frequencyDistribution;
  }

  @Override
  public ArrayList<String> getImages() {
    ArrayList<String> imageList = new ArrayList<>(this.images.keySet());
    return imageList;
  }
}
