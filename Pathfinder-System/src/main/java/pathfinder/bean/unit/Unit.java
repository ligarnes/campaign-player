package pathfinder.bean.unit;

import net.alteiar.shared.UniqueID;

public interface Unit {

	UniqueID getId();

	Integer getTotalHp();

	void setTotalHp(Integer totalHp);

	Integer getCurrentHp();

	void setCurrentHp(Integer currentHp);

	Integer getInitMod();

	void setInitMod(Integer initMod);
}
