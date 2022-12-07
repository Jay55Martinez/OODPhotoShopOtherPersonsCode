package controller;


import controller.commands.AdjustBrightness;
import controller.commands.Blur;
import controller.commands.GreyscaleBlue;
import controller.commands.GreyscaleGreen;
import controller.commands.GreyscaleIntensity;
import controller.commands.GreyscaleLuma;
import controller.commands.GreyscaleRed;
import controller.commands.GreyscaleValue;
import controller.commands.HorizontalFlipImage;
import controller.commands.ImageCommands;
import controller.commands.LoadImage;
import controller.commands.SaveImage;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import controller.commands.VerticalFlipImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

import model.ImageModel;
import view.ImageView;

/**
 * Implementation of ImageController that works with a GUI View. The view uses JavaSwing library to
 * allow user interaction with photo processing. Commands from the User Interface are made into
 * strings which are then passed into this controller. The controller will communicate between the
 * model and view and control user inputs flow of an image. This controller differs from the text
 * controller because it is designed to work with ActionListeners as the source of input from the
 * user, rather than the InputStream from the previous submission. That is why this class implements
 * ActionListener, as it needs to be able to detect user actions.
 */
public class ControllerGUI implements ImageController, ActionListener {

  /**
   * the input stream being passed in from user. set to private and final to avoid outside access
   * and disallow reassignment.
   */
  private final ImageModel model;

  /**
   * the stack will hold a history of commands. set to private and final to avoid reassignment and
   * outside access.
   */
  private final Stack<ImageCommands> commandHistory;

  /**
   * the commands map will hold commands to utilize the command patter.
   * set to private and final to avoid reassignment and outside access.
   *  Changed to protected to allow for extension.
   */
  protected final Map<String, Function<Scanner, ImageCommands>> commands;

  /**
   * The view to be passed in to controller. set to private and final to avoid outside access and
   * reassignment.
   */
  private final ImageView view;

  /**
   * Construct a controller GUI that takes a model and view. If either of parameters are null, then
   * throw IllegalArgumentException. In this contructor, the commands will be put into the commands
   * map that calls the function objects from the commands package.
   * @param model - model being passed in
   * @param view - view being passed in
   * @throws IllegalArgumentException - null input
   */
  public ControllerGUI(ImageModel model, ImageView view) throws IllegalArgumentException {
    // throw exception if model or view are null
    if (view == null || model == null) {
      throw new IllegalArgumentException("Null input given to constructor");
    }
    this.model = model;
    this.view = view;

    this.commandHistory = new Stack<>();
    this.commands = new HashMap<>();

    commands.put("load", (Scanner s) -> new LoadImage(s.next(), s.next()));
    commands.put("save", (Scanner s) -> new SaveImage(s.next(), s.next()));
    commands.put("brightness", (Scanner s) ->
        new AdjustBrightness(s.nextInt(), s.next(), s.next()));
    commands.put("vertical-flip", (Scanner s) -> new VerticalFlipImage(s.next(), s.next()));
    commands.put("horizontal-flip", (Scanner s) -> new HorizontalFlipImage(s.next(), s.next()));
    commands.put("value-component", (Scanner s) -> new GreyscaleValue(s.next(), s.next()));
    commands.put("intensity-component", (Scanner s) -> new GreyscaleIntensity(s.next(), s.next()));
    commands.put("luma-component", (Scanner s) -> new GreyscaleLuma(s.next(), s.next()));
    commands.put("red-component", (Scanner s) -> new GreyscaleRed(s.next(), s.next()));
    commands.put("green-component", (Scanner s) -> new GreyscaleGreen(s.next(), s.next()));
    commands.put("blue-component", (Scanner s) -> new GreyscaleBlue(s.next(), s.next()));
    commands.put("sepia", (Scanner s) -> new Sepia(s.next(), s.next()));
    commands.put("blur", (Scanner s) -> new Blur(s.next(), s.next()));
    commands.put("sharpen", (Scanner s) -> new Sharpen(s.next(), s.next()));

  }

