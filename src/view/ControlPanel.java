package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

import model.Image;
import model.ImageModel;

/**
 * The control panel extends JPanel class and is the panel of our main photoProcessing GUI View
 * that will contain all the different controls/operations that a user can perform on an image.
 * The panel will contain different buttons and combo boxes allowing the user to interact with the
 * GUI and select different filter/transformation/edit operations.
 * This panel will be used in the PhotoProcessingView class as a field.
 */
public class ControlPanel extends JPanel {

  /**
   * Image to be displayed, not final as it will be reassigned.
   */
  private Image currentImage;

  /**
   * Text fields used in GUI. Set to private to avoid outside access, but not final as these will
   * be reassigned to empty after input is read.
   */
  private JTextField brightnessInput;
  private JTextField newTitle;
  private JTextField savePath;
  private JTextField loadPath;
  private JTextField loadFilename;

  /**
   * Combo boxes representing greyscale options, filter types, and images. set to private and final
   * to avoid reassignment and outside access.
   */
  private final DefaultComboBoxModel greyscale;
  private final DefaultComboBoxModel filters;
  private final DefaultComboBoxModel types;
  private DefaultComboBoxModel images;


  /**
   * List of images, set to private and final to avoid outside access and reassignment.
   */
  private final JComboBox imagesList;

  /**
   * ArrayList representing the buttons in our control panel. Set to private and final to avoid
   * outside access and reassignment.
   * This was made protected to access it in the mosaic implementation.
   */
  protected final ArrayList<JButton> buttonArray = new ArrayList<>();

  /**
   * Button map is a map where they key is the name of the button and the value is a command to
   * be used in the controller, based on an action event. the map is used to create a string command
   * to be passed to control after an action event. Set to private and final to avoid outside access
   * and reassignment.
   *
   * This was made protected to access it in the mosaic implementation.
   */
  protected final Map<String,String> buttonMap = new HashMap<String,String>();

  /**
   * The model to be passed in. Set as private and final to avoid outside access and reassignment.
   */
  private final ImageModel model;

  /**
   * Construct a control panel, which takes an ImageModel and an Image as the currentImage to be
   * displayed. In this constructor, it calls super() to its parent class JPanel and creates all
   * the different buttons, combo boxes, text fields, and any other GUI formatting tasks.
   * All buttons are added to the button array and map after being added to the panel.
   * @param model - the current model being displayed
   * @param currentImage - the current image displayed in imagePanel
   * @throws IllegalArgumentException - if parameters are null
   */
  public ControlPanel(ImageModel model, Image currentImage) throws IllegalArgumentException {
    super();
    if (model == null || currentImage == null) {
      throw new IllegalArgumentException("Null input given");
    }
    this.model = model;
    this.currentImage = currentImage;

    this.setSize(256, 600 - 256);

    // DISPLAY PANEL
    images = new DefaultComboBoxModel();
    ArrayList<String> imagesNameList = model.getImages();
    for (String file : imagesNameList) {
      images.addElement(file);
    }
    imagesList = new JComboBox(images);
    imagesList.setSelectedIndex(imagesNameList.indexOf(this.currentImage.getFilename()));
    JPanel display = new JPanel();
    display.setLayout(new FlowLayout());
    JButton displayButton = new JButton("Display");
    display.add(displayButton);
    buttonMap.put(displayButton.getActionCommand(), "display");
    buttonArray.add(displayButton);
    display.add(imagesList);

    // TITLE PANEL
    JPanel title = new JPanel();
    title.setLayout(new FlowLayout());
    title.add(new JLabel("New Image Name"));
    newTitle = new JTextField(6);
    title.add(newTitle);

    // GREYSCALE PANEL
    JPanel greyscalePanel = new JPanel();
    greyscalePanel.setLayout(new FlowLayout());
    JButton greyscaleButton = new JButton("Greyscale");
    greyscalePanel.add(greyscaleButton);
    buttonMap.put(greyscaleButton.getActionCommand(), "greyscale");
    buttonArray.add(greyscaleButton);
    greyscale = new DefaultComboBoxModel();
    greyscale.addElement("Red");
    greyscale.addElement("Blue");
    greyscale.addElement("Green");
    greyscale.addElement("Intensity");
    greyscale.addElement("Luma");
    greyscale.addElement("Value");
    JComboBox greyscaleList = new JComboBox(greyscale);
    greyscalePanel.add(greyscaleList);

    // FILTERS PANEL
    JPanel filtersPanel = new JPanel();
    filtersPanel.setLayout(new FlowLayout());
    JButton applyButton = new JButton("Apply");
    filtersPanel.add(applyButton);
    buttonMap.put(applyButton.getActionCommand(), "apply");
    buttonArray.add(applyButton);
    filters = new DefaultComboBoxModel();
    filters.addElement("Sepia");
    filters.addElement("Blur");
    filters.addElement("Sharpen");
    JComboBox filtersList = new JComboBox(filters);
    filtersPanel.add(filtersList);

    // FLIP PANEL
    JPanel flip = new JPanel();
    flip.setLayout(new FlowLayout());
    flip.add(new JLabel("Flip"));
    JButton horizontalButton = new JButton("Horizontal");
    flip.add(horizontalButton);
    buttonMap.put(horizontalButton.getActionCommand(), "horizontal-flip");
    buttonArray.add(horizontalButton);
    JButton verticalButton = new JButton("Vertical");
    flip.add(verticalButton);
    buttonMap.put(verticalButton.getActionCommand(), "vertical-flip");
    buttonArray.add(verticalButton);

    // BRIGHTNESS PANEL
    JPanel brightness = new JPanel();
    brightness.setLayout(new FlowLayout());
    JButton brightnessButton = new JButton("Brightness");
    brightness.add(brightnessButton);
    brightnessInput = new JTextField(6);
    brightness.add(brightnessInput);
    buttonMap.put(brightnessButton.getActionCommand(), "brightness");
    buttonArray.add(brightnessButton);

    // LOAD PANEL
    JPanel load = new JPanel();
    load.setLayout(new FlowLayout());
    JButton loadButton = new JButton("Load");
    load.add(loadButton);
    buttonMap.put(loadButton.getActionCommand(), "load");
    buttonArray.add(loadButton);
    loadPath = new JTextField(6);
    load.add(loadPath);
    load.add(new JLabel("as"));
    loadFilename = new JTextField(6);
    load.add(loadFilename);

    // SAVE PANEL
    JPanel save = new JPanel();
    save.setLayout(new FlowLayout());
    JButton saveButton = new JButton("Save as");
    save.add(saveButton);
    buttonMap.put(saveButton.getActionCommand(), "save");
    buttonArray.add(saveButton);
    savePath = new JTextField(6);
    save.add(savePath);
    types = new DefaultComboBoxModel();
    types.addElement("PNG");
    types.addElement("JPG");
    types.addElement("GIF");
    types.addElement("BMP");
    types.addElement("PPM");
    JComboBox saveType = new JComboBox(types);
    save.add(saveType);


    this.setLayout(new GridLayout(9,1));

    this.add(display);
    this.add(title);
    this.add(greyscalePanel);
    this.add(filtersPanel);
    this.add(flip);
    this.add(brightness);
    this.add(new JPanel());
    this.add(load);
    this.add(save);
  }

