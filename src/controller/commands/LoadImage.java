package controller.commands;

import model.ImageModel;


/**
 * Class represents a command to load in an image. Loading an image will add it to our model
 * image storage.
 */
public class LoadImage implements ImageCommands {

  /**
   * the file being loaded. Set to private and final to restrict outside access
   * and reassignment.
   */
  private final String path;

  /**
   * the filename being operated on. Set to private and final to restrict outside access
   * and reassignment.
   */
  private final String filename;


  /**
   * Create instance of an Load object that will be used to delegate
   * the brightness method onto our model.
   *
   * @param path - image path
   * @param filename - image name
   */

  public LoadImage(String path, String filename) {
    this.path = path;
    this.filename = filename;
  }


  @Override
  public void edit(ImageModel model) {
    model.addImage(path, filename);
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Loaded " + filename;
  }
}

