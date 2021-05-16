 public static void drawHollowRect(int x, int y, int w, int h, int color) {

      Gui.drawHorizontalLine(x, x + w, y, color);
      Gui.drawHorizontalLine(x, x + w, y + h, color);

      Gui.drawVerticalLine(x, y + h, y, color);
      Gui.drawVerticalLine(x + w, y + h, y, color);

  }
