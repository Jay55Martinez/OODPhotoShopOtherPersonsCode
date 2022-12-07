import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import controller.ImageController;
import controller.commands.ImageCommands;
import encapsuled.ControllerTextEnc;
import encapsuled.ImageModelMosaic;
import encapsuled.ImageModelMosaicImpl;
import encapsuled.Mosaic;
import model.ImageModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the mosaic command.
 */
public class TestMosaic {
  ImageCommands commands = new Mosaic("smallTest", "mosaicTest", 5);
  Mosaic mosaic = new Mosaic("smallTest", "mosaicTest", 5);
  ImageModelMosaic mosaicModel = new ImageModelMosaicImpl("smile.png", "smile");
  ImageController controller;

  @Test (expected = IllegalArgumentException.class)
  public void testExceptionWhenNotGivenMosaicModel() {
    commands.edit(new ImageModelImpl("smile.png", "smile"));
  }

  @Test
  public void testMosaicToString() {
    assertEquals(mosaic.toString(), "Mosaic smallTest");
  }

  @Test
  public void testMosaicMethodInModel() {
    int seed = 12;
    mosaicModel.mosaic("smile", "smileTest1", seed);
    int[][][] image = mosaicModel.getImage("smileTest1");
    assertEquals(countColors(image), seed);
  }

  @Test
  public void testController() throws IOException {
    Readable in = new StringReader("mosaic smile mosaic 5");
    controller = new ControllerTextEnc(in , mosaicModel);
    controller.run();
    int[][][] image = mosaicModel.getImage("mosaic");
    assertEquals(countColors(image), 5);
  }

  // Helper method to count the number of colors in an image
  // the number of colors is the number of seeds
  private int countColors(int[][][] image) {
    List<int[]> differentColors = new ArrayList<>();
    differentColors.add(image[0][0]);
    boolean addColor = true;
    for (int col = 0; col < image.length; col++) {
      for (int row = 0; row < image[0].length; row++) {
        for (int i = 0; i < differentColors.size(); i++) {
          int[] colors = differentColors.get(i);
          if (colors[0] == image[col][row][0] && colors[1] == image[col][row][1] &&
                  colors[2] == image[col][row][2]) {
            addColor = false;
            break;
          }
        }
        if (addColor) {
          differentColors.add(image[col][row]);
        }
        addColor = true;
      }
    }
    return differentColors.size();
  }
}
