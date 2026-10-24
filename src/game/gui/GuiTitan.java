package game.gui;

import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.Titan;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;

// Cartoon sprite of a titan seen from a 45 degree elevation, facing left (towards the walls).
// Origin is on the ground between the feet, y goes up into negatives.
public class GuiTitan extends Group{
	private static final Color OUTLINE = Color.web("#2b1d16");

	private Titan titan;
	private double s; // base size, everything is drawn relative to it

	// body layout, filled in by layoutBody()
	private double legLen, torsoY, shoulderY, headY;

	public GuiTitan(Titan titan) {
		this.titan = titan;

		if(titan instanceof ColossalTitan) drawColossal();
		else if(titan instanceof ArmoredTitan) drawArmored();
		else if(titan instanceof AbnormalTitan) drawAbnormal();
		else drawPure();
	}

	private void drawPure(){
		s = 18;
		Color skin = Color.web("#f2b48f");
		layoutBody(0.55);
		addShadow();
		addLegs(skin);
		addLimb(s*0.24, shoulderY, s*0.42, shoulderY + s*0.45, skin.darker());
		add(outlined(new Ellipse(0, torsoY, s*0.36, s*0.42), skin));
		// belly button
		add(outlined(new Circle(-s*0.1, torsoY + s*0.12, s*0.025), skin.darker()));
		addHead(skin);
		add(outlined(hair(Color.web("#6b4226")), Color.web("#6b4226")));
		addEyes(s*0.07, s*0.035);
		// goofy grin
		Arc grin = new Arc(-s*0.2, headY + s*0.2, s*0.13, s*0.08, 190, 150);
		grin.setType(ArcType.OPEN);
		grin.setFill(null);
		grin.setStroke(OUTLINE);
		grin.setStrokeWidth(1.2);
		add(grin);
		addLimb(-s*0.2, shoulderY, -s*0.46, shoulderY + s*0.38, skin);
	}

	private void drawAbnormal(){
		s = 16;
		Color skin = Color.web("#f7d3bb");
		// lanky long legs
		layoutBody(0.8);
		addShadow();
		addLegs(skin);
		// flailing arms raised above the head
		addLimb(s*0.22, shoulderY, s*0.55, shoulderY - s*0.45, skin.darker());
		add(outlined(new Ellipse(0, torsoY, s*0.3, s*0.42), skin));
		addHead(skin);
		// wild spiky hair over the top and back of the head
		Polygon spikes = new Polygon();
		for(int i = 0; i <= 10; i++){
			double a = Math.toRadians(-15 - i*18);
			double r = (i % 2 == 0) ? s*0.58 : s*0.36;
			spikes.getPoints().addAll(s*0.05 + Math.cos(a)*r, headY - s*0.05 + Math.sin(a)*r*0.9);
		}
		add(outlined(spikes, Color.web("#3d2b1f")));
		// big googly eyes
		addEyes(s*0.12, s*0.04);
		Ellipse mouth = new Ellipse(-s*0.2, headY + s*0.2, s*0.09, s*0.07);
		add(outlined(mouth, OUTLINE));
		add(outlined(new Ellipse(-s*0.2, headY + s*0.25, s*0.06, s*0.04), Color.web("#e05a6b")));
		addLimb(-s*0.18, shoulderY, -s*0.6, shoulderY - s*0.4, skin);
	}

	private void drawArmored(){
		s = 21;
		Color skin = Color.web("#d99a7c");
		Color armor = Color.web("#e3ddcf");
		layoutBody(0.55);
		addShadow();
		addLegs(armor);
		addLimb(s*0.26, shoulderY, s*0.42, shoulderY + s*0.45, armor.darker());
		add(outlined(new Ellipse(0, torsoY, s*0.4, s*0.44), skin));
		// chest plate
		Polygon chest = new Polygon(
				-s*0.3, torsoY - s*0.3,
				s*0.3, torsoY - s*0.3,
				s*0.22, torsoY + s*0.15,
				0, torsoY + s*0.3,
				-s*0.22, torsoY + s*0.15);
		add(outlined(chest, armor));
		addLine(0, torsoY - s*0.3, 0, torsoY + s*0.3);
		addLine(-s*0.27, torsoY - s*0.05, s*0.27, torsoY - s*0.05);
		addHead(skin);
		add(outlined(hair(Color.web("#f0d27a")), Color.web("#f0d27a")));
		// face plate
		Polygon mask = new Polygon(
				-s*0.4, headY,
				-s*0.3, headY - s*0.22,
				s*0.05, headY - s*0.2,
				s*0.08, headY + s*0.22,
				-s*0.2, headY + s*0.35,
				-s*0.36, headY + s*0.2);
		add(outlined(mask, armor));
		addLine(-s*0.3, headY - s*0.22, -s*0.15, headY + s*0.05);
		addLine(-s*0.2, headY + s*0.35, -s*0.15, headY + s*0.05);
		// glowing eyes
		add(outlined(new Ellipse(-s*0.27, headY + s*0.02, s*0.06, s*0.035), Color.WHITE));
		add(outlined(new Ellipse(-s*0.06, headY + s*0.02, s*0.05, s*0.035), Color.WHITE));
		addLimb(-s*0.22, shoulderY, -s*0.46, shoulderY + s*0.38, armor);
		// shoulder plate
		add(outlined(new Ellipse(-s*0.22, shoulderY, s*0.18, s*0.13), armor));
	}

