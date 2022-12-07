import controller.ControllerText;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.commands.ImageCommands;
import controller.ImageController;
import controller.commands.AdjustBrightness;
import controller.commands.GreyscaleBlue;
import controller.commands.GreyscaleGreen;
import controller.commands.GreyscaleIntensity;
import controller.commands.GreyscaleLuma;
import controller.commands.GreyscaleRed;
import controller.commands.GreyscaleValue;
import controller.commands.HorizontalFlipImage;
import controller.commands.LoadImage;
import controller.commands.SaveImage;
import controller.commands.VerticalFlipImage;
import java.util.Arrays;
import model.ImageModel;
import model.ImageModelImpl;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ImageController and ImageCommands.
 */
public class TestController {

  @Test
  public void testControllerLoadSave() {
    String s = "load res/twoByTwo.ppm two save res/saved1.ppm two q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/saved1.ppm");
    assertTrue(saved.exists());
  }

  @Test
  public void testControllerVFlip() {
    int[][][] flip = {{{240, 58, 220}, {210, 78, 251}, {253, 62, 75}},
        {{255, 63, 63}, {255, 62, 62}, {250, 59, 132}}};
    String s = "load res/smallTest.ppm two vertical-flip two flip save res/vflip.ppm flip q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/vflip.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("flip"), flip);
  }

  @Test
  public void testControllerHFlip() {
    int[][][] flip = {{{250, 59, 132}, {255, 62, 62}, {255, 63, 63}},
        {{253, 62, 75}, {210, 78, 251}, {240, 58, 220}}};
    String s = "load res/smallTest.ppm two horizontal-flip two flip save res/hflip.ppm flip q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/hflip.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("flip"), flip);
  }

  @Test
  public void testControllerBrightness() {
    int[][][] bright = {{{255, 73, 73}, {255, 72, 72}, {255, 69, 142}},
        {{250, 68, 230}, {220, 88, 255}, {255, 72, 85}}};
    String s = "load res/smallTest.ppm two brightness 10 two bright save res/bright.ppm bright q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/bright.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("bright"), bright);
  }

  @Test
  public void testControllerValue() {
    int[][][] val = {{{255, 255, 255}, {255, 255, 255}, {250, 250, 250}},
        {{240, 240, 240}, {251, 251, 251}, {253, 253, 253}}};
    String s = "load res/smallTest.ppm two value-component two val save res/val.ppm val q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/val.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("val"), val);
  }

  @Test
  public void testControllerIntensity() {
    int[][][] ints = {{{127, 127, 127}, {126, 126, 126}, {147, 147, 147}},
        {{172, 172, 172}, {179, 179, 179}, {130, 130, 130}}};
    String s = "load res/smallTest.ppm two intensity-component two ints save res/ints.ppm ints q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/ints.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("ints"), ints);
  }

  @Test
  public void testControllerLuma() {
    int[][][] luma = {{{103, 103, 103}, {103, 103, 103}, {104, 104, 104}},
        {{108, 108, 108}, {118, 118, 118}, {103, 103, 103}}};
    String s = "load res/smallTest.ppm two luma-component two luma save res/luma.ppm luma q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/luma.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("luma"), luma);
  }

  @Test
  public void testControllerRed() {
    int[][][] red = {{{255, 255, 255}, {255, 255, 255}, {250, 250, 250}},
        {{240, 240, 240}, {210, 210, 210}, {253, 253, 253}}};
    String s = "load res/smallTest.ppm two red-component two red save res/red.ppm red q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/red.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("red"), red);
  }

  @Test
  public void testControllerGreen() {
    int[][][] green = {{{63, 63, 63}, {62, 62, 62}, {59, 59, 59}},
        {{58, 58, 58}, {78, 78, 78}, {62, 62, 62}}};
    String s = "load res/smallTest.ppm two green-component two green save res/green.ppm green q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/green.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("green"), green);
  }

  @Test
  public void testControllerBlue() {
    int[][][] blue = {{{63, 63, 63}, {62, 62, 62}, {132, 132, 132}},
        {{220, 220, 220}, {251, 251, 251}, {75, 75, 75}}};
    String s = "load res/smallTest.ppm two blue-component two blue save res/blue.ppm blue q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/blue.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("blue"), blue);
  }

  @Test
  public void testControllerSepia() {
    int[][][] sepia = {{{160, 142, 111}, {159, 141, 110}, {168, 149, 116}},
        {{180, 160, 125}, {189, 168, 131}, {161, 143, 111}}};
    String s = "load res/smallTest.ppm newImg sepia newImg sepiaImg save res/sepia.ppm sepiaImg q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/sepia.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("sepiaImg"), sepia);
  }

  @Test
  public void testControllerBlur() {
    int[][][] blur = {{{13, 4, 15}, {41, 12, 35}, {44, 11, 24}},
        {{26, 9, 31}, {83, 26, 71}, {89, 24, 49}}};
    String s = "load res/smallTest.ppm newImg blur newImg blurImg save res/blur.ppm blurImg q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/blur.ppm");
    assertTrue(saved.exists());
    System.out.print(Arrays.deepToString(model.getImage("blurImg")));
    assertArrayEquals(model.getImage("blurImg"), blur);
  }

  @Test
  public void testControllerSharpen() {
    int[][][] sharpen = {{{21, 12, 53}, {115, 34, 80}, {115, 34, 80}},
        {{21, 12, 53}, {255, 93, 255}, {255, 81, 137}}};
    String s = "load res/smallTest.ppm newImg sharpen newImg sharpenImg save res/sharpen.ppm "
        + "sharpen"
        + "Img q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertFalse(model.isEmpty());
    File saved = new File("res/sharpen.ppm");
    assertTrue(saved.exists());
    assertArrayEquals(model.getImage("sharpenImg"), sharpen);
  }

  @Test(expected = IllegalStateException.class)
  public void testControllerInvalid() {
    String s = "load res/smallTest.ppm two invalid-cmd two inv q";
    Readable in = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));
    ImageModel model = new ImageModelImpl();
    ImageController controller = new ControllerText(in, model);
    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testAdjustBrightness() {
    int[][][] threeByTwoArray = {{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
        {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};
    int[][][] threeByTwoArrayPlus10 = {{{255, 73, 73}, {255, 72, 72}, {255, 69, 142}},
        {{250, 68, 230}, {220, 88, 255}, {255, 72, 85}}};
    int[][][] threeByTwoArrayMinus15 = {{{240, 48, 48}, {240, 47, 47}, {235, 44, 117}},
        {{225, 43, 205}, {195, 63, 236}, {238, 47, 60}}};

    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands ten = new AdjustBrightness(10, "small", "10");
    ten.edit(model);
    ImageCommands fifteen = new AdjustBrightness(-15, "small", "-15");
    fifteen.edit(model);

    assertArrayEquals(model.getImage("small"), threeByTwoArray);
    assertArrayEquals(model.getImage("10"), threeByTwoArrayPlus10);
    assertArrayEquals(model.getImage("-15"), threeByTwoArrayMinus15);
  }

  @Test
  public void testGreyscaleRed() {
    int[][][] red = {{{255, 255, 255}, {255, 255, 255}, {250, 250, 250}},
        {{240, 240, 240}, {210, 210, 210}, {253, 253, 253}}};

    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands cmd = new GreyscaleRed("small", "red");
    cmd.edit(model);

    assertArrayEquals(model.getImage("red"), red);
  }

  @Test
  public void testGreyscaleGreen() {
    int[][][] green = {{{63, 63, 63}, {62, 62, 62}, {59, 59, 59}},
        {{58, 58, 58}, {78, 78, 78}, {62, 62, 62}}};

    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands cmd = new GreyscaleGreen("small", "green");
    cmd.edit(model);

    assertArrayEquals(model.getImage("green"), green);
  }

  @Test
  public void testGreyscaleBlue() {
    int[][][] blue = {{{63, 63, 63}, {62, 62, 62}, {132, 132, 132}},
        {{220, 220, 220}, {251, 251, 251}, {75, 75, 75}}};

    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands cmd = new GreyscaleBlue("small", "blue");
    cmd.edit(model);

    assertArrayEquals(model.getImage("blue"), blue);
  }

  @Test
  public void testGreyscaleValue() {
    int[][][] val = {{{255, 255, 255}, {255, 255, 255}, {250, 250, 250}},
        {{240, 240, 240}, {251, 251, 251}, {253, 253, 253}}};

    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands cmd = new GreyscaleValue("small", "val");
    cmd.edit(model);

    assertArrayEquals(model.getImage("val"), val);
  }

  @Test
  public void testGreyscaleLuma() {
    int[][][] luma = {{{103, 103, 103}, {103, 103, 103}, {104, 104, 104}},
        {{108, 108, 108}, {118, 118, 118}, {103, 103, 103}}};

    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands cmd = new GreyscaleLuma("small", "luma");
    cmd.edit(model);

    assertArrayEquals(model.getImage("luma"), luma);
  }

  @Test
  public void testGreyscaleIntensity() {
    int[][][] ints = {{{127, 127, 127}, {126, 126, 126}, {147, 147, 147}},
        {{172, 172, 172}, {179, 179, 179}, {130, 130, 130}}};

    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands cmd = new GreyscaleIntensity("small", "ints");
    cmd.edit(model);

    assertArrayEquals(model.getImage("ints"), ints);
  }

  @Test
  public void testFlipVertical() {
    int[][][] flip = {{{240, 58, 220}, {210, 78, 251}, {253, 62, 75}},
        {{255, 63, 63}, {255, 62, 62}, {250, 59, 132}}};

    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands cmd = new VerticalFlipImage("small", "flip");
    cmd.edit(model);

    assertArrayEquals(model.getImage("flip"), flip);
  }

  @Test
  public void testFlipHorizontal() {
    int[][][] flip = {{{250, 59, 132}, {255, 62, 62}, {255, 63, 63}},
        {{253, 62, 75}, {210, 78, 251}, {240, 58, 220}}};

    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands cmd = new HorizontalFlipImage("small", "flip");
    cmd.edit(model);

    assertArrayEquals(model.getImage("flip"), flip);
  }

  @Test
  public void testLoad() {
    int[][][] small = {{{255, 63, 63}, {255, 62, 62}, {250, 59, 132}},
        {{240, 58, 220}, {210, 78, 251}, {253, 62, 75}}};
    ImageModel model = new ImageModelImpl();
    assertTrue(model.isEmpty());
    ImageCommands cmd = new LoadImage("res/smallTest.ppm", "small");
    cmd.edit(model);
    assertFalse(model.isEmpty());
    assertArrayEquals(model.getImage("small"), small);
  }

  @Test
  public void testSave() {
    ImageModel model = new ImageModelImpl("res/smallTest.ppm", "small");
    ImageCommands save = new SaveImage("res/saved.ppm", "small");
    save.edit(model);
    ImageCommands load = new LoadImage("res/saved.ppm", "opened");
    load.edit(model);
    File saved = new File("res/saved.ppm");
    assertTrue(saved.exists());

    assertArrayEquals(model.getImage("small"), model.getImage("opened"));

  }
}