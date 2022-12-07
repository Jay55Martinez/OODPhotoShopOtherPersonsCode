package encapsuled;

import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import model.Image;
import model.ImageModel;
import view.ControlPanel;

/**
 * Class to represent a control panel for the GUI.
 */
public class ControlPanelEnc extends ControlPanel {
  private JTextField seeds;

  /**
   * Construct a control panel, which takes an ImageModel and an Image as the currentImage to be
   * displayed. In this constructor, it calls super() to its parent class JPanel and creates all
   * the different buttons, combo boxes, text fields, and any other GUI formatting tasks.
   * All buttons are added to the button array and map after being added to the panel.
   *
   * @param model        - the current model being displayed
   * @param currentImage - the current image displayed in imagePanel
   * @throws IllegalArgumentException - if parameters are null
   */
  public ControlPanelEnc(ImageModel model, Image currentImage) throws IllegalArgumentException {
    super(model, currentImage);

    // MOSAIC PANEL
    JPanel mosaic = new JPanel();
    mosaic.setLayout(new FlowLayout());
    JButton mosaicButton = new JButton("Mosaic");
    mosaic.add(mosaicButton);
    seeds = new JTextField(6);
    mosaic.add(seeds);
    buttonMap.put(mosaicButton.getActionCommand(), "mosaic");
    buttonArray.add(mosaicButton);

    this.add(mosaic);
  }

  /**
   * Retreive the text field input from the mosaic command. Set the field back to "" after being
   * read so it is empty for the next command.
   * @return
   */
  public String getSeedsCommand() {
    String command = this.seeds.getText();
    this.seeds.setText("");
    return command;
  }
}
