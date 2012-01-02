package com.ashkit.digital;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * This code is free for any purpose of use. Just Keep this comment block as it is
 * and It will be nice if you give me credit, where you used this (not mandatory.)
 * 
 * @author Ashik uddin Ahmad
 * email: ashikcu@gmail.com
 * 
 * other contributors:
 * 1. name: enhancement-description
 * 2. 
 */
public class AKDigit {
	
	static final char UL = 0; // UP-LEFT
	static final char DL = 1; // DOWN-LEFT
	static final char UR = 2; // UP-RIGHT
	static final char DR = 3; // DOWN-RIGHT
	static final char U  = 4; // UP
	static final char M  = 5; // MID
	static final char D  = 6; // DOWN
	static final char DOT1 = 7;
	static final char DOT2 = 8;
	
	private static final int noOfPoints = 6;
	private static final int noOfBars = 9;
	
	private int xPoints[][];
	private int yPoints[][];
	
	private Color fg;
	private boolean visible;
	private boolean decimal;
	private int value;
	private Point p; //position
	private float th; // thick
	private float g; // gap
	private int italic; // measure of bend in degrees (default: 0)
	private float r;
	
	/**
	 * @param left Digits x position in parents coordinate
	 * @param top Digits y position in parents coordinate
	 * @param th thickness of the digit-lines segment. actual lines are 4-times thick
	 * @param g gap between digit-lines
	 * @param color Foreground color for the digit
	 */
	public AKDigit(int left, int top, float th, float g, Color color){
		p = new Point(left, top);
		this.th = th;
		this.g = g;
		fg = color;
		italic = 0;
		r = 0;
		
		visible = true;
		decimal = false;
		
		xPoints = new int[noOfBars][noOfPoints];
		yPoints = new int[noOfBars][noOfPoints];
		
		initPoints();
	}
	
	/**
	 * @param left Digits x position in parents coordinate
	 * @param top Digits y position in parents coordinate
	 * @param th thickness of the digit-lines segment. actual lines are 4-times thick
	 * @param g gap between digit-lines
	 */
	public AKDigit(int left, int top, float th, float g){
		this(left, top, th, g, Color.GRAY);
	}
	
	/**
	 * @param d Char to be set in the digit e.g. '1', 'A' etc. 
	 */
	public void setDigit(int d){ value = d; }
	
	/**
	 * @param visible visibility status
	 */
	public void setVisible(boolean visible){ this.visible = visible; }
	
	/**
	 * @param decimal shows/not the decimal at right of the digit 
	 */
	public void setDecimal(boolean decimal){ this.decimal = decimal; }
	
	/**
	 * @param c Foreground color
	 */
	public void setColor(Color c){fg = c;}
	
	/**
	 * @param x Digits x position in parent coordinate
	 * @param y Digits y position in parent coordinate
	 */
	public void setPosition(int x, int y)
	{
		p.x = x;
		p.y = y;
		initPoints();
	}
	
	/**
	 * @param th thickness-parameter
	 * @param g g-parameter
	 */
	public void setSizeParams(float th, float g)
	{
		this.th = th;
		this.g = g;
		initPoints();
	}

	/**
	 * @return actual width of the digit
	 */
	public int getWidth(){ return Math.round (20*th + 2*g); }

	/**
	 * @return actual height of the digit
	 */
	public int getHeight(){ return Math.round (34*th + 4*g); }

	/**
	 * @return the italic
	 */
	public int getItalic() {
		return italic;
	}

	/**
	 * @param italic the italic to set (use 0-45)
	 */
	public void setItalic(int italic) {
		if(italic < 0 || italic > 45)
		this.italic = italic;
		
		r = (float) Math.atan(italic*Math.PI/180.0);
		
		for(int i = 0; i<noOfBars; i++)
			for(int j = 0; j<noOfPoints; j++)
				xPoints[i][j] += Math.round(yPoints[i][j]*r);

	}

	void paint(Graphics gr)
	{
		if (visible) {
			gr.setColor(fg);

			for (char i = 0; i < charSet.length; i++) {
				if (value == charSet[i][0]) {
					for (int j = 1; j < charSet[i].length; j++) {
						int bar = charSet[i][j];
						gr.fillPolygon(xPoints[bar], yPoints[bar], noOfPoints);
					}
					break;
				}
			}

			if (decimal)
				gr.fillOval(p.x + Math.round(18 * th + 2 * g + getHeight()*r), p.y + Math.round(30 * th + 4 * g),
						Math.round(4 * th), Math.round(4 * th));
		}
	}
	
