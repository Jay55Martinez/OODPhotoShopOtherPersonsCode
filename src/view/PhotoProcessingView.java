package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Image;
import model.ImageModel;

/**
 * This is an implementation of the IView interface
 * that uses Java Swing GUI to display an image and its
 * corresponding image histogram. It shows any error messages using a
 * pop-up dialog box.
 * The frame also has commands a user can input that consist of different edits, transformations,
 * filters, and displays of an image. The interface is fairly straightforward and a sample image
 * is provided to the user upon opening the program with an empty model.
 * For drop down menu commands, select the command click the button beside the menu to execute
 * command. Same for text fields, enter text and hit the button to execute.
 * The Display button will render the selected image to the frame.
 */
public class PhotoProcessingView extends JFrame implements ImageView {

  /**
   * the model being passed in. set to private and final to avoid outside access and reassignment.
   */
  private final ImageModel model;

  /**
   * the controlPanel being passed in. set to private and final to avoid outside access
   * and reassignment.
   */
  private final ControlPanel controlPanel;

  /**
   * the controlPanel of the frame. set to private and final to avoid outside access
   * and reassignment.
   */
  private final ImagePanel imagePanel;

  /**
   * The histogram panel of the frame, set to private and final to avoid outside access and
   * reassignment.
   */
  private final HistogramPanel histogramPanel;

  /**
   * The current image being worked on/displayed. Set to private to avoid outside access, not final
   * however because will be reassigned.
   */
  private Image currentImage;


  /**
   * Construct the PhotoProcessingView frame to represent the GUI of our photo processing
   * system. Takes in an ImageModel.
   * @param model - image model
   */
  public PhotoProcessingView(ImageModel model) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Null input given");
    }
    this.model = model;
    this.currentImage = this.model.getObject("smile");
    //this.currentImage = this.model.getImagesMap().entrySet().iterator().next().getValue();
    this.setTitle("Photo Processing");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());

    imagePanel = new ImagePanel(this.model, this.currentImage);

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BorderLayout());

    controlPanel = new ControlPanel(this.model, this.currentImage);
    histogramPanel = new HistogramPanel(this.model, this.currentImage);

    infoPanel.add(controlPanel, BorderLayout.NORTH);
    infoPanel.add(histogramPanel, BorderLayout.SOUTH);

    this.add(imagePanel, BorderLayout.LINE_START);
    this.add(infoPanel, BorderLayout.LINE_END);



    this.pack();


  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setPhoto(String filename) {
    this.currentImage = this.model.getObject(filename);
    this.imagePanel.setPhoto(this.currentImage);
    this.histogramPanel.setHistogram(this.currentImage);
    this.controlPanel.setCurrent(this.currentImage);
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public Map<String, String> getButtonMap() {
    return this.controlPanel.getButtonMap();
  }

  @Override
  public void setListeners(ActionListener actionEvent) {
    this.controlPanel.setButtonListener(actionEvent);
  }

  @Override
  public String getBrightnessCommand() {
    return this.controlPanel.getBrightnessCommand();
  }

  @Override
  public String getGreyscaleCommand() {
    return this.controlPanel.getGreyscaleType();
  }

  @Override
  public String getApplyCommand() {
    return this.controlPanel.getApplyCommand();
  }

  @Override
  public String getSavePath() {
    return this.controlPanel.getSavePath();
  }

  @Override
  public String getNewTitle() {
    return this.controlPanel.getNewTitle();
  }

  @Override
  public String getDisplayedImage() {
    return this.controlPanel.getDisplayedImage();
  }

  @Override
  public String getDisplaySelection() {
    return this.controlPanel.getDisplayCommand();
  }

  @Override
  public String getLoadPath() {
    return this.controlPanel.getLoadPath();
  }

  @Override
  public String getLoadFilename() {
    return this.controlPanel.getLoadFilename();
  }


}
