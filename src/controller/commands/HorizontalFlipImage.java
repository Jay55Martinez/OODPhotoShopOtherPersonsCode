package controller.commands;

import model.ImageModel;

/**
 * Class represents a command to flip an image on its horizontal axis.
 * See GreyScalePPM package subclasses for descriptions of each greyscale type.
 * This class implements the ImageCommands interface , so it utilizes the command pattern and
 * will be represented as a function object in the controller to decipher which
 * greyscale method is required.
 * <p>
 * The model will be passed in to the method and flip method will be called on the model with the
 * given filename and a new name and axis set to true and add it to the model map as the newName.
 * </p>
 */
public class HorizontalFlipImage implements ImageCommands {

  /**
   * the filename being operated on. Set to private and final to restrict outside access
   * and reassignment.
   */
  private final String filename;

  /**
   * the new filename of the image being operated on. Set to private and final to restrict
   * outside access and reassignment.
   */
  private final String newName;

  /**
   * Instantiate the Horizontal flip object with a filename and newName. The filename
   * is the file that is being greyscale, and newName is the string that this will be saved as.
   *
   * @param filename - file to be operated on
   * @param newName  - the newName of the file
   */
  public HorizontalFlipImage(String filename, String newName) {
    this.filename = filename;
    this.newName = newName;
  }

  @Override
  public void edit(ImageModel model) {
    model.flip(filename, newName, true); // false = horizontal axis flip
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Horizontal Flip";
  }

}