	/**
	 * @return position
	 */
	public Point getPosition() {
		return p;
	}
	
	public float getThick() {
		return th;
	}
	
	public boolean isDecimal() {
		return decimal;
	}
	
	protected int getItalicWidth() {
		return Math.round (getHeight()*r);
	}

	protected void initPoints() {
		xPoints[UL][0] = Math.round (3*th);
		xPoints[UL][1] = Math.round (6*th);
		xPoints[UL][2] = Math.round (6*th);
		xPoints[UL][3] = Math.round (3*th);
		xPoints[UL][4] = Math.round (2*th);
		xPoints[UL][5] = Math.round (2*th);

		xPoints[DL][0] = Math.round (3*th);
		xPoints[DL][1] = Math.round (6*th);
		xPoints[DL][2] = Math.round (6*th);
		xPoints[DL][3] = Math.round (3*th);
		xPoints[DL][4] = Math.round (2*th);
		xPoints[DL][5] = Math.round (2*th);

		xPoints[UR][0] = Math.round (17*th+2*g);
		xPoints[UR][1] = Math.round (18*th+2*g);
		xPoints[UR][2] = Math.round (18*th+2*g);
		xPoints[UR][3] = Math.round (17*th+2*g);
		xPoints[UR][4] = Math.round (14*th+2*g);
		xPoints[UR][5] = Math.round (14*th+2*g);

		xPoints[DR][0] = Math.round (17*th+2*g);
		xPoints[DR][1] = Math.round (18*th+2*g);
		xPoints[DR][2] = Math.round (18*th+2*g);
		xPoints[DR][3] = Math.round (17*th+2*g);
		xPoints[DR][4] = Math.round (14*th+2*g);
		xPoints[DR][5] = Math.round (14*th+2*g);

		xPoints[U][0] = Math.round (3*th+g);
		xPoints[U][1] = Math.round (4*th+g);
		xPoints[U][2] = Math.round (16*th+g);
		xPoints[U][3] = Math.round (17*th+g);
		xPoints[U][4] = Math.round (14*th+g);
		xPoints[U][5] = Math.round (6*th+g);

		xPoints[M][0] = Math.round (3*th+g);
		xPoints[M][1] = Math.round (5*th+g);
		xPoints[M][2] = Math.round (15*th+g);
		xPoints[M][3] = Math.round (17*th+g);
		xPoints[M][4] = Math.round (15*th+g);
		xPoints[M][5] = Math.round (5*th+g);

		xPoints[D][0] = Math.round (3*th+g);
		xPoints[D][1] = Math.round (6*th+g);
		xPoints[D][2] = Math.round (14*th+g);
		xPoints[D][3] = Math.round (17*th+g);
		xPoints[D][4] = Math.round (16*th+g);
		xPoints[D][5] = Math.round (4*th+g);

		xPoints[DOT1][0] = Math.round (8*th+g);
		xPoints[DOT1][1] = Math.round (12*th+g);
		xPoints[DOT1][2] = Math.round (12*th+g);
		xPoints[DOT1][3] = Math.round (10*th+g);
		xPoints[DOT1][4] = Math.round (8*th+g);
		xPoints[DOT1][5] = Math.round (8*th+g);

		xPoints[DOT2][0] = Math.round (10*th+g);
		xPoints[DOT2][1] = Math.round (12*th+g);
		xPoints[DOT2][2] = Math.round (12*th+g);
		xPoints[DOT2][3] = Math.round (8*th+g);
		xPoints[DOT2][4] = Math.round (8*th+g);
		xPoints[DOT2][5] = Math.round (10*th+g);


		yPoints[UL][0] = Math.round (3*th+g);
		yPoints[UL][1] = Math.round (6*th+g);
		yPoints[UL][2] = Math.round (14*th+g);
		yPoints[UL][3] = Math.round (17*th+g);
		yPoints[UL][4] = Math.round (16*th+g);
		yPoints[UL][5] = Math.round (4*th+g);

		yPoints[DL][0] = Math.round (17*th+3*g);
		yPoints[DL][1] = Math.round (20*th+3*g);
		yPoints[DL][2] = Math.round (28*th+3*g);
		yPoints[DL][3] = Math.round (31*th+3*g);
		yPoints[DL][4] = Math.round (30*th+3*g);
		yPoints[DL][5] = Math.round (18*th+3*g);

		yPoints[UR][0] = Math.round (3*th+g);
		yPoints[UR][1] = Math.round (4*th+g);
		yPoints[UR][2] = Math.round (16*th+g);
		yPoints[UR][3] = Math.round (17*th+g);
		yPoints[UR][4] = Math.round (14*th+g);
		yPoints[UR][5] = Math.round (6*th+g);

		yPoints[DR][0] = Math.round (17*th+3*g);
		yPoints[DR][1] = Math.round (18*th+3*g);
		yPoints[DR][2] = Math.round (30*th+3*g);
		yPoints[DR][3] = Math.round (31*th+3*g);
		yPoints[DR][4] = Math.round (28*th+3*g);
		yPoints[DR][5] = Math.round (20*th+3*g);

		yPoints[U][0] = Math.round (3*th);
		yPoints[U][1] = Math.round (2*th);
		yPoints[U][2] = Math.round (2*th);
		yPoints[U][3] = Math.round (3*th);
		yPoints[U][4] = Math.round (6*th);
		yPoints[U][5] = Math.round (6*th);

		yPoints[M][0] = Math.round (17*th+2*g);
		yPoints[M][1] = Math.round (15*th+2*g);
		yPoints[M][2] = Math.round (15*th+2*g);
		yPoints[M][3] = Math.round (17*th+2*g);
		yPoints[M][4] = Math.round (19*th+2*g);
		yPoints[M][5] = Math.round (19*th+2*g);

		yPoints[D][0] = Math.round (31*th+4*g);
		yPoints[D][1] = Math.round (28*th+4*g);
		yPoints[D][2] = Math.round (28*th+4*g);
		yPoints[D][3] = Math.round (31*th+4*g);
		yPoints[D][4] = Math.round (32*th+4*g);
		yPoints[D][5] = Math.round (32*th+4*g);

		yPoints[DOT1][0] = Math.round (6*th+g);
		yPoints[DOT1][1] = Math.round (6*th+g);
		yPoints[DOT1][2] = Math.round (12*th+g);
		yPoints[DOT1][3] = Math.round (14*th+g);
		yPoints[DOT1][4] = Math.round (12*th+g);
		yPoints[DOT1][5] = Math.round (12*th+g);

		yPoints[DOT2][0] = Math.round (19*th+3*g);
		yPoints[DOT2][1] = Math.round (21*th+3*g);
		yPoints[DOT2][2] = Math.round (28*th+3*g);
		yPoints[DOT2][3] = Math.round (28*th+3*g);
		yPoints[DOT2][4] = Math.round (21*th+3*g);
		yPoints[DOT2][5] = Math.round (19*th+3*g);

		for(int i = 0; i<noOfBars; i++)
			for(int j = 0; j<noOfPoints; j++)
			{
				xPoints[i][j] += p.x + yPoints[i][j]*r;
				yPoints[i][j] += p.y;
			}
	}
	
