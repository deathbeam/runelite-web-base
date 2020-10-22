package timetracking;

public class VarbitCallerWorking
{
	public static int getVarbitId(VarbitWrapper varbitWrapper) {
		final Varbits varbit = varbitWrapper.getVarbit();
		return varbit.getId();
	}
}
