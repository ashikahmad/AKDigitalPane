package com.ashkit.digital;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * AKDigitalPane.java
 * 
 * @author Ashik uddin Ahmad
 * email: ashikcu@gmail.com
 * 
 * TERM OF USE:
 * 		Permission is granteed to use this code, with or without modification,
 * 		in any application. But it will be worth providing that credit is given
 * 		to the original work.
 *
 * This program is distributed in the hope that it will be usefull,
 * but WITHOUT ANY WARRANTY.
 * 
 * PLEASE DO NOT REMOVE THIS COPYRIGHT BLOCK 
 */
public class AKDigitalPane extends JPanel {
	private byte displayLen;
	private AKDigit digits[];
	private String displayString;
	
	private Dimension prefferedDimension;
	
	/**
	 * @param noOfDigts no of digits in display
	 * @param thick thickness parameter
	 * @param gap gap between digit-parts
	 */
	public AKDigitalPane(int noOfDigts, float thick, float gap){
		super();
		displayLen = (byte) noOfDigts;
		digits = new AKDigit[displayLen];
		
		displayString = "";
		int x = Math.round(thick), y=Math.round(thick);
		
		digits[0] = new AKDigit(x, y, thick, gap);
		for (int i=1; i<displayLen; i++)
			digits[i] = new AKDigit(x+i*digits[0].getWidth(), y, thick, gap);
		
		calculateSize();
	}
	
	/**
	 * @param noOfDigts no of digits in display
	 * @param thick thickness parameter
	 * @param gap gap between digit-parts
	 */
	public AKDigitalPane(int noOfDigts, float thick){
		this(noOfDigts, thick, thick);
	}	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		boolean skipped = false;
		for(int i=0, dUsed=0; dUsed<displayLen; i++)
		{
			int place = (displayLen-1)-dUsed;
			char c = (i<displayString.length())? displayString.charAt(displayString.length()-1-i):'\0' ;
			
			if(!skipped && digits[place].isDecimal()) digits[place].setDecimal(false);
			
			if(c == '.'){
				digits[place].setDecimal(true);
				skipped = true;
				continue;
			} else {
				skipped = false;
				dUsed++;
			}
			
			digits[place].setDigit(c);
			digits[place].paint(g);
		}
	}
	
	/**
	 * @param text set text
	 */
	public void setText(String text)
	{
		displayString = text.toUpperCase();
		repaint();
	}

	/**
	 * @return current text
	 */
	public String getText()
	{
		return displayString;
	}

	/**
	 * @param thick thickness parameter
	 * @param gap gap
	 */
	public void setFontParams(float thick, float gap)
	{
		int x = digits[0].getPosition().x;
		int y = digits[0].getPosition().y;

		digits[0].setSizeParams(thick, gap);
		for(int i=0; i<displayLen; i++)
		{
			digits[i].setPosition(x + i*digits[0].getWidth(), y);
			digits[i].setSizeParams(thick, gap);
		}
	}
	
	/**
	 * @param c font-color
	 */
	public void setFontColor(Color c)
	{
		for(int i=0; i<displayLen; i++) {
			digits[i].setColor(c);
		}
		repaint();
	}
	
	public void setItalic(int degree){
		for(int i=0; i<displayLen; i++) {
			digits[i].setItalic(degree);
		}
		repositionDigits();
		calculateSize();
		repaint();
	}
	
	private void repositionDigits() {
		if(digits[0].getItalicWidth() < 0 )
		digits[0].setPosition(digits[0].getPosition().x - digits[0].getItalicWidth(), digits[0].getPosition().y);
		for(int i=0; i<displayLen; i++)
			digits[i].setPosition(digits[0].getPosition().x + i*digits[0].getWidth(), digits[0].getPosition().y);
	}

	@Override
	public Dimension getPreferredSize() {
		return prefferedDimension;
	}
	
	@Override
	public Dimension getMinimumSize() {
		return prefferedDimension;
	}
	
	private void calculateSize(){
		prefferedDimension = new Dimension(digits[0].getWidth()*displayLen+Math.round(4*digits[0].getThick())+Math.abs(digits[0].getItalicWidth()), digits[0].getHeight()+Math.round(4*digits[0].getThick()));
	}
}
