package controller;

import controller.ControllerGUI;
import controller.ControllerText;
import controller.ImageController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import model.ImageModel;
import model.ImageModelImpl;
import view.ImageView;
import view.PhotoProcessingView;

/**
 * class to run program through console.
 */
public class PhotoProcessing {

  /**
   * Main method to run the program.
   * @param args - arguments passed in from user.
   * @throws IOException - if exception found.
   */
  public static void main(String[] args) throws IOException {
    if (args == null ) {
      throw new IllegalArgumentException("null input");
    }

    ImageModel model = new ImageModelImpl("smile.png","smile");
    Readable in;
    ImageView view = new PhotoProcessingView(model);
    ImageController controller = new ControllerGUI(model, view);


    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-file")) {
        in = new InputStreamReader(new FileInputStream(args[i + 1]));
        controller = new ControllerText(in, model);
      } else if (args[i].equals("-text")) {
        in = new InputStreamReader(System.in);
        controller = new ControllerText(in, model);
      }
    }

    PhotoProcessingView.setDefaultLookAndFeelDecorated(false);
    controller.run();
  }
}