  /**
   * Get the button map from this panel.
   * @return - Map< String, String >, representing the button string to command map
   */
  public Map<String, String> getButtonMap() {
    Map<String, String> shallowCopy = new HashMap<>();
    shallowCopy.putAll(this.buttonMap);
    return shallowCopy;
  }


  /**
   * Add action listener to every button in the button array of this panel. this is so the buttons
   * will be able to respond when clicked in the GUI. Without this, the buttons will not know when
   * have been pressed.
   * @param actionEvent - listener
   */
  public void setButtonListener(ActionListener actionEvent) {
    for (JButton button : this.buttonArray) {
      button.addActionListener(actionEvent);
    }
  }

  /**
   * Updates the current image, display drop down box, and newTitle text box if a
   * new Image was created.
   * @param image - new currentImage
   */
  public void setCurrent(Image image) {
    this.currentImage = image;
    this.images = new DefaultComboBoxModel();
    ArrayList<String> imagesNameList = this.model.getImages();
    for (String file : imagesNameList) {
      this.images.addElement(file);
      if (this.newTitle.getText().equals(file)) {
        this.newTitle.setText("");
      }
    }
    this.imagesList.setModel(this.images);
    imagesList.setSelectedIndex(imagesNameList.indexOf(this.currentImage.getFilename()));
  }

  //The following methods from this point all return strings that represent the different commands
  //to be passed into the controller from a specific button being clicked.

  /**
   * retrieve the text field input from the brightness command. Set the field back to "" after being
   * read so it is empty for next time.
   * @return - String command
   */
  public String getBrightnessCommand() {
    String command = this.brightnessInput.getText();
    this.brightnessInput.setText("");
    return command;
  }

  /**
   * this method will return the string to be used as a command from the greyscale combo box.
   * the string commandGreyscale is PART of the command that our controller registers, so the
   * String tempCommand will get the appropriate first part (i.e. red, intensity, value) and add it
   * to the commandGreyscale string. Ending with something like "red-component".
   * @return - String command
   */
  public String getGreyscaleType() {
    String commandGreyscale = "-component";
    String tempCommand =
            (this.greyscale.getSelectedItem().toString() + commandGreyscale).toLowerCase();
    return tempCommand;

  }

  /**
   * Return a string representing the command from the filters drop down menu.
   * @return - String command
   */
  public String getApplyCommand() {
    String applyCommand = this.filters.getSelectedItem().toString().toLowerCase();
    return applyCommand;
  }


  /**
   * return the string from the display image command.
   * @return - String command
   */
  public String getDisplayCommand() {
    String displayCommand = this.images.getSelectedItem().toString();
    return displayCommand;
  }

  /**
   * return the path of the image being loaded as a String.
   * @return - the path of the image being loaded as a String
   */
  public String getLoadPath() {
    String text = this.loadPath.getText();
    this.loadPath.setText("");
    return text;
  }

  /**
   * return the name of the image being loaded as a String.
   * @return - the name of the image being loaded as a String
   */
  public String getLoadFilename() {
    String text = this.loadFilename.getText();
    this.loadFilename.setText("");
    return text;
  }

  /**
   * return the path of the image being loaded as String.
   * @return - the path of the image being loaded as String
   */
  public String getSavePath() {
    String saveCommand = this.savePath.getText()
        + "." + this.types.getSelectedItem().toString().toLowerCase();
    this.savePath.setText("");
    return saveCommand;
  }

  /**
   * return the string from the new title text field.
   * @return - String command
   */
  public String getNewTitle() {
    String command = this.newTitle.getText();
    return command;
  }

  /**
   * get the displayed image from the drop down menu to be used as a load command.
   * @return - String command
   */
  public String getDisplayedImage() {
    String applyCommand = this.images.getSelectedItem().toString().toLowerCase();
    return applyCommand;
  }
}
