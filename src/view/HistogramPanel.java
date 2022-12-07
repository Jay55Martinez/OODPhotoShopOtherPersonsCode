package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;
import model.Image;
import model.ImageModel;
import model.transform.GreyscaleBlue;
import model.transform.GreyscaleGreen;
import model.transform.GreyscaleIntensity;
import model.transform.GreyscaleRed;
import model.transform.Histogram;

/**
 * the histogram panel is an extension of JPanel and will hold the image pixel histogram in the main
 * frame of the GUI. The image histogram is a histogram line chart with 4 lines, representing the
 * 3 color channels frequency distributions and the value color component. The lines are all on the
 * same histogram and color coded accordingly, with the intensity component in black and the other
 * lines with their corresponding color. This histogram essentially represents a color distribution
 * of different pixels values. This panel will be used in the PhotoProcessingView class as a field.
 */
public class HistogramPanel extends JPanel {

  /**
   * the panel size. set to private and final to avoid outside access and reassignment.
   */
  private final int panelSize;

  /**
   * The model being passed in. Set to private and final to avoid outside reassignment.
   */
  private final ImageModel model;

  /**
   * The current image, set to private to avoid outside access, but not final as it will be
   * reassigned.
   */
  private Image currentImage;

  /**
   * Construct the Histogram panel. The panel will take an model and currentImage, this currentImage
   * will be synced with the current image also represented in the image panel of the GUI, this is
   * so the histogram always reflects the current image displayed.
   * @param model - ImageModel
   * @param currentImage - Image being displayed
   */
  public HistogramPanel(ImageModel model, Image currentImage) {
    super();
    if (model == null || currentImage == null) {
      throw new IllegalArgumentException("Null input given");
    }
    this.model = model;
    this.currentImage = currentImage;


    this.panelSize = 256;

    this.setPreferredSize(new Dimension(panelSize, panelSize));

  }

  // *** this is where histogram will be painted ***
  @Override
  public void paintComponent(Graphics g) {
    //super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.drawRect(0, 0, panelSize, panelSize); // draw rectangle as the base/background


    String filename = this.currentImage.getFilename();

    // call paintHist helper on the 4 components.
    paintHist(filename, Color.RED, new GreyscaleRed(), g);
    paintHist(filename, Color.GREEN, new GreyscaleGreen(), g);
    paintHist(filename, Color.BLUE, new GreyscaleBlue(), g);
    paintHist(filename, Color.BLACK, new GreyscaleIntensity(), g);
  }

  /**
   * given a filename, color and histogram type, draw the histogram line for that image.
   * @param filename - name of file
   * @param color - the color to be drawn
   * @param type - the type of histogram (red, blue, green, or intensity)
   * @param g - graphics to be drawn on (in  this case, a rectangle)
   */
  private void paintHist(String filename, Color color, Histogram type, Graphics g) {
    g.setColor(color); // set color
    Map<Integer, Integer> hist = model.getHistData(filename, type); // get the histogram data
    int max = hist.get(-2);
    int i = 0;
    g.drawLine(0, 0, i, panelSize - (int) ((double) hist.get(i) / max * panelSize));


    for (i = 1; i < 256; i++) {
      g.drawLine(i - 1, panelSize - (int) ((double) hist.get(i - 1) / max * panelSize),
              i, panelSize - (int) ((double) hist.get(i) / max * panelSize));
    }

  }

  /**
   * Set the histogram current image to be the image passed in.
   * @param img - Image to become the currentImage.
   */
  public void setHistogram(Image img) {
    this.currentImage = img;
  }


}
