package ferus.tigris.buzzle.levels;

import java.util.List;

import ferus.tigris.buzzle.data.PersonageInfo;
import ferus.tigris.buzzle.personages.AbstractBehavior;

public interface AbstractLevel {
	abstract public AbstractLevel nextLevel();
	abstract public AbstractLevel clone();
	abstract public void start();
	abstract public boolean check();
	public abstract void died(AbstractBehavior personage);
	public abstract void setScope(int scope);
	public abstract double scopeScale();
	public abstract void fill(List<PersonageInfo> personages);
	public abstract void onLoad();
	public abstract List<PersonageInfo> personages();
	public abstract int scope();
	public abstract int complexity();
	
	public abstract void update();
}
