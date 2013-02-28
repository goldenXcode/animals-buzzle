package ferus.tigris.buzzle.personages;

public class KillerBehavior extends BehaviorDelegate {

	private boolean die = false;

	public KillerBehavior(BehaviorDelegate b) {
		super(b);
	}

	public boolean dead() {
		return die;
	}
	
	public void onKill() {
		die = true;
	}

}
