package controller.commands;

import model.ImageModel;

/**
 * Class represents a command to apply a blur filter to an image.
 * See filter package subclasses for descriptions of the blur filter.
 * This class implements the ImageCommands interface , so it utilizes the command pattern and
 * will be represented as a function object in the controller.
 * <p>
 * The model will be passed in to the method and filter method will be called on the model with the
 * given filename with the "option" being the Blur class, and so it will delegate
 * the pixel editing to the model.transform.Blur class and add it to the model
 * map as the newName.
 * </p>
 */
public class Blur implements ImageCommands {

  private final String filename;

  private final String newname;

  public Blur(String filename, String newname) {
    this.filename = filename;
    this.newname = newname;
  }

  @Override
  public void edit(ImageModel model) {
    model.filter(filename, newname, new model.filter.Blur());
    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "Blurred";
  }
}
