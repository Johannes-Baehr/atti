package io.github.johannes_baehr.atti;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Atti {
    public static void addTextToImage(Graphics g, String text, Rectangle stringBounds, Font font, Color color) {
        int fontSize = font.getSize();
        char[] chars = text.toCharArray();
        ArrayList<StringBuilder> lines = new ArrayList<>();

        // i is -1 so the loop starts the first iteration with i = 0
        int i = -1;

        while (true) {
            Font textFont = new Font(font.getFontName(), font.getStyle(), fontSize);

            g.setFont(textFont);
            g.setColor(color);

            // check if there is no line
            if (lines.isEmpty()) {
                lines.add(new StringBuilder());
            }

            StringBuilder lastLine = lines.get(lines.size() - 1);
            i += 1;

            // are all characters from the text already used?
            if (i > chars.length - 1) {
                FontMetrics metrics = g.getFontMetrics(textFont);
                // get string bounds for the last line
                Rectangle2D stringRect = metrics.getStringBounds(lastLine.toString(), g);

                drawAllLines(g, lines, stringRect, stringBounds);
                break;
            }

            // add new character to the current line
            lastLine.append(chars[i]);
            FontMetrics metrics = g.getFontMetrics(textFont);
            Rectangle2D stringRect = metrics.getStringBounds(lastLine.toString(), g);

            // check if there is room for a new line
            if (newLineSpace(stringRect, stringBounds, lines.size() + 1)) {
                lines.add(new StringBuilder());
                // distribute characters so they warp at the space character only
                wrapSpaceDistribution(lines);
            }

            // check if string exceeds user given bounds
            if (stringRect.getWidth() > stringBounds.getWidth()) {
                // decrease font size by 10 percent
                float fontResult = (float) (fontSize * 0.1);
                fontSize -= (int) fontResult;
                continue;
            }

            // if there are characters left to add, loop again
            if (countAllChars(lines) != chars.length) {
                continue;
            }

            drawAllLines(g, lines, stringRect, stringBounds);
            break;
        }
    }
    private static void drawAllLines(Graphics g, ArrayList<StringBuilder> lines, Rectangle2D stringRect, Rectangle stringBounds) {
        for (int i = 0; i <= lines.size() - 1; i++) {
            int y = (int) stringBounds.getY() - (int) stringRect.getY();
            int yShitftedLine = y + (int) stringRect.getHeight() * i;
            g.drawString(lines.get(i).toString(), (int) stringBounds.getX(), yShitftedLine);
        }
    }
    private static int countAllChars(ArrayList<StringBuilder> lines) {
        int count = 0;
        for (StringBuilder line : lines) {
            count += line.length();
        }

        return count;
    }
    private static boolean newLineSpace(Rectangle2D stringRect, Rectangle stringBounds, int nthLine) {
        return (stringBounds.getY() + stringRect.getHeight() * nthLine <= stringBounds.getY() + stringBounds.getHeight());
    }
    private static void wrapSpaceDistribution(ArrayList<StringBuilder> lines) {
        StringBuilder lastLine = lines.get(lines.size() - 1);

        // check if a space is the first char of the last line
        // -> don't wrap the entire word that was in the previous line
        if (!lastLine.isEmpty() && lastLine.charAt(0) == ' ') {
            lastLine.deleteCharAt(0);
            return;
        }

        StringBuilder nextToLastLine = lines.get(lines.size() - 2);
        int indexLastSpace = nextToLastLine.lastIndexOf(" ");

        // if the space on the next to last line is where the line wraps, don't do anything
        if (nextToLastLine.length() < indexLastSpace) return;

        // remove chars from the previous line
        int charsToMove = nextToLastLine.length() - indexLastSpace - 1;
        char[] dst = new char[charsToMove];
        nextToLastLine.getChars(indexLastSpace + 1, nextToLastLine.length(), dst, 0);
        nextToLastLine.delete(indexLastSpace + 1, nextToLastLine.length());

        // add these characters to the current line
        lastLine.append(new String(dst));
    }
}