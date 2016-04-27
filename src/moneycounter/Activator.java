package moneycounter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import moneycounter.dao.OperationDAO;
import moneycounter.dao.impl.OperationDAOImpl;

public class Activator implements BundleActivator {
	private static BundleContext context;	
	
	private static OperationDAO dao = new OperationDAOImpl();
	
	public static OperationDAO getDao() {
		return dao;
	}

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		dao.close();
		Activator.context = null;
	}
}
