package encapsuled;

import view.ImageView;

/**
 * Interface to represent a mosaic command.
 */
public interface ImageViewEncInt extends ImageView {

  /**
   * retrieve the mosaic command from the text field in the control panel to be passed into
   * controller as a command from user.
   *
   * @return - mosaic command string
   */
  public String getMosaicCommand();
}
