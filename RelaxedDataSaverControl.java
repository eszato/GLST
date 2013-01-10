
/**
 * This class is probably getting the axe soon.  Just moved all it's children
 * to DataSaverControl, as well as promoting the getIndexForTime function from
 * FlyerDataSaverControl.
 */

@Deprecated
public strictfp abstract class RelaxedDataSaverControl<T extends RelaxedSaveable<T>, S extends DataSaver<T> > extends DataSaverControl<T, S> {

	public RelaxedDataSaverControl(T s, Creator<T, S> c) {
		super(s, c);
	}

	@Override
	public int getIndexForTime(long t)
	{
		//System.out.println("t is " + Long.toString(t) + " and game time is " + Long.toString(GameInterface.GC.TC.getTime()));
		
		/*figure out where the earliest information we still have is stored.
			either 0, if the whole array has yet to be utilized, or index,
			because this is the next location to save to*/
		
		int begin_indx;
		if(saved_data[index].isDataSaved())
			begin_indx=index;
		else
			begin_indx=0;
		
		return ModifiedBinarySearch(begin_indx, getPreviousIndex(index), t);
	}

	//earliest: the index for an entry with time earlier than t
	//latest: the index for an entry with time later than t
	//t: the time being searched for
	//return: the index of the entry in saved_data with the greatest time of the entries that is less than t, or -1 if there is no such entry.
	private int ModifiedBinarySearch(int earliest, int latest, long t)
	{
		if(earliest==latest || getNextIndex(earliest) == latest)
		{
			if(saved_data[latest].t <= t) //in case latest never changes throughout recursion, in which case we have never tested it.
				return latest;
			else if(saved_data[earliest].t <= t)
				return earliest;
			else
			{
				int before_earliest = getPreviousIndex(earliest);
				if(saved_data[before_earliest].isDataSaved())
					return -1; //indicates the time being looked for it too old to be found.
				else
					return before_earliest; //will find later that data is not saved
			}
		}
		
		int middle_indx = translateNormalToActualIndex((translateToNormalArrayIndex(earliest) + translateToNormalArrayIndex(latest))/2);
		if(saved_data[middle_indx].t == t)
			return middle_indx;
		else if(saved_data[middle_indx].t > t)
			return ModifiedBinarySearch(earliest, middle_indx, t);
		else //if(saved_data[middle_indx].t < t) will be true by trichotomy
			return ModifiedBinarySearch(middle_indx, latest, t);
	}
	
	
	private int translateToNormalArrayIndex(int i) //translates index+1 to 0 and index to data_capacity-1
	{
		return (i-index+saved_data.length)%saved_data.length;
	}
	
	//the inverse of translateToNormalArrayIndex
	private int translateNormalToActualIndex(int i)
	{
		return (i+index)%saved_data.length;
	}
}
