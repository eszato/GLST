public strictfp abstract class FlyerDataSaverControl<T extends Flyer<T, ?, ?>, S extends FlyerDataSaver<T>> extends DataSaverControl<T,S> {

	public FlyerDataSaverControl(T f, Creator<T,S> c)
	{
		super(f, c);
	}
	
	public int getIndexForTime(long t) throws DataNotYetSavedException
	{
		//System.out.println("t is " + Long.toString(t) + " and game time is " + Long.toString(GameInterface.GC.TC.getTime()));
		
		int stepback=(int) ((saved_data[getPreviousIndex(index)].t-t)/GalacticStrategyConstants.TIME_GRANULARITY + 1);
		stepback += (t%GalacticStrategyConstants.TIME_GRANULARITY != 0) ? 1 : 0;
		
		int indx=-1;
		//System.out.println("load data: t is " + Long.toString(t) + " and time is " + Long.toString(time) + ", so step back... " + Integer.toString(stepback));
		if (stepback>50)
		{
			System.out.println("Error loading ship data: the delay is too long"); //BOOKMARK - how should these errors be dealt with
		}
		else if(stepback <= 0)
		{
			System.out.println("Major consistency error: stepback in getIndexForTime is " + Integer.toString(stepback) + "with t=" + Long.toString(t) + " and time="+Long.toString(the_obj.getTime()));
			throw new DataNotYetSavedException(stepback);
		}
		else
		{
			if (stepback<=index)
				indx=index-stepback;
			else
				indx=index+GalacticStrategyConstants.data_capacity-stepback;			
		}
		
		if (saved_data[indx].t != t)
			throw new RuntimeException("saved_data[" + indx + "].t is " + saved_data[indx].t + " and t is " + t);
		
		return indx;
	}
}
