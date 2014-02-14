package nth.portfoliochart;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductGroupFactory {
	static final String MEYN = "Meyn";
	private static final String OTHER = "Other";
	private static final String ISHIDA = "Ishida Poultry";
	private static final String MAREL_STORK = "Marel";
	private static final String BAADER_LINCO = "Baader";
	private static final String BRIGHT_COOP = "Bright Coop";
	private static final String FOODMATE = "Foodmate";
	
	private static final Color MEYN_COLOR = new Color(0,120,91);
	private static final Color MEYN_ISHIDA_COLOR = new Color (192,52,46);
	private static final Color MAREL_STORK_COLOR = new Color (0,53,105);
	private static final Color BAADER_LINCO_COLOR = new Color (10,166,206);
	private static final Color FOODMATE_COLOR = new Color (38,187,236);
	private static final Color BRIGHT_COOP_COLOR = Color.RED;
	private static final Color OTHER_COLOR = new Color (128,128,128);
	
	private static final int MEGA_EURO = 1000000;
	
	
	private static final int WGLPMC_MEYN_REVANUE_2012_IN_EURO = 5*MEGA_EURO;
	private static final int WGLPMC_ISHIDA_REVANUE_2012_IN_EURO = 55*MEGA_EURO;
	private static final int WGLPMC_MAREL_REVANUE_2012_IN_EURO = 183*MEGA_EURO;
	

	private static final int WGL_DEBONING_MEYN_REVANUE_2012_IN_EURO = 200*MEGA_EURO;
	private static final int WGL_DEBONING_MAREL_REVANUE_2012_IN_EURO = 205*MEGA_EURO;
	private static final int WGL_DEBONING_BAADER_REVANUE_2012_IN_EURO = 60*MEGA_EURO;
	private static final int WGL_DEBONING_TOTAL_REVANUE_2012_IN_EURO = 585*MEGA_EURO;
	private static final int WGL_DEBONING_OTHER_REVANUE_2012_IN_EURO = WGL_DEBONING_TOTAL_REVANUE_2012_IN_EURO-WGL_DEBONING_MEYN_REVANUE_2012_IN_EURO- WGL_DEBONING_MAREL_REVANUE_2012_IN_EURO- WGL_DEBONING_BAADER_REVANUE_2012_IN_EURO;  
	
	
	
	private static final int MEYN_REVANUE_2012_IN_EURO = WGL_DEBONING_MEYN_REVANUE_2012_IN_EURO+WGLPMC_MEYN_REVANUE_2012_IN_EURO;
	private static final int ISHIDA_REVANUE_2012_IN_EURO = WGLPMC_ISHIDA_REVANUE_2012_IN_EURO;
	private static final int MAREL_REVANUE_2012_IN_EURO = WGL_DEBONING_MAREL_REVANUE_2012_IN_EURO+WGLPMC_MAREL_REVANUE_2012_IN_EURO;
	private static final int BAADER_REVANUE_2012_IN_EURO = WGL_DEBONING_BAADER_REVANUE_2012_IN_EURO ;
	private static final int OTHER_REVANUE_2012_IN_EURO = WGL_DEBONING_OTHER_REVANUE_2012_IN_EURO ;
	private static final double UNKNOWN = -1;
	
	
	
	
	
	public static List<ProductGroup> createProductGroups() {
		List<ProductGroup> productGroups = new ArrayList<ProductGroup>();

		ProductGroup liveBirdHandling = new ProductGroup("LBH", 0.03  );
		liveBirdHandling.getSuppliers().add(new Supplier(MEYN, MEYN_COLOR, 8700000));
		liveBirdHandling.getSuppliers().add(new Supplier(MAREL_STORK, MAREL_STORK_COLOR, 14000000));
		liveBirdHandling.getSuppliers().add(new Supplier(BRIGHT_COOP,  BRIGHT_COOP_COLOR, 12500000));
		liveBirdHandling.getSuppliers().add(new Supplier(BAADER_LINCO,  BAADER_LINCO_COLOR, 6300000));
		liveBirdHandling.getSuppliers().add(new Supplier(OTHER, OTHER_COLOR, 10000000));
		productGroups.add(liveBirdHandling);

		ProductGroup defeathering = new ProductGroup("Defeathering", UNKNOWN);
		defeathering.getSuppliers().add(new Supplier(MEYN, MEYN_COLOR,   15424000  ));
		defeathering.getSuppliers().add(new Supplier(MAREL_STORK, MAREL_STORK_COLOR,  21739000 ));
		defeathering.getSuppliers().add(new Supplier(BAADER_LINCO, BAADER_LINCO_COLOR, 10150000 ));
		defeathering.getSuppliers().add(new Supplier(OTHER, OTHER_COLOR,  12573000 ));
		productGroups.add(defeathering);

		ProductGroup evisceration = new ProductGroup("Evisceration", 0.07);
		evisceration.getSuppliers().add(new Supplier(MEYN, MEYN_COLOR, 32690000));
		evisceration.getSuppliers().add(new Supplier(MAREL_STORK, MAREL_STORK_COLOR, 29290000));
		evisceration.getSuppliers().add(new Supplier(BAADER_LINCO, BAADER_LINCO_COLOR,  13070000));
		evisceration.getSuppliers().add(new Supplier(OTHER, OTHER_COLOR,   5950000 ));
		productGroups.add(evisceration);

		ProductGroup chilling = new ProductGroup("Chilling", UNKNOWN);
//		chilling.getSuppliers().add(new Supplier(MEYN, MEYN_COLOR, UNDIFINED_MARKET_SHARE));
//		chilling.getSuppliers().add(new Supplier(MAREL_STORK, MAREL_STORK_COLOR, UNDIFINED_MARKET_SHARE));
//		chilling.getSuppliers().add(new Supplier(BAADER_LINCO, BAADER_LINCO_COLOR, UNDIFINED_MARKET_SHARE));
//		chilling.getSuppliers().add(new Supplier(OTHER, OTHER_COLOR, calculateRemainingMarketShare(chilling)));
//		productGroups.add(chilling);
//
		ProductGroup cutUp = new ProductGroup("Cut Up", UNKNOWN);
		cutUp.getSuppliers().add(new Supplier(MEYN, MEYN_COLOR,  14796000  ));
		cutUp.getSuppliers().add(new Supplier(MAREL_STORK, MAREL_STORK_COLOR,  10195000  ));
		cutUp.getSuppliers().add(new Supplier(BAADER_LINCO, BAADER_LINCO_COLOR,  5503000  ));
		cutUp.getSuppliers().add(new Supplier(FOODMATE, FOODMATE_COLOR,  2751000 ));
		cutUp.getSuppliers().add(new Supplier(OTHER, OTHER_COLOR,  7181000  ));
		productGroups.add(cutUp);

		ProductGroup deboning = new ProductGroup("Deboning", UNKNOWN);
		deboning.getSuppliers().add(new Supplier(MEYN, MEYN_COLOR, 13800000));
		deboning.getSuppliers().add(new Supplier(MAREL_STORK, MAREL_STORK_COLOR, 12645825));
		deboning.getSuppliers().add(new Supplier(OTHER, OTHER_COLOR, 15000000));
		productGroups.add(deboning);

		ProductGroup wglpmc = new ProductGroup("WGLPMC", 0.20);
//		wglpmc.getSuppliers().add(new Supplier(MEYN, MEYN_COLOR, 5*MEGA_EURO));//5 M Euro in 2012 
//		wglpmc.getSuppliers().add(new Supplier(ISHIDA, MEYN_ISHIDA_COLOR, 30*MEGA_EURO));//55 M Euro in 2012=30 Capital Sales
//		wglpmc.getSuppliers().add(new Supplier(MAREL_STORK, MAREL_STORK_COLOR, 109*MEGA_EURO));//198 M Euro in 2012=109 Capital Sales  
//		wglpmc.getSuppliers().add(new Supplier(OTHER, OTHER_COLOR, (70-30)*MEGA_EURO));//127M *0.55 -ishida
//		productGroups.add(wglpmc);
		
		//validations
		
		DecimalFormat curency = new DecimalFormat("€ ###,###.###");
		
		int totalCSRevanuePrimaryAndSecondaryProcessing = liveBirdHandling.getTotalCapitalSales()+defeathering.getTotalCapitalSales()+evisceration.getTotalCapitalSales()+chilling.getTotalCapitalSales()+cutUp.getTotalCapitalSales()+deboning.getTotalCapitalSales();
		System.out.println("totalCSRevanuePrimaryAndSecondaryProcessing: "+ curency.format(totalCSRevanuePrimaryAndSecondaryProcessing));
		//totalCSRevanuePrimaryAndSecondaryProcessing = 650 mil - aftersales
		
		int totalCSRevanueWGLPMC=wglpmc.getTotalCapitalSales();
		System.out.println("totalCSRevanueWGLPMC: "+ curency.format(totalCSRevanueWGLPMC));
		//330 mil?
		
		int totalCSRevanueMeyn=getTotalCapitalSalesRevanue(MEYN, productGroups);
		System.out.println("totalCSRevanueMeyn: "+ curency.format(totalCSRevanueMeyn));
		//201.95 mil-40% after sales=121?
				
		int totalCSRevanueMarelStork=getTotalCapitalSalesRevanue(MAREL_STORK, productGroups);
		System.out.println("totalCSRevanueMarelStork: "+ curency.format(totalCSRevanueMarelStork));
		//388.4 mil?
		
		int totalCSRevanueBaaderLinco=getTotalCapitalSalesRevanue(BAADER_LINCO, productGroups);
		System.out.println("totalCSRevanueBaaderLinco: "+ curency.format(totalCSRevanueBaaderLinco));
		//total revanue=60 mil  = estimated 40mil Capital sales ?
		
		
		return productGroups;

	}





	private static int getTotalCapitalSalesRevanue(String supplierName, List<ProductGroup> productGroups) {
		int total = 0;
		for (ProductGroup productGroup : productGroups) {
			for (Supplier supplier : productGroup.getSuppliers()) {
				if (supplier.getName().equals(supplierName)) {
					total+=supplier.getEstimatedCapitalSalesRevanue2012();
				}
			}
		}
		return total;
		
	}

//	private static double calculateRemainingMarketShare(ProductGroup productGroup) {
//		List<Supplier> suppliers = productGroup.getSuppliers();
//		double remainingMarketShare=1;
//		for (Supplier supplier:suppliers) {
//			remainingMarketShare-=supplier.getMarketSharePercentage();
//		}
//		return remainingMarketShare;
//	}

}
