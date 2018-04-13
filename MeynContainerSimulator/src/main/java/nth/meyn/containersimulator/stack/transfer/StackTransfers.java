package nth.meyn.containersimulator.stack.transfer;

import java.util.ArrayList;
import java.util.List;

import nth.meyn.containersimulator.unit.StackSource;

public class StackTransfers extends ArrayList<StackTransfer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7379837789704951297L;

	public List<StackTransfer> findWithSourceName(String sourceNameToFind) {
		List<StackTransfer> stackTransfers=new ArrayList<>();
		for (StackTransfer stackTransfer : this) {
			StackSource source = stackTransfer.getStackSource();
			if (source!=null && sourceNameToFind.equals(source.getName())) {
				stackTransfers.add(stackTransfer);
			} 
		}
		return stackTransfers;
	}
	
}