	private void drawColossal(){
		s = 30;
		Color muscle = Color.web("#c0392b");
		Color darkMuscle = Color.web("#8e2a20");
		layoutBody(0.5);
		addShadow();
		addLegs(muscle);
		addLimb(s*0.28, shoulderY, s*0.45, shoulderY + s*0.45, darkMuscle);
		add(outlined(new Ellipse(0, torsoY, s*0.42, s*0.44), muscle));
		// muscle striations
		for(int i = -2; i <= 2; i++)
			addLine(i*s*0.1, torsoY - s*0.32, i*s*0.13, torsoY + s*0.3);
		addHead(muscle);
		// bald darker skull top
		add(outlined(hair(darkMuscle), darkMuscle));
		// row of teeth
		Polygon teeth = new Polygon(
				-s*0.36, headY + s*0.12,
				-s*0.02, headY + s*0.12,
				-s*0.04, headY + s*0.26,
				-s*0.3, headY + s*0.26);
		add(outlined(teeth, Color.web("#f5ecd7")));
		for(int i = 1; i <= 3; i++)
			addLine(-s*0.36 + i*s*0.085, headY + s*0.12, -s*0.36 + i*s*0.085, headY + s*0.26);
		add(outlined(new Circle(-s*0.27, headY - s*0.04, s*0.05), Color.web("#ffe08a")));
		add(outlined(new Circle(-s*0.08, headY - s*0.04, s*0.045), Color.web("#ffe08a")));
		addLimb(-s*0.24, shoulderY, -s*0.48, shoulderY + s*0.38, muscle);
		// steam puffs
		double[][] puffs = {{s*0.3, headY - s*0.35, 0.16}, {s*0.5, torsoY - s*0.2, 0.12}, {-s*0.4, headY - s*0.4, 0.13}, {s*0.4, torsoY + s*0.3, 0.1}};
		for(double[] p : puffs)
			add(new Circle(p[0], p[1], s*p[2], Color.rgb(255, 255, 255, 0.55)));
	}

	private void layoutBody(double legRatio){
		legLen = s*legRatio;
		torsoY = -(legLen + s*0.32);
		shoulderY = torsoY - s*0.25;
		headY = torsoY - s*0.68;
	}

	// small soft circle on the ground, squashed since it is seen at 45 degrees
	private void addShadow(){
		Ellipse shadow = new Ellipse(0, 0, s*0.45, s*0.45*Math.sin(Math.toRadians(45)));
		shadow.setFill(Color.rgb(0, 0, 0, 0.4));
		shadow.setEffect(new GaussianBlur(3));
		add(shadow);
	}

	// legs mid step, back leg darker since it is further away
	private void addLegs(Color skin){
		addLimb(s*0.1, -legLen, s*0.2, -s*0.08, skin.darker());
		add(outlined(new Ellipse(s*0.14, -s*0.06, s*0.13, s*0.07), skin.darker()));
		addLimb(-s*0.1, -legLen, -s*0.2, -s*0.04, skin);
		add(outlined(new Ellipse(-s*0.27, -s*0.03, s*0.14, s*0.07), skin));
	}

	private void addHead(Color skin){
		// ear on the back side of the head
		add(outlined(new Ellipse(s*0.3, headY + s*0.02, s*0.08, s*0.11), skin));
		add(outlined(new Circle(0, headY, s*0.4), skin));
	}

	// cap over the top and back of the head
	private Arc hair(Color c){
		Arc hair = new Arc(0, headY, s*0.42, s*0.42, -20, 190);
		hair.setType(ArcType.CHORD);
		return hair;
	}

	// eyes on the left side of the face, the nearer one slightly bigger
	private void addEyes(double eyeRadius, double pupilRadius){
		double[][] eyes = {{-s*0.27, 1.0}, {-s*0.05, 0.85}};
		for(double[] e : eyes){
			add(outlined(new Circle(e[0], headY + s*0.08, eyeRadius*e[1]), Color.WHITE));
			add(new Circle(e[0] - eyeRadius*0.35, headY + s*0.08, pupilRadius*e[1], OUTLINE));
		}
	}

	// rounded limb with a hand/foot sized blob at the end
	private void addLimb(double x1, double y1, double x2, double y2, Color skin){
		Line outline = new Line(x1, y1, x2, y2);
		outline.setStroke(OUTLINE);
		outline.setStrokeWidth(s*0.22 + 2.4);
		outline.setStrokeLineCap(StrokeLineCap.ROUND);
		Line limb = new Line(x1, y1, x2, y2);
		limb.setStroke(skin);
		limb.setStrokeWidth(s*0.22);
		limb.setStrokeLineCap(StrokeLineCap.ROUND);
		add(outline);
		add(limb);
	}

	private void addLine(double x1, double y1, double x2, double y2){
		Line l = new Line(x1, y1, x2, y2);
		l.setStroke(Color.rgb(43, 29, 22, 0.6));
		l.setStrokeWidth(1);
		add(l);
	}

	private Shape outlined(Shape shape, Color fill){
		shape.setFill(fill);
		shape.setStroke(OUTLINE);
		shape.setStrokeWidth(1.2);
		return shape;
	}

	private void add(Shape shape){
		getChildren().add(shape);
	}

	public Titan getTitan() {
		return titan;
	}
	public void setTitan(Titan titan) {
		this.titan = titan;
	}

}
