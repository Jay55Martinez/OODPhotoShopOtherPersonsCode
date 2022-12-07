package controller;

import java.io.IOException;

/**
 * Represents the controller for an image, as part of the MVC model.A controller is used to
 * communicate between the view and the model. go() will be only public method of this interface.
 * An implementation of this interface represents an image being passed in with commands from the
 * user.
 */
public interface ImageController {

  /**
   * Conduct a set of commands on an image.
   * @throws IllegalStateException - if the file is unable to be processed or anything goes wrong.
   */
  void run() throws IllegalStateException, IOException;

  void processCommand(String command);
}
