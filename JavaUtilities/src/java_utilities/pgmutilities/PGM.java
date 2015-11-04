package java_utilities.pgmutilities;

/**
 *
 * @author user
 */
public class PGM
{
  private int width;
  private int height;
  private int max_val;
  private int[] pixels;

  public PGM(int width, int height, int max_val)
  {
      this.width = width;
      this.height = height;
      this.max_val = max_val;
      this.pixels = new int[width*height];
  }

   /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the max_val
     */
    public int getMax_val() {
        return max_val;
    }

    /**
     * @param max_val the max_val to set
     */
    public void setMax_val(int max_val) {
        this.max_val = max_val;
    }

    /**
     * @return the pixels
     */
    public int[] getPixels() {
        return pixels;
    }

    /**
     * @param pixels the pixels to set
     */
    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }
}
