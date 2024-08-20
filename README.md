# ATTI - Add Image To Text

## Warning

DO NOT USE THIS IN PRODUCTION. I have not tested the library well because it is just for a side project.


## How to use

```java
Atti.addTextToImage(Graphics g, String yourText, awt.Rectangle textBounds, Font font, Color textColor);
```

```java
import java.awt.*;

class Main {
    public static void main(String[] args) {
        // Load your image
        BufferedImage image = ImageIO.read(new File("./my_image.jpeg"));
        // Get image graphics
        Graphics graphics = image.getGraphics();
        // The text you want on the image
        String text = "your text";
        // Set bounds for the text. The text will decrease, so it will fit in the rectangle. 
        Rectangle textBounds = new Rectangle(x, y, width, height);
        // Choose your font. The font size will decrease when characters overflow the bounds
        Font font = new Font("Arial", Font.BOLD, 20);
        // Choose text color
        Color color = Color.your_color;
        
        Atti.addTextToImage(graphics, text, textBounds, font, color);
        ImageIO.write(image, "jpeg", new File("./out.jpeg"));
    }
}
```

You can have multiple calls to Atti.addTextToImage for one image to add different texts on an image with different properties.
Just call the function before your save your Image and pass your previous Graphics to it.

```java
import java.awt.*;

class Main {
    public static void main(String[] args) {
        BufferedImage image = ImageIO.read(new File("./my_image.jpeg"));
        Graphics graphics = image.getGraphics();
        
        Atti.addTextToImage(graphics, "First Text", new Rectangle(100, 200, 150, 150), new Font("Arial", Font.BOLD, 20), Color.RED);
        Atti.addTextToImage(graphics, "Second Text", new Rectangle(200, 500, 800, 355), new Font("Arial", Font.ITALIC, 90), Color.BLACK);
        Atti.addTextToImage(graphics, "Third Text", new Rectangle(800, 700, 300, 60), new Font("Sans Serif", Font.PLAIN, 30), Color.GREEN);
        
        ImageIO.write(image, "jpeg", new File("./out.jpeg"));
    }
}
```
