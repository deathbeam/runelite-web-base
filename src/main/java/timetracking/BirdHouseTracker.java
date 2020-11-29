package timetracking;

import def.js.Globals;
import def.js.Map;
import java.util.function.BiFunction;

public class BirdHouseTracker
{
	public Map<BirdHouseSpace, BirdHouseData> loadFromConfig(BiFunction<String, String, String> getConfiguration)
	{
		final Map<BirdHouseSpace, BirdHouseData> birdHouseData = new Map<>();

		for (int i = 0; i < BirdHouseSpace.values().length; i++)
		{
			final BirdHouseSpace space = BirdHouseSpace.values()[i];
			String key = TimeTrackingConfig.BIRD_HOUSE + "." + space.getVarp().getId();
			String storedValue = getConfiguration.apply(TimeTrackingConfig.CONFIG_GROUP, key);
			boolean updated = false;

			if (storedValue != null)
			{
				String[] parts = storedValue.split(":");
				if (parts.length == 2)
				{
					try
					{
						int varp = Globals.parseInt(parts[0]);
						long timestamp = Globals.parseInt(parts[1]);
						birdHouseData.set(space, new BirdHouseData(space, varp, timestamp));
						updated = true;
					}
					catch (Exception e)
					{
						// ignored
					}
				}
			}

			if (!updated) {
				birdHouseData.set(space, null);
			}
		}

		return birdHouseData;
	}
}