	static final char charSet[][] = {
										{'-',M},
										{'0',UL,DL,UR,DR,U,D},
										{'1',UR,DR},
										{'2',DL,UR,U,M,D},
										{'3',UR,DR,U,M,D},
										{'4',UL,UR,DR,M},
										{'5',UL,DR,U,M,D},
										{'6',UL,DL,DR,U,M,D},
										{'7',UL,UR,DR,U},
										{'8',UL,DL,UR,DR,U,M,D},
										{'9',UL,UR,DR,U,M,D},
										{'A',UL,DL,UR,DR,U,M},
										{'B',UL,DL,DR,M,D},
										{'C',UL,DL,U,D},
										{'D',DL,UR,DR,M,D},
										{'E',UL,DL,U,M,D},
										{'F',UL,DL,U,M},
										{'G',UL,DL,DR,U,D},
										{'H',UL,DL,DR,M},
										{'I',DR},
										{'J',UR,DR,D},
										{'L',UL,DL,D},
										{'M',UL,UR,DL,DR,U,DOT1},
										{'N',DL,DR,M},
										{'O',DL,DR,M,D},
										{'P',UL,DL,UR,U,M},
										{'R',DL,M},
										{'S',UL,DR,U,M,D},
										{'T',UL,DL,M,D},
										{'U',UL,DL,UR,DR,D},
										{'W',UL,UR,DL,DR,D,DOT2},
										{'Y',UL,UR,DR,M,D},
										{'Z',DL,UR,U,M,D},
										{':',DOT1,DOT2}
									};

}
