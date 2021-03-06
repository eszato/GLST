public strictfp class ShipyardCancelBuildOrder extends Order {

	FacilityDescriber<Shipyard> shipyard_describer;
	int ship_id;
	
	private Shipyard the_yard;
	private Ship the_ship;
	
	public ShipyardCancelBuildOrder(Shipyard syd, Ship s, long t)
	{
		super(t, syd.location.owner);
		
		the_yard=syd;
		shipyard_describer = syd.describer();
		scheduled_time=t;
		ship_id = s.getId().queue_id;
		the_ship = s;
		
		mode = Order.MODE.ORIGIN;
	}
	
	@Override
	public boolean execute(Galaxy g) throws DataSaverControl.DataNotYetSavedException {
		
		the_yard = (Shipyard)shipyard_describer.retrieveObject(g);
		
		if(the_yard != null)
		{
			if(the_yard.is_alive && the_yard.manufac_queue.size() != 0 &&
					the_yard.location.owner.getId() == p_id) //validity check: is the Shipyard still alive?
			{
				
				if (mode == MODE.NETWORK)
					the_ship = the_yard.manufac_queue.get(ship_id);
				
				if(the_ship != null)
				{
					the_yard.removeFromQueue(the_ship, scheduled_time);
					decision = Decision.ACCEPT;
					return true;
				}
			}
		}
		
		decision = Decision.REJECT;
		return false;
	}

	public ShipyardCancelBuildOrder(){mode=Order.MODE.NETWORK;}
	
	public FacilityDescriber<Shipyard> getShipyard_describer(){return shipyard_describer;}
	public void setShipyard_describer(FacilityDescriber<Shipyard> desc){shipyard_describer=desc;}
	public int getShip_id(){return ship_id;}
	public void setShip_id(int i){ship_id=i;}
}
