import controller.ImageController;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import encapsuled.ControllerGUIEnc;
import encapsuled.ControllerTextEnc;
import encapsuled.ImageModelMosaic;
import encapsuled.ImageModelMosaicImpl;
import encapsuled.ImageViewEnc;
import encapsuled.ImageViewEncInt;
import view.PhotoProcessingView;

/**
 * class to run program through console.
 */
public class PhotoProcessingV2 {

  /**
   * Main method to run the program.
   *
   * @param args - arguments passed in from user.
   * @throws IOException - if exception found.
   */
  public static void main(String[] args) throws IOException {
    if (args == null) {
      throw new IllegalArgumentException("null input");
    }

    ImageModelMosaic model = new ImageModelMosaicImpl("smile.png", "smile");
    Readable in;
    ImageViewEncInt view = new ImageViewEnc(model);
    ImageController controller = new ControllerGUIEnc(model, view);

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-file")) {
        in = new InputStreamReader(new FileInputStream(args[i + 1]));
        controller = new ControllerTextEnc(in, model);
      } else if (args[i].equals("-text")) {
        in = new InputStreamReader(System.in);
        controller = new ControllerTextEnc(in, model);
      }
    }
    PhotoProcessingView.setDefaultLookAndFeelDecorated(false);
    controller.run();
  }
}