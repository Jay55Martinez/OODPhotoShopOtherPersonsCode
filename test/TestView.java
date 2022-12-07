import controller.ControllerGUI;
import controller.ImageController;
import model.ImageModel;
import model.ImageModelImpl;

import org.junit.Test;

import java.awt.event.ActionEvent;
import java.util.Map;

import view.ImageView;
import view.PhotoProcessingView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the ImageView interface and implementations.
 */
public class TestView {

  ImageModel model = new ImageModelImpl("res/smile.png","smile");
  ImageView view = new PhotoProcessingView(model);
  ImageController controller = new ControllerGUI(model, view);

  private static class MockController extends ControllerGUI {

    public StringBuilder log;

    /**
     * Construct a controller GUI that takes a model and view. If either of parameters are null,
     * then throw IllegalArgumentException. In this contructor, the commands will be put into the
     * commands map that calls the function objects from the commands package.
     *
     * @param model - model being passed in
     * @param view  - view being passed in
     * @throws IllegalArgumentException - null input
     */
    public MockController(ImageModel model, ImageView view, StringBuilder log)
            throws IllegalArgumentException {
      super(model, view);
      this.log = log;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      this.log.append(e.getSource().getClass());
      this.log.append(" ");
      this.log.append(e.getActionCommand());
      this.log.append("\n");
    }

  }

  @Test
  public void testView() {
    ImageModel model = new ImageModelImpl("res/smile.png", "smile");
    ImageView view = new PhotoProcessingView(model);
    StringBuilder out = new StringBuilder();
    MockController controller = new MockController(model, view, out);

    String defaultControlPanel = "class view.PhotoProcessingView \n" +
            "class view.PhotoProcessingView red-component\n" +
            "class view.PhotoProcessingView sepia\n" +
            "class view.PhotoProcessingView \n" +
            "class view.PhotoProcessingView \n" +
            "class view.PhotoProcessingView .png\n" +
            "class view.PhotoProcessingView smile\n" +
            "class view.PhotoProcessingView smile\n" +
            "class view.PhotoProcessingView \n";

    ActionEvent brightness = new ActionEvent(this.view, 0, view.getBrightnessCommand());
    controller.actionPerformed(brightness);

    ActionEvent greyscale = new ActionEvent(this.view, 0, view.getGreyscaleCommand());
    controller.actionPerformed(greyscale);

    ActionEvent apply = new ActionEvent(this.view, 0, view.getApplyCommand());
    controller.actionPerformed(apply);

    ActionEvent loadFile = new ActionEvent(this.view, 0, view.getLoadFilename());
    controller.actionPerformed(loadFile);

    ActionEvent loadPath = new ActionEvent(this.view, 0, view.getLoadPath());
    controller.actionPerformed(loadPath);

    ActionEvent savePath = new ActionEvent(this.view, 0, view.getSavePath());
    controller.actionPerformed(savePath);

    ActionEvent displayImage = new ActionEvent(this.view, 0, view.getDisplayedImage());
    controller.actionPerformed(displayImage);

    ActionEvent displaySelect = new ActionEvent(this.view, 0, view.getDisplaySelection());
    controller.actionPerformed(displaySelect);

    ActionEvent title = new ActionEvent(this.view, 0, view.getNewTitle());
    controller.actionPerformed(title);

    assertEquals(controller.log.toString(), defaultControlPanel);
  }

  @Test
  public void testButtonMap() {
    Map<String, String> buttonMap = this.view.getButtonMap();
    String checkMap = "{Vertical=vertical-flip, " +
            "Load=load, " +
            "Horizontal=horizontal-flip, " +
            "Brightness=brightness, " +
            "Apply=apply, " +
            "Greyscale=greyscale, " +
            "Save as=save, " +
            "Display=display}";
    assertEquals(buttonMap.toString(), checkMap);
  }
}
