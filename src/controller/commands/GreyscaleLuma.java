package controller.commands;


import model.ImageModel;

/**
 * Class represents a command to apply greyscale Luma transformation to an image.
 * See transform package subclasses for descriptions of Greyscale Luma transformation.
 * This class implements the ImageCommands interface , so it utilizes the command pattern and
 * will be represented as a function object in the controller.
 * <p>
 * The model will be passed in to the method and transform will be called on the model with the
 * given filename with the "option" being the GreyscaleLuma class, and so it will delegate the pixel
 * editing to the model.transform.GreyscaleLuma class and add it to the model map as the newName.
 * </p>
 */
public class GreyscaleLuma implements ImageCommands {

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
   * Instantiate the Greyscale Luma object with a filename and newName. The filename
   * is the file that is being greyscale, and newName is the string that this will be saved as.
   *
   * @param filename - file to be operated on
   * @param newName  - the newName of the file
   */
  public GreyscaleLuma(String filename, String newName) {
    this.filename = filename;
    this.newName = newName;
  }

  @Override
  public void edit(ImageModel model) {
    model.transform(filename, newName, new model.transform.GreyscaleLuma());
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Luma Greyscale";
  }
}