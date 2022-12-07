package controller.commands;

import model.ImageModel;
import model.ImageUtil;

/**
 * Class represents a command to save an image to a file.
 */
public class SaveImage implements ImageCommands {

  /**
   * the filename being operated on. Set to private and final to restrict outside access
   * and reassignment.
   */
  private final String filename;

  /**
   * the new path of the image being operated on. Set to private and final to restrict
   * outside access and reassignment.
   */
  private final String path;

  /**
   * Create instance of an SaveImage object that will be used to save an image to a file.
   */
  public SaveImage(String path, String filename) {
    this.filename = filename;
    this.path = path;
  }

  @Override
  public void edit(ImageModel model) {
    ImageUtil.saveImage(path, model.getObject(filename));
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Saved Image " + filename;
  }
}

