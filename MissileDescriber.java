public strictfp class MissileDescriber implements Describer<Missile>
{
	int system_id;
	int missile_id;
	Describer<Ship> shooter;
	
	public MissileDescriber(Missile m)
	{
		missile_id = m.getId().getM_id();
		shooter = m.getId().getShooter().describer();
		system_id = m.location.getId();
	}
	
	@Override
	public Missile retrieveObject(Galaxy g)
	{
		return g.systems.get(system_id).missiles.get(
			new Missile.MissileId(missile_id, shooter.retrieveObject(g))
		);
	}
	
	public MissileDescriber(){}
	public int getSystem_id(){return system_id;}
	public void setSystem_id(int i){system_id=i;}
	public int getMissile_id(){return missile_id;}
	public void setMissile_id(int m){missile_id=m;}
	public Describer<Ship> getShooter(){return shooter;}
	public void setShooter(Describer<Ship> d){shooter = d;}
}