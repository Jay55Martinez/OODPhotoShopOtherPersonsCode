package model;

import java.util.ArrayList;
import java.util.Map;
import model.transform.Histogram;
import model.transform.Transform;
import model.filter.Filter;

/**
 * The MVC Model for an image, used to store images in a map and perform operations on images.
 * The design for this model consists of a HashMap< string, Image > to represent the file name and
 * the corresponding image as an array of pixels. We add to this map of images as users enter in
 * commands through the controller. Can handle multiple Image types (anything in ImageIO package).
 * <p>
 * We opted to not include an Image class and rely purely on indexing for a file name and then
 * conducting operations on that photo array. This can be best described using the following example
 * User wants to load in an image "vertical" and perform a vertical flip and call new image
 * "vertical flipped". What our model does is add the initial image to our map, then performs the
 * flip and writes it as a new file in our map called "vertical flip".
 * </p>
 * <p>
 * If at some point we want to see the height and width of the new "vertical flipped" image, we can
 * retrieve these values by calling the methods getHeight(String filename)
 * and getWidth(String) by doing: getHeight("vertical flipped") and getHeight("vertical flipped")
 * which will get the file from the map and perform an operation on the array to calculate
 * the height.
 * </p>
 * <p>
 * This structure follows for the majority of our model, where a filename and new File name are used
 * as searching tools to access an image and then use the corresponding array to figure out some
 * type of operation or image data.
 * </p>
 */
public interface ImageModel {

  /**
   * Flip an image vertically or horizontally. Void method that adds new image that is flipped
   * to the photo map as the "newName" string. The axis represents the type of flip. Where true
   * is a vertical flip, and false is a horizontal flip. The filename being passed in will be the
   * image being indexed from map and then subsequently being operated on.
   *
   * @param axis true for vertical false for horizontal
   */
  void flip(String filename, String newName, boolean axis);


  /**
   * Is the map of images in the model empty.
   * @return boolean - is map of images empties?
   */
  boolean isEmpty();

  /**
   * Get the height of an image from map based on filename.
   * @return int - height of inputted image from model
   */
  int getHeight(String filename);

  /**
   * Get the width of an image from map based on filename.
   * @return int - width of inputted image from model
   */
  int getWidth(String filename);

  /**
   * Get the max value of an image from map based on filename.
   * @return int - max of inputted image from model
   */
  int getMax(String filename);

  /**
   * Get the pixel value of an image from map based on filename.
   *
   * @return int - max of inputted image from model
   */
  int[] getPixel(String filename, int r, int c);


  /**
   * Create a greyscale version of the image using channels or other properties.
   * @param option - an enumeration of the different options.
   * @throws IllegalArgumentException - invalid pixel encountered at any point
   *
   */
  void transform(String filename, String newName, Transform option)
      throws IllegalArgumentException;

  void filter(String filename, String newname, Filter option);

  /**
   * Increase or decrease the brightness of the image.
   * @param adjustment the amount to change the brightness by, can be positive or negative
   */
  void brightness(String filename, String newName, int adjustment);

  /**
   * Add an image to the model's map of images. Add will take a filename and array of ints that
   * represent the photo.
   * @param filename - the file name
   * @param array - photo being represented
   */
  void addImage(String filename, String array);

  /**
   * Get the pixel array of an image from the model map, when given a string representing the
   * filename.
   * @param filename - name of file
   * @return pixel array of image
   */
  int[][][] getImage(String filename);

  /**
   * Return the image object when given a filename. Will be retrieved from the model map.
   * @param filename - name of file
   * @return - Image represented as image object
   */
  Image getObject(String filename);

  /**
   * get the image histogram data of an image with a certain histogram type. The frequency
   * distribution is represented as a Map< Integer, Integer >, where the key is the pixel value
   * and the value is the frequency of that pixel in an image. This method will be used to create
   * the histogram in the view.
   * @param filename - name of file
   * @param type - type of histogram
   * @return Frequency distribution map
   */
  Map<Integer, Integer> getHistData(String filename, Histogram type);


  /**
   * Return an array of the image filenames in the model.
   * @return arrayList of strings representing filenames of images in model map
   */
  ArrayList<String> getImages();
}
