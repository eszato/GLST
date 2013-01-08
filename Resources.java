import java.io.IOException;
import javax.swing.ImageIcon;

public class Resources
{
	public static void preload() throws IOException //GameControl calls this method when it is instantiated
	{
		//load Images
		for(ImageResource r : ImageResource.values())
		{
			// this function may be called multiple times, so this null check speeds things up.
			if (r.image == null)
				r.image = GraphicsUtilities.loadCompatibleImage(r.img_path);
		}
		
		//store metrics from images to ShipTypes
		for(ShipType t : ShipType.values())
		{
			t.width = t.img.getWidth();
			t.height = t.img.getHeight();
			t.dim = Math.max(t.width, t.height);
			
			// null check to avoid constructing extra ImageIcons
			if (t.icon == null)
				t.icon = new ImageIcon(t.img.image);
		}
	}
}
