package view;

import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Represents the view for the photo processing program.
 */
public interface ImageView {

  /**
   * Make the view visible. This is usually called
   * after the view is constructed.
   */
  void makeVisible();

  /**
   * Update the currentImage for the ImageView as well as all child panels.
   * @param filename - image to be shown
   */
  void setPhoto(String filename);

  /**
   * Transmit an error message to the view, in case
   * the command could not be processed correctly.
   *
   * @param error - error message to user
   */
  void showErrorMessage(String error);

  /**
   * Signal the view to draw itself. Important to have so the image and histograms
   * will be refreshed/updated after processing operations.
   */
  void refresh();

  /**
   * add action listeners for all the buttons in the control panel.
   * @param actionEvent - Action Event.
   */
  void setListeners(ActionListener actionEvent);

  /**
   * get the button map from the control panel.
   * @return - button map,  Map< String, String > */
  Map<String, String> getButtonMap();

  /**
   * retrieve the brightness command from the text field in the control panel to be passed into
   * controller as a command from user.
   * @return - brightness command string
   */
  String getBrightnessCommand();

  /**
   * get the greyscale command from the drop-down box of the control panel, to be passed into the
   * controller as a command from user.
   * @return - greyscale command string
   */
  String getGreyscaleCommand();

  /**
   * get the apply command from the control panel, to be passed into the controller as a command
   * from the user.
   * @return - apply command string
   */
  String getApplyCommand();

  /**
   * Get the new title from control panel to be passed into controllers.
   * @return - String filename
   */
  String getNewTitle();

  /**
   * get the displayed image to be displayed.
   * @return - filename of displayed image
   */
  String getDisplayedImage();

  /**
   * Get the command from the display drop down to display a new image.
   * @return - filename to be displayed.
   */
  String getDisplaySelection();

  /**
   * Get the new path from control panel load text field to be passed into controllers.
   * @return - String filename
   */
  String getLoadPath();

  /**
   * get the filename from load command.
   * @return - String filename
   */
  String getLoadFilename();

  /**
   * get the save path from load command.
   * @return - String filename
   */
  String getSavePath();
}