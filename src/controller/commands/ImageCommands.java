package controller.commands;

import model.ImageModel;

/**
 * Interface that represents a command that can be done/called on an image. This is an
 * implementation
 * of the command design pattern in order to clean up and maintain extensible code in our control
 * as more types of commands are added in the future. this will avoid changing code when new edit
 * types are added, and instead we can simply add subclasses to implement more commands.
 * This class will have one method called edit and will take an image model and perform a command
 * on it.
 */
public interface ImageCommands {

  /**
   * Take an image model and perform an edit on it.
   * @param model - image model
   */
  void edit(ImageModel model);

  /**
   * To String method for our commands. This can tell the user what is happening based
   * on user input. (ie) Photo is loading "filename".
   *
   * @return String - string represenation of what command is doing.
   */
  String toString();
}
