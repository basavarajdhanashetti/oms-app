package com.bsd.oms.jbpm;

import java.util.HashSet;
import java.util.Set;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bsd.oms.PropertyConfig;
import com.bsd.oms.entity.User;
import com.bsd.oms.process.Address;
import com.bsd.oms.process.ApprovalDetails;
import com.bsd.oms.process.PRQuotations;
import com.bsd.oms.process.PurchaseItem;
import com.bsd.oms.process.PurchaseRequest;
import com.bsd.oms.process.Vendor;
import com.bsd.oms.repo.UserRepository;

@Component
public class JBPMConnection {

	private static Logger LOG = LoggerFactory.getLogger(JBPMConnection.class);

	@Autowired
	private PropertyConfig propertyConfig;

	@Autowired
	private UserRepository userRepo;

	/**
	 * Get JBPM Connection factory instance
	 * 
	 * @return
	 */

	public KieServicesClient getNewSession(String userName) {
		LOG.debug("Preparing connection with BPM Configuration URL:" + propertyConfig.getBpmServerUrl() + ", username: "
				+ propertyConfig.getBpmServerUserName());

		User user = userRepo.getUserByUserName(userName);

		if(user == null){
			return null;
		}
		
		KieServicesConfiguration conf = KieServicesFactory.newRestConfiguration(propertyConfig.getBpmServerUrl(), userName,
				user.getPassword(), 3000);
		
		// If you use custom classes, such as Obj.class, add them to the
		// configuration
		Set<Class<?>> extraClassList = new HashSet<Class<?>>();

		extraClassList.add(Address.class);
		extraClassList.add(ApprovalDetails.class);
		extraClassList.add(PRQuotations.class);
		extraClassList.add(PurchaseItem.class);
		extraClassList.add(PurchaseRequest.class);
		extraClassList.add(Vendor.class);

		conf.addExtraClasses(extraClassList);
		conf.setMarshallingFormat(MarshallingFormat.JSON);

		return KieServicesFactory.newKieServicesClient(conf);
	}

}
