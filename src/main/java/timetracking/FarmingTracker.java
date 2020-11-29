package timetracking;

import java.lang.String;
import java.util.function.BiFunction;
import def.js.Date;
import def.js.Globals;
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
		int tickrate = state.getTickRate() * 60;
		int farmingTickLength = 5 * 60;

		if (autoweed && state.getProduce() == Produce.WEEDS)
		{
			stage = 0;
			stages = 1;
			tickrate = 0;
		}

		if (botanist)
		{
			tickrate /= 5;
			farmingTickLength /= 5;
		}

		long doneEstimate = 0;
		if (tickrate > 0)
		{
			long tickNow = (unixNow + farmingTickLength) / tickrate;
			long tickTime = (unixTime + farmingTickLength) / tickrate;
			int delta = (int) (tickNow - tickTime);

			doneEstimate = ((stages - 1 - stage) + tickTime) * tickrate + farmingTickLength;

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
}
