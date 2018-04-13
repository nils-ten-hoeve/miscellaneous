package nth.meyn.containersimulator.stack.transfer;

import nth.meyn.containersimulator.statemachine.State;

public class StateWaitForTransfer extends StackTransferState {


	private StackTransfer stackTransfer;

	public StateWaitForTransfer(StackTransfer stackTransfer) {
		super(stackTransfer);
		this.stackTransfer = stackTransfer;
	}

	@Override
	public Class<? extends State> getNextStateClass() {
		boolean okToFeedOut = stackTransfer.isOkToFeedOut();
		boolean okToFeedIn = stackTransfer.isOkToFeedIn();
		boolean routeOk = stackTransfer.isRouteOk();
		if (okToFeedOut && okToFeedIn && routeOk) {
			return StateTransfer.class;
		}
		return null;
	}

}
