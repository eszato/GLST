public strictfp class DestinationPoint implements Destination<DestinationPoint>, Describer<DestinationPoint>
{
	double x,y;
	
	public DestinationPoint(double x, double y)
	{
		this.x=x;
		this.y=y;
	}
	
	@Override
	public DestinationPoint retrieveObject(Galaxy g)
	{
		// this function used to "return this".  I am changing this due to
		// problems in the logs I'm using for testing (test case 10) - for
		// some reason, a pair of DestinationPoints in the logs are aliased on
		// one player's computer but not the other, so then alias in the
		// simulated games; making a new DestinationPoint fixes this.  I'm also
		// changing the describer() function so that this aliasing won't get
		// into the logs in the future.
		return new DestinationPoint(x, y);
	}
	
	@Override
	public Describer<DestinationPoint> describer() {
		
		// See comment in retrieveObject
		return new DestinationPoint(x, y);
	}
	
	@Override
	public double getXCoord(long t){return x;}
	@Override
	public double getYCoord(long t){return y;}
	@Override
	public double getXVel(long t){return 0.0d;}
	@Override
	public double getYVel(long t){return 0.0d;}
	
	@Override
	public String imageLoc(){return "images/destinationpoint.jpg";}
	@Override
	public String getName(){return "Point";}
	
	//default constructor, for XML encoding/decoding
	public DestinationPoint(){}
	
	public double getX(){return x;}
	public void setX(double a){x=a;}
	public double getY(){return y;}
	public void setY(double b){y=b;}
}