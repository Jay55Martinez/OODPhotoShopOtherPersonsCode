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

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

import model.ImageModel;

/**
 * Implementation of ImageController. the controller will communicate between the
 * model and view and control user inputs flow of an image.
 */
public class ControllerText implements ImageController {

  /**
   * the input stream being passed in from user. set to private and final to avoid outside access
   * and disallow reassignment.
   */
  private final Readable inputStream;

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
   * Changed to protected to allow for extension.
   */
  protected Map<String, Function<Scanner, ImageCommands>> commands;


  /**
   * Create a new instance of the controller.
   *
   * @param inputStream - Readable to receive input
   * @throws IllegalArgumentException - if any input is null.
   */
  public ControllerText(Readable inputStream, ImageModel model) throws IllegalArgumentException {

    // throw exception if model, view, or inputSteam are null
    if (inputStream == null || model == null) {
      throw new IllegalArgumentException("Null input given to constructor");
    }

    this.inputStream = inputStream;
    this.model = model;
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

  /**
   * Method to control the photo processing input from a user.
   *
   * @throws IllegalStateException - if anything goes wrong in the process of processing a photo
   */
  @Override
  public void run() throws IllegalStateException {
    System.out.println("Begin by loading an image: ");

    // new scanner object
    Scanner sc = new Scanner(this.inputStream);


    while (sc.hasNext()) { // while scanner has next, continue to check for process calls
      ImageCommands c; // store command
      Function<Scanner, ImageCommands> cmd;

      if (sc.hasNext("q") || sc.hasNext("quit") ||
          sc.hasNext("Q") || sc.hasNext("Quit")
          || sc.hasNext("QUIT")) { // quit logic
        System.out.println("Quitting controller.PhotoProcessing");
        //break;
        System.exit(0);
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

  @Override
  public void processCommand(String command) {
    return; // return nothing as this controller uses inputStream not GUI inputs.
  }


  private String printLog() {
    StringBuilder out = new StringBuilder();
    for (ImageCommands cmd : commandHistory) {
      out.append(cmd.toString()).append(System.lineSeparator());
    }
    return out.toString();
  }
}