package controller.commands;

import model.ImageModel;

/**
 * Class represents a command to adjust brightness  an image.
 * See adjustBrightness method in the model for additional documentation.
 * This package contains the subclasses that we used to delegate the pixel editing methods in our
 * model and so these objects will be part of the greyscale input for this class in order
 * to decipher which greyscale method is required.
 * <p>
 * The model will be passed in to the method and adjustBrightness method will be called on the
 * model with the given filename and a new name and a value to adjust it by and add it to the model
 * map as the newName.
 * </p>
 */
public class AdjustBrightness implements ImageCommands {

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
   * the amount the brightness will be changed by. Set to private and final to restrict
   * outside access and reassignment.
   */
  private final int val;

  /**
   * the filename being operated on. Set to private and final to restrict outside access
   * and reassignment.
   *
   * @param val      - the amount brighness will be changed by
   * @param filename - file to be operated on
   * @param newName  - the newName of the file
   */
  public AdjustBrightness(int val, String filename, String newName) {
    this.val = val;
    this.filename = filename;
    this.newName = newName;

  }

  @Override
  public void edit(ImageModel model) {
    // call the greyscale method on brightness PPM object to delegate method.
    // the filename is the image being worked on and newName is new name of file.
    model.brightness(filename, newName, val);
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Adjusted Brightness by " + val;
  }
}
