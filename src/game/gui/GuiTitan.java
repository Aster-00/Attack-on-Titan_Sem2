package game.gui;

import javafx.scene.image.Image;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;

public class GuiTitan extends Image{
	private Titan titan;
	private Image rightStep;
	private Image leftStep;
	

	public GuiTitan(Titan titan, String url) {
		super(url);
		this.titan = titan;
		

	}
	
	public Titan getTitan() {
		return titan;
	}
	public void setTitan(Titan titan) {
		this.titan = titan;
	}

}
