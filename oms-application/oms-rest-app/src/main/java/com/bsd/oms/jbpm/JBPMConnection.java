package com.bsd.oms.jbpm;

import java.util.HashSet;
import java.util.Set;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsd.oms.PropertyConfig;
import com.bsd.oms.process.OrderDetails;

public class JBPMConnection {

	private static Logger LOG = LoggerFactory.getLogger(JBPMConnection.class);

	/**
	 * 
	 */
	private KieServicesConfiguration configuration;

	private JBPMConnection() {
		// default for factory method
	}

	private JBPMConnection(KieServicesConfiguration configuration2) {
		this.configuration = configuration2;
	}

	private static JBPMConnection connection;

	public static JBPMConnection getInstance(PropertyConfig propertyConfig) {

		if (connection == null) {
			System.out
					.println("Preparing connection with BPM Configuration URL:"
							+ propertyConfig.getBpmServerUrl() + ", username: "
							+ propertyConfig.getBpmServerUserName());
			KieServicesConfiguration conf = KieServicesFactory
					.newRestConfiguration(propertyConfig.getBpmServerUrl(),
							propertyConfig.getBpmServerUserName(),
							propertyConfig.getBpmServerPassword(),3000);
			// If you use custom classes, such as Obj.class, add them to the
			// configuration
			Set<Class<?>> extraClassList = new HashSet<Class<?>>();
			extraClassList.add(OrderDetails.class);
			conf.addExtraClasses(extraClassList);
			conf.setMarshallingFormat(MarshallingFormat.JSON);
			
			connection = new JBPMConnection(conf);
		}
		return connection;
	}

	/**
	 * Get JBPM Connection factory instance
	 * 
	 * @return
	 */

	public KieServicesClient getNewSession() {
		return KieServicesFactory.newKieServicesClient(this.configuration);
	}

}
