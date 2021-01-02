package timetracking;

import def.js.Date;
import def.js.Globals;
import java.util.function.BiFunction;
import timetracking.PatchImplementation.Produce;

public class FarmingTracker
{
	public PatchPrediction predictPatch(FarmingPatch patch, BiFunction<String, String, String> getConfiguration)
	{
		long unixNow = (long) (new Date().getTime() / 1000);

		boolean autoweed = Integer.toString(Autoweed.ON.ordinal())
			.equals(getConfiguration.apply(TimeTrackingConfig.CONFIG_GROUP, TimeTrackingConfig.AUTOWEED));

		boolean botanist = Boolean.TRUE.toString()
			.equals(getConfiguration.apply(TimeTrackingConfig.CONFIG_GROUP, TimeTrackingConfig.BOTANIST));

		String key = patch.getRegion().getRegionID() + "." + patch.getVarbit().getId();
		String storedValue = getConfiguration.apply(TimeTrackingConfig.CONFIG_GROUP, key);

		if (storedValue == null)
		{
			return null;
		}

		long unixTime = 0;
		int value = 0;
		{
			String[] parts = storedValue.split(":");
			if (parts.length == 2)
			{
				try
				{
					value = Globals.parseInt(parts[0]);
					unixTime = Globals.parseInt(parts[1]);
				}
				catch (Exception e)
				{
				}
			}
		}

		if (unixTime <= 0)
		{
			return null;
		}

		PatchState state = patch.getImplementation().forVarbitValue(value);

		if (state == null)
		{
			return null;
		}

		int stage = state.getStage();
		int stages = state.getStages();
		int tickrate = state.getTickRate();

		if (autoweed && state.getProduce() == Produce.WEEDS)
		{
			stage = 0;
			stages = 1;
			tickrate = 0;
		}

		if (botanist)
		{
			tickrate /= 5;
		}

		long doneEstimate = 0;
		if (tickrate > 0)
		{
			long tickNow = getTickTime(tickrate, 0, unixNow, getConfiguration);
			long tickTime = getTickTime(tickrate, 0, unixTime, getConfiguration);
			int delta = (int) (tickNow - tickTime) / (tickrate * 60);

			doneEstimate = getTickTime(tickrate, stages - 1 - stage, tickTime, getConfiguration);

			stage += delta;
			if (stage >= stages)
			{
				stage = stages - 1;
			}
		}

		return new PatchPrediction(
			state.getProduce(),
			state.getCropState(),
			doneEstimate,
			stage,
			stages
		);
	}

	private static long getTickTime(int tickRate, int ticks, long requestedTime, BiFunction<String, String, String> getConfiguration)
	{
		String offsetPrecisionMinsString = getConfiguration.apply(TimeTrackingConfig.CONFIG_GROUP, TimeTrackingConfig.FARM_TICK_OFFSET_PRECISION);
		String offsetTimeMinsString = getConfiguration.apply(TimeTrackingConfig.CONFIG_GROUP, TimeTrackingConfig.FARM_TICK_OFFSET);

		Integer offsetPrecisionMins = offsetPrecisionMinsString != null && !offsetPrecisionMinsString.isEmpty() ? Integer.parseInt(offsetPrecisionMinsString) : null;
		Integer offsetTimeMins = offsetTimeMinsString != null && !offsetTimeMinsString.isEmpty() ? Integer.parseInt(offsetTimeMinsString) : null;

		//All offsets are negative but are stored as positive
		long calculatedOffsetTime = 0L;
		if (offsetPrecisionMins != null && offsetTimeMins != null && (offsetPrecisionMins >= tickRate || offsetPrecisionMins >= 40))
		{
			calculatedOffsetTime = (offsetTimeMins % tickRate) * 60;
		}

		//Calculate "now" as +offset seconds in the future so we calculate the correct ticks
		long unixNow = requestedTime + calculatedOffsetTime;

		//The time that the tick requested will happen
		long timeOfCurrentTick = (unixNow - (unixNow % (tickRate * 60)));
		long timeOfGoalTick = timeOfCurrentTick + (ticks * tickRate * 60);

		//Move ourselves back to real time
		return timeOfGoalTick - calculatedOffsetTime;
	}
}
