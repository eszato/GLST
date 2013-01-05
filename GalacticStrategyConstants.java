import java.awt.Color;

public strictfp class GalacticStrategyConstants
{
	//this class contains constants relevant to the games/galaxy designer's configuration.
	//It does NOT contain constants that are only of interest within a certain class, such the numeric values which represent different states of 1 variable.
	//For instance, GDFrame has DRAG_NONE, DRAG_DIST, and DRAG_RANGE, which tells the interface if ranges or distances should be drawn during drags.
	//In particular, these values are for the different states of drag_options
	
	//time_granularity is the amount of time, in ms, between updates of the game.  Used as a time increment in ship physics.
	final static long TIME_GRANULARITY = 20; //20ms -> 50 fps
	final static long TIME_BETWEEN_RESOURCES = 3000; //you get resources every 3 seconds.
	final static int data_capacity=50; //amount of time grains to save data from.  50 grains at 20ms per grain = 1 second
	
	//******************************************PHYSICS TO PROGRAM CONVERSIONS*******************************************//
	
	//Used in orbit
	static double PERIOD_CONSTANT =100000; //equiv to 2pi/sqrt(G), where G is the "gravity constant" of the game.
	
	//*******************************DEFAULTS USED IN Galaxy Designer********************************************************//
	
	//Used in SystemViewer
	static int DEFAULT_STAR_SIZE=35;
	static int DEFAULT_STAR_ZONE_SIZE=50;
	static int DEFAULT_PLANET_SIZE = 20;
	static int DEFAULT_MOON_SIZE = 12;
	static double DEFAULT_PLANET_MASS = 10;
	static double DEFAULT_STAR_MASS = 10000;
	static double DEFAULT_MOON_MASS= 1;
	
	static final int MIN_PLANET_SIZE=10;
	static final int MIN_STAR_SIZE=16;
	static final int MIN_MOON_SIZE=6;
	static final int MIN_ASTEROID_SIZE=3;
	static final int MAX_STAR_SIZE=100;
	static final int MAX_PLANET_SIZE=75;
	static final int MAX_MOON_SIZE=30;
	static final int MAX_ASTEROID_SIZE=10;
	
	static final int MAJOR_TICKS_FOR_OBJECT_SIZE = 25;
	static final int MINOR_TICKS_FOR_OBJECT_SIZE = 5;
	
	//Used in GalacticMapPainter and GDFrame
	//these 4 control the distance slider that marks off the ranges to show or the maximum distances that should be drawn
	static final int MAX_DIST=1000;
	static final int DEFAULT_DIST=100; //this is used as the default value for the slider determining the distances shown.  used for max_dist_shown in GalacticMapPainter
	static final int DIST_MINOR_TICKS = 25;
	static final int DIST_MAJOR_TICKS = 100;
	
	//These 4 values set up the Nav level slider and spinner
	static final int MIN_NAV_LEVEL=1;
	static final int MAX_NAV_LEVEL=10;
	static final int DEFAULT_NAV_LEVEL = MAX_NAV_LEVEL;//always start at the max. higher nav level = easier to navigate to.  So unless specified, systems placed are by default easy to get to.
	static final int NAV_LEVEL_TICK_SPACING=1; //navigation slider automatically snaps to ticks.  This SHOULD NOT be changed!!!!!!!  This means nav_level can only take on integer values.  And the nav spinner does the same, using this variable to control how much it can change at a time
	
	//These values set the defaults for the other navigability controls.
	static final int DEFAULT_NAV_OPTIONS = 0; //0=don't show navigabilities, 1=show navigabilities for only selected systems, 2=show all navigabilities
	static final boolean DEFAULT_DISPLAY_UNNAV = false; //false = do not display systems with nav's below the threshold, true = display them, but gray instead of white
	//**********************INTERFACE SETTINGS******************************************************************************//
	
	static final int EDGE_BOUND=40; //this is the distance from the edge of the system, in pixels, at which the system will start to be scrolled
	static final int SYS_WIDTH=1200; //the allowed width of a system
	static final int SYS_HEIGHT=1000; //the allowed height of a system
	
	static final int GALAXY_WIDTH=800; //the width of the galaxy
	static final int GALAXY_HEIGHT=600; //the height of the galaxy
	
	static final double DEFAULT_SCALE = 1.0d; //this sets the default scale for the view of the system
	static final double MIN_SCALE = 0.66d;
	static final double MAX_SCALE=5.0d;
	static final double SCROLL_SENSITIVITY=.1d; //the amount the wheel is rotated will be multiplied by this number to determine how much the scale changes.
	
	static final double SELECTION_TOLERANCE = 5.0d; //when the system is clicked on, the program looks for an object centered at the click site within a circle of radius of this many pixels.
	
	//*************************************GAMEPLAY SETTINGS****************************************************************//
	
	static int MAX_PLAYERS = 2;
	
	static Color[] DEFAULT_COLORS = {Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN, Color.ORANGE, Color.MAGENTA, Color.PINK, Color.BLUE};
	
	//default build times and costs for facilities configurable in FacilityType
	
	final static double LANDING_RANGE = 10.0d; //the max distance at which ships can get/send troops to planets
	public final static float troop_transfer_rate = .1f;
	//Used by TaxOffice
	static double DEFAULT_INCOME_RATE = .01; //$ per tax update per person
	//Used by Player
	static long DEFAULT_MONEY=1000;
	static long DEFAULT_METAL=1000;
	
	//ship attacking range
	static final double Detection_Range_Sq = 40000.0;
	static final double Attacking_Range_Sq=40000.0;
	static final double Attacking_Range=200.0;
	static final long Attacking_cooldown=1200;
	static final double WARP_EXIT_SPEED = .75; //px/ms
	static final double CloseEnoughDistance = 100.0;
	
	//missile setup
	final static int MISSILE_DAMAGE = 10;
	final static double INITIAL_MISSILE_SPEED = .04;
	
	//Used by Shipyard
	static final int queue_capa=5;//the capacity of a shipyard's queue
	
	//Defaults for Bases
	final static int initial_soldier=100;
	final static int default_max_soldier=500;
	final static float soldier_production_rate = .005f; //solders per millisecond.  corresponds to 5 soldiers per second
	final static int initial_base_endu=500;
	final static int initial_shipyard_endu = 250;
	final static int initial_mine_endu = 150;
	final static int initial_taxoffice_endu = 100;
	final static int initial_research_building_endu = 200;
	final static int max_soldier_upgraderate=100;
	final static int endu_upgraderate=100;
	final static double additional_mine_penalty = .9;
	final static double additional_taxoffice_penalty = .9;
	final static int planet_building_limit = 7;
	//For MathFormula
	final static double rand_mod = .05;
	//For Panel sizing
	final static int mini_ship_w=50;
	final static int mini_ship_h=75;
	final static int mini_prog_w=75;
	
	//***************************************************************GENERAL DATA*******************************************//
	
	//used by GameControl for networking
	static final int DEFAULT_PORT_NUMBER = 7007; //used for game server.
	public static final String DEFAULT_IP = "192.168.5.101";
}