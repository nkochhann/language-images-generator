package Graphwar;

import java.util.Random;

import GraphServer.Constants;

public class Soldier
{
	private int x;
	private int y;
	private double angle;
	
	private boolean alive;	
	private boolean exploding;
	private long timeExplodingStarted;
	private int killPosition;
	
	private String function;
	
	private boolean animating;
	private int animationNum;
	private long timeAnimationStarted;
	private long nextAnimation;
	
	private static Random random = new Random();
	
	public Soldier()
	{
		this.x = 0;
		this.y = 0;
		
		this.angle = 0;
		
		this.alive = false;
		this.exploding = false;
		
		this.function = "";
	}
	
	public Soldier(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		this.angle = 0;
		
		this.alive = true;
		
		this.function = "";
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	public int getKillPosition()
	{
		return killPosition;
	}
	
	public void setKillPosition(int killPosition)
	{
		this.killPosition = killPosition;
	}
	
	public boolean isExploding()
	{
		return exploding;
	}

	public void setExploding(boolean exploding)
	{
		this.exploding = exploding;
		
		if(exploding == true)
		{
			timeExplodingStarted = System.currentTimeMillis();
		}
	}
	
	public long getTimeExploding()
	{
		return System.currentTimeMillis() - timeExplodingStarted;
	}
	
	public String getFunction()
	{
		return function;
	}
	
	public void setFunction(String function)
	{
		this.function = function;
	}
	
	public boolean isAnimating()
	{
		if(animating)
		{
			return true;
		}
		else
		{
			if(System.currentTimeMillis() > nextAnimation)
			{
				animating = true;
				timeAnimationStarted = System.currentTimeMillis();
				animationNum = random.nextInt(Integer.MAX_VALUE);
				return true;
			}
			
			return false;
		}
	}
	
	public int getAnimationNum()
	{
		return animationNum;
	}
	
	public long getAnimationTime()
	{
		return System.currentTimeMillis() - timeAnimationStarted;
	}
	
	public void endAnimation()
	{
		animating = false;
		
		nextAnimation = (long) (System.currentTimeMillis() + Math.abs(random.nextGaussian()*Constants.SOLDIER_ANIMATION_DELAY_STANDARD_DEVIATION + Constants.SOLDIER_ANIMATION_MEAN_VALUE));
	}
	
}