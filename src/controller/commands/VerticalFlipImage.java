package controller.commands;

import model.ImageModel;

/**
 * Class represents a command to flip an image on its vertical axis.
 * See GreyScalePPM package subclasses for descriptions of each greyscale type.
 * This class implements the ImageCommands interface , so it utilizes the command pattern and
 * will be represented as a function object in the controller to decipher which
 * image processing operation is required.
 * <p>
 * The model will be passed in to the method and flip method will be called on the model with the
 * given filename and a new name and axis set to false and add it to the model map as the newName.
 * </p>
 */
public class VerticalFlipImage implements ImageCommands {

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
   * Create instance of a vertical Flip object that will be used to delegate
   * the vertical flip method onto our model.
   */
  public VerticalFlipImage(String filename, String newName) {
    this.filename = filename;
    this.newName = newName;
  }

  @Override
  public void edit(ImageModel model) {
    model.flip(filename, newName, false);
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Vertical Flip";
  }
}

