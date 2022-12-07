package controller.commands;

import model.ImageModel;

/**
 * Class represents a command to apply Sepia transformation to an image.
 * See transform package subclasses for descriptions of sepia transformation.
 * This class implements the ImageCommands interface , so it utilizes the command pattern and will
 * be represented as a function object in the controller.
 * <p>
 * The model will be passed in to the method and transform will be called on the model with the
 * given filename with the "option" being the Sepia class, and so it will delegate the pixel
 * editing to the model.transform.Sepia class and add it to the model map as the newName.
 * </p>
 */
public class Sepia implements ImageCommands {

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

  public Sepia(String filename, String newName) {
    this.filename = filename;
    this.newName = newName;
  }

  @Override
  public void edit(ImageModel model) {
    model.transform(filename, newName, new model.transform.Sepia());
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Sepia Applied";
  }
}
