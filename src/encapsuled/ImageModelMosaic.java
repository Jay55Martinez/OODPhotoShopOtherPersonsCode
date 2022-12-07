package encapsuled;

import model.ImageModel;

/**
 * Interface to represent a mosaic command.
 */
public interface ImageModelMosaic extends ImageModel {

  /**
   * Creates a mosaic of the image with the given seed.
   *
   * @param seed the seed to use for the mosaic
   */
  void mosaic(String filename, String newName, int seed);
}
