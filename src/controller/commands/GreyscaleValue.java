package controller.commands;


import model.ImageModel;

/**
 * Class represents a command to greyscale an image on its VALUE.
 * See transform package subclasses for descriptions of each greyscale type.
 * <p>
 * NOTE: Once an image is greyscale on a color (GreyscaleRed, GreyscaleBlue, GreyscaleGreen) then
 * it will not change the image if this is called again, because all the values of a pixel become
 * the same value. This command would be accepted, but does not actually change anything.
 * </p>
 * <p>
 * The model will be passed in to the method and transform will be called on the model with the
 * given filename with the "option" being the GreyscaleValue class, and so it will delegate the
 * pixel editing to the model.transform.GreyscaleValue class and add it to the model
 * map as the newName.
 * </p>
 */
public class GreyscaleValue implements ImageCommands {

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
   * Instantiate the Greyscale Value object with a filename and newName. The filename
   * is the file that is being greyscale, and newName is the string that this will be saved as.
   * @param filename - file to be operated on
   * @param newName  - the newName of the file
   */
  public GreyscaleValue(String filename, String newName) {
    this.filename = filename;
    this.newName = newName;
  }


  @Override
  public void edit(ImageModel model) {
    model.transform(filename, newName, new model.transform.GreyscaleValue());
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Value Greyscale";
  }

}
