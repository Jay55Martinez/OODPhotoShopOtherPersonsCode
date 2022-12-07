package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Image;
import model.ImageModel;
import model.ImageUtil;

/**
 * The image panel will be the panel of our GUI view that displays the current image. It is on a
 * scrollable pane and uses the current image as the image to display. This panel will be used
 * in the PhotoProcessingView class as a field.
 */
public class ImagePanel extends JPanel {

  /**
   * the panel size. set to private and final to avoid reassignment and outside access.
   */
  private final int panelSize;

  /**
   * the currentImage to be displayed. set to private to avoid outside access, but needs to be
   * reassigned so not final.
   */
  private Image currentImage;

  private final JLabel iconLabel;
  private final JScrollPane scrollPane;

  /**
   * Construct the image panel with an image model and current image. the background color
   * is set to white with panel size equal to 600.
   * @param model - ImageModel
   * @param currentImage - the currentImage to be displayed
   */
  public ImagePanel(ImageModel model, Image currentImage) {
    super();
    if (model == null || currentImage == null) {
      throw new IllegalArgumentException("Null input given");
    }

    this.setBackground(Color.WHITE);

    this.panelSize = 600;

    this.setPreferredSize(new Dimension(panelSize, panelSize));

    this.currentImage = currentImage;

    this.iconLabel = new JLabel();
    this.scrollPane = new JScrollPane(iconLabel);

  }

  // this is where the image is drawn
  @Override
  public void paintComponent(Graphics g) {
    ImageIcon i = ImageUtil.displayImage(currentImage);
    java.awt.Image img = i.getImage();
    int high = Math.max(this.currentImage.getHeight(), this.currentImage.getWidth());
    int low = Math.min(this.currentImage.getHeight(), this.currentImage.getWidth());
    boolean portrait = (low == this.currentImage.getWidth());
    high = (int) (((double) high / low) * panelSize);
    img = img.getScaledInstance(portrait ? panelSize : high,
        portrait ? high : panelSize,
        java.awt.Image.SCALE_DEFAULT);
    i = new ImageIcon(img);
    iconLabel.setIcon(i);
    scrollPane.setPreferredSize(new Dimension(panelSize, panelSize));
    this.add(scrollPane);
  }

  /**
   * Set the current image to the image being passed in. This currentImage is what will be displayed
   * in the GUI View.
   * @param img - ImageModel to be displayed.
   */
  public void setPhoto(Image img) {
    this.currentImage = img;
  }


}

