package model.transform;

/**
 * NEW ADDITION:
 * Interface to represent making a histogram of the pixel values of an image. the interface
 * will be implemented by classes that create a histogram based on the given channel OR on the
 * greyscale of an image (average). This design follows similarly to filter and transform
 * packages, where the classes are delegated to in the model. This allows the code to be easily
 * extensible if we were to add more histogram types.
 */
public interface Histogram {

  /**
   * This method will retrieve the data for an image histogram. It will be represented as a
   * Map< Integer, Integer > , where the key is the RGB value (0-255) and the value will be the
   * frequency distribution of that pixel.
   */
  int value(int[] pixel);
}
