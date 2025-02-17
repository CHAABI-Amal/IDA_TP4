package tp4;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

public class mainContainer1 {

	public static void main(String[] args) {

		
		try {
		Runtime runtime =Runtime.instance();//j0
		
		Properties  properties= new ExtendedProperties(); //i0
		properties.setProperty(Profile.GUI, "true");//i1
		ProfileImpl profileImpl=new ProfileImpl(properties); //i2

		AgentContainer mainContainer=runtime.createMainContainer(profileImpl);//j1

		mainContainer.start();//j3
		} catch (ControllerException e) {e.printStackTrace();}
		}
}
