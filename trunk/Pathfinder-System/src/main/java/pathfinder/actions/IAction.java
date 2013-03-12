package pathfinder.actions;

public interface IAction {
	String getName();

	Boolean canDoAction();

	void doAction() throws Exception;
}
