import java.util.HashSet;
import java.util.Iterator;

public strictfp abstract class Flyer<T extends Flyer<T,ID,ITERATOR>, ID extends Flyer.FlyerId<ID>, ITERATOR extends Iterator<ID>> extends Targetter<T>
{
	//these two constants are used by loadData for its second argument, to
	//instruct it whether or not to call loadMoreData from the FlyerDataSaver (or subclass thereof)
	//which it finds
	final static int ONLY_FLYER_DATA=0;
	final static int ALL_DATA=1;
	
	ShipType type;
	GSystem location;
	String name;
	ID id;
	Player owner;
	
	HashSet<Targetter<?>> aggressors; //all the ships/ missiles targeting this
	
	Destination<?> destination;
	//save in case SystemPainter wants to paint crosshairs
	double dest_x_coord;
	double dest_y_coord;
	
	//for Targetable
	int damage;
	
	double pos_x; //pos_x and pos_y indicate where in the system the ship is located
	double pos_y;
	double direction;
	double speed;
	protected long time;
	FlyerAI current_flying_AI;
	boolean is_alive;
	
	FlyerDataSaverControl<T, ? extends FlyerDataSaver<T> > data_control;
	
	//subclasses are responsible for instantiating data_control
	public Flyer(String nm, ShipType st)
	{
		name=nm;
		type=st;
		
		damage=0;
		is_alive=true;
		aggressors = new HashSet<Targetter<?>>();
		destination=null;
		target=null;
	}
	
	public Flyer(){}
	
	@Override
	public void handleDataNotSaved(long t){removeFromGame(t);}
	public abstract void removeFromGame(long t);
	
	public boolean isAlive(){return is_alive;}
	public boolean isAliveAt(long t) throws DataSaverControl.DataNotYetSavedException
	{
		if(t == time)
			return isAlive();
		else
		{
			try
			{
				return data_control.saved_data[data_control.getIndexForTime(t)].is_alive;
			}
			catch(DataSaverControl.DataNotYetSavedException e)
			{
				if(!data_control.saved_data[data_control.getPreviousIndex(data_control.index)].is_alive)
					return false;
				/*else if (e.stepback == 0) //not necessary due to first if in the function
					return true;*/
				else
					throw e; //TODO: in Java 1.7, this can be changed to throw;
			}
		}
	}
	
	public abstract boolean update(long time, ITERATOR iterator);
	
	//ship physics functions
	
	//moves the ship one time_granularity.  this is a separate function so that all ships updates can be stepped through 1 by 1.
	protected void moveIncrement()
	{
		//change position
		pos_x += speed*GalacticStrategyConstants.TIME_GRANULARITY*Math.cos(direction);
		pos_y += speed*GalacticStrategyConstants.TIME_GRANULARITY*Math.sin(direction);
		
		double desired_direction_chng;

		switch(current_flying_AI.directionType())
		{
			case FlyerAI.ABS_DIRECTION:
				desired_direction_chng = current_flying_AI.calcDesiredDirection() - direction;
				if(desired_direction_chng > Math.PI)
					desired_direction_chng -= 2.0*Math.PI;
				else if(desired_direction_chng < -Math.PI)
					desired_direction_chng += 2.0*Math.PI;
				break;
			case FlyerAI.REL_DIRECTION:
				desired_direction_chng = current_flying_AI.calcDesiredDirection();
				break;
			default:
				System.out.println("directionType not supported by Flyer.moveIncrement");
				return; //to avoid that desired_direction_chng might not be initialized.
				//break;
		}
		
		double desired_speed = current_flying_AI.calcDesiredSpeed(desired_direction_chng);
		
		double accel = getAccel();
		
		//change speed
		if(desired_speed < speed)
			speed = Math.max(Math.max(speed - accel*GalacticStrategyConstants.TIME_GRANULARITY, desired_speed), 0.0d);
		else
		{
			if(enforceSpeedCap()) //false allows ships to exceed their speed limitations
				speed = Math.min(Math.min(speed + accel*GalacticStrategyConstants.TIME_GRANULARITY, desired_speed), type.max_speed);
			else
				speed = Math.min(speed + accel*GalacticStrategyConstants.TIME_GRANULARITY, desired_speed);
		}
		
		//change direction
		double actual_chng = Math.min(Math.abs(desired_direction_chng), Math.abs(type.max_angular_vel*GalacticStrategyConstants.TIME_GRANULARITY)); //finds the absolute value of the amount the direction changes
		if(desired_direction_chng > 0)
			direction += actual_chng;
		else
			direction -= actual_chng;
		
		if(direction > Math.PI)
			direction -= 2*Math.PI;
		else if(direction<-Math.PI)
			direction += 2*Math.PI;
	}
	
	protected boolean enforceSpeedCap(){return true;} //overriding this function allows ships to speed up to warp.  note that if it were Private it would not be overrideable
	
	protected double getAccel()
	{
		return type.accel_rate;
	}
	
	public double destinationX()
	{
		dest_x_coord = destination.getXCoord(time);
		return dest_x_coord;
	}
	
	public double destinationY()
	{
		dest_y_coord = destination.getYCoord(time);
		return dest_y_coord;
	}
	
	public double destinationVelX()
	{
		return destination.getXVel(time);
	}
	
	public double destinationVelY()
	{
		return destination.getYVel(time);
	}
	
	//methods to implement destination
	public String imageLoc(){return type.img.img_path;}
	
	public double getXCoord(long t)
	{
		return pos_x;
	}
	
	public double getYCoord(long t)
	{
		return pos_y;
	}
	
	public double getXVel(long t)
	{
		return speed*Math.cos(direction);
	}
	
	public double getYVel(long t)
	{
		return speed*Math.sin(direction);
	}
	
	//for Saveable
	public FlyerDataSaverControl<T, ? extends FlyerDataSaver<T>> getDataControl(){return data_control;}
	
	//for Targetable
	public HashSet<Targetter<?>> getAggressors(){return aggressors;}
	public void addAggressor(Targetter<?> t)
	{
		aggressors.add(t);
	}
	public void removeAggressor(Targetter<?> t)
	{
		aggressors.remove(t);
	}
	
	@Override
	public void addDamage(long t, int d)
	{ //TODO: t is ignored... should it be?
		damage+=d;
		if(damage>=type.hull)
			destroyed();
	}
	
	public ShipType getType(){return type;}
	public void setType(ShipType tp){type=tp;}
	public double getPos_x(){return pos_x;}
	public double getPos_y(){return pos_y;}
	public void setPos_x(double x){pos_x=x;}
	public void setPos_y(double y){pos_y=y;}
	public double getSpeed(){return speed;}
	public double getDirection(){return direction;}
	public void setSpeed(double s){speed=s;}
	public void setDirection(double d){direction=d;}
	final public long getTime(){return time;}
	final public void setTime(long t){time=t;}
	public String getName(){return name;}
	public void setName(String nm){name=nm;}
	public int getDamage(){return damage;}
	public void setDamage(int d){damage=d;}
	public void setId(ID i){id=i;}
	public ID getId(){return id;}
	public Player getOwner() {return owner;}
	public void setOwner(Player p){owner = p;}
	
	public static abstract class FlyerId<T extends FlyerId<T>>
	{
		@Override
		public abstract int hashCode();
		
		@Override
		public abstract boolean equals(Object o);
	}
}