  @Override
  public void run() throws IllegalStateException {
    this.view.setListeners(this);
    this.view.refresh();
    this.view.makeVisible();
  }

  /**
   * Method to control the photo processing input from a user.
   *
   * @throws IllegalStateException - if anything goes wrong in the process of processing a photo
   */
  @Override
  public void processCommand(String command) throws IllegalStateException {

    Scanner sc = new Scanner(command);


    while (sc.hasNext()) { // while scanner has next, continue to check for process calls
      ImageCommands c; // store command
      Function<Scanner, ImageCommands> cmd;

      if (sc.hasNext("q") || sc.hasNext("quit") ||
          sc.hasNext("Q") || sc.hasNext("Quit")
          || sc.hasNext("QUIT")) { // quit logic
        break;
      }

      // get the command based on input
      String next = sc.next();
      cmd = this.commands.getOrDefault(next, null);
      if (cmd == null) {
        throw new IllegalStateException("Process not Found"); // if null, throw exception
      } else {
        c = cmd.apply(sc);
        c.edit(model);
        commandHistory.add(c);
      }
    }

  }

  private String printLog() {
    StringBuilder out = new StringBuilder();
    for (ImageCommands cmd : commandHistory) {
      out.append(cmd.toString()).append(System.lineSeparator());
    }
    return out.toString();
  }

  /**
   * actionPerformed is an overrode method from ActionListener. this method will listen for a
   * user input in the GUI. the buttonMap from the view will map the button thatis pressed to a
   * command string. This string is then thrown into a switch statement, and depending on the
   * command, any other useful strings will be added such as file name and other drop down menu
   * options. This string will then be passed into the processCommand() method as the command and
   * this method will perform the command on the model. The view is then refreshed to properly
   * reflect the currentImage that is to be displayed.
   * @param e - the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {

    // get the button map that maps a button to a command string
    Map<String, String> buttonMap = view.getButtonMap();

    // get the button pressed from the action
    String buttonPressed = e.getActionCommand();

    // get the buttonPressed's value from the button map
    String command = buttonMap.get(buttonPressed);

    switch (command) {
      case "brightness":
        command += " " + this.view.getBrightnessCommand();
        this.actionProcessor(command, this.view.getNewTitle(), this.view.getDisplayedImage());
        break;
      case "greyscale":
        command = this.view.getGreyscaleCommand();
        this.actionProcessor(command, this.view.getNewTitle(), this.view.getDisplayedImage());
        break;
      case "apply":
        command = this.view.getApplyCommand();
        this.actionProcessor(command, this.view.getNewTitle(), this.view.getDisplayedImage());
        break;
      case "load":
        this.actionProcessor(command, this.view.getLoadFilename(), this.view.getLoadPath());
        break;
      case "save":
        this.actionProcessor(command, this.view.getDisplayedImage(), this.view.getSavePath());
        break;
      case "display":
        this.view.setPhoto(this.view.getDisplaySelection());
        break;
      default:
        this.view.showErrorMessage("Command not found");
        break;
    }

    this.view.refresh(); // refresh the view after each actionEvent to update accordingly

  }

  /**
   * actionProcessor takes in command and the image to be saved and current displayed image and
   * sends this string command to the processCommand() method which will perform the actual
   * operations on the image. if an exception is caught, the view will show an error message box
   * and then view sets the displayed photo to be the savedImage.
   * @param command - the command being passed into processCommand as the command for controller
   * @param savedImage - the name of the image being operated on
   * @param displayedImage - the current displayed image
   */
  private void actionProcessor(String command, String savedImage, String displayedImage) {
    if (savedImage.equals("")) {
      this.view.showErrorMessage("Give your modified image a name");
    } else {
      try {
        processCommand(command + " " + displayedImage + " " + savedImage);
      } catch (Exception ex) {
        view.showErrorMessage(ex.getMessage()); // show error message if exception caught
      }
      this.view.setPhoto(savedImage); // set the photo in view to the savedImage which will make the
      // displayed image whatever was just created.
    }
  }
}
