package encapsuled;

import controller.commands.ImageCommands;
import model.ImageModel;

/**
 * Class to represent a mosaic command.
 */
public class Mosaic implements ImageCommands {
  private String imageName;
  private String newName;
  private int seed;
  private ImageModelMosaic model;

  /**
   * Constructor for a mosaic command.
   *
   * @param imageName name of the current image
   * @param newName   name of the new image
   * @param seed      number of seeds for the mosaic
   */
  public Mosaic(String imageName, String newName, int seed) {
    this.imageName = imageName;
    this.newName = newName;
    this.seed = seed;
  }

  @Override
  public void edit(ImageModel model) {
    if (model instanceof ImageModelMosaic) {
      this.model = (ImageModelMosaic) model;
      this.model.mosaic(imageName, newName, seed);
      System.out.println(this.toString());
    } else {
      throw new IllegalArgumentException("Model does not support mosaic");
    }
  }

  @Override
  public String toString() {
    return "Mosaic " + this.imageName;
  }
}
