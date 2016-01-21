package helpers;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ImageManipulator {
	public static ImageIcon resizePicture(Image originalPicture, int newWidth, int newHeight, boolean preserveAlpha) {
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, imageType);
		
		Graphics2D g = resizedImage.createGraphics();
		
		if(preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		
		g.drawImage(originalPicture, 0, 0, newWidth, newHeight, null);
		g.dispose();
		
		return new ImageIcon(resizedImage);
	}
}