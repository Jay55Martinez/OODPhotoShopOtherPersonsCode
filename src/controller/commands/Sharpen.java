package controller.commands;

import model.ImageModel;

/**
 * Class represents a command to apply the Sharpen filter to an image.
 * See filter package subclasses for descriptions of sharpen filter.
 * This class implements the ImageCommands interface , so it utilizes the command pattern and will
 * be represented as a function object in the controller in a map of commands.
 * <p><
 * The model will be passed in to the method and filter will be called on the model with the
 * given filename with the "option" being the Sharpen class, and so it will delegate the pixel
 * editing to the model.filter.Sharpen class and add it to the model map as the newName.
 * </p>
 */
public class Sharpen implements ImageCommands {

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

  public Sharpen(String filename, String newName) {
    this.filename = filename;
    this.newName = newName;
  }

  @Override
  public void edit(ImageModel model) {
    model.filter(filename, newName, new model.filter.Sharpen());
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Sharpened";
  }
}
