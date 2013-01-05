import java.util.HashMap;

public strictfp class OwnableSatelliteDataSaver<T extends OwnableSatellite<T>> extends DataSaver<T> {

	boolean is_data_saved;
	FacilityType bldg_in_prog;
	long t_finish;
	long t_start;
	int next_fac_id;
	
	Player own;
	HashMap<Integer, Facility<?>> fac; //save this in case facility gets destroyed, so we still have a reference to it
	Base base;
	//int num_mines;
	//int num_taxoffices;
	
	public OwnableSatelliteDataSaver()
	{
		is_data_saved=false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doLoadData(T sat)
	{
		sat.bldg_in_progress = bldg_in_prog;
		sat.time_finish = t_finish;
		sat.time_start = t_start;
		sat.next_facility_id = next_fac_id;
		sat.owner = own;
		synchronized(sat.facilities)
		{
			//sat.number_mines = num_mines;
			//sat.number_taxoffices = num_taxoffices;
			sat.facilities = (HashMap<Integer, Facility<?>>) fac.clone(); //unchecked cast warning
			sat.the_base = base;
		}
		sat.time=t;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doSaveData(T sat)
	{
		bldg_in_prog = sat.bldg_in_progress;
		t_finish = sat.time_finish;
		t_start = sat.time_start;
		next_fac_id = sat.next_facility_id;
		
		own=sat.owner;
		synchronized(sat.facilities)
		{
			//num_mines = sat.number_mines;
			//num_taxoffices = sat.number_taxoffices;
			fac = (HashMap<Integer, Facility<?>>) sat.facilities.clone(); //unchecked cast warning
			base = sat.the_base;
		}
		t=sat.time;
	}
}
