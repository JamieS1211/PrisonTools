package com.github.jamies1211.prisontools.Data.customData.Gym;

import com.github.jamies1211.prisontools.Data.customData.MyKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

/**
 * Created by Jamie on 29/10/2016.
 */
public class ImmutableGymTokenData extends AbstractImmutableData<ImmutableGymTokenData, GymTokenData> {
	private boolean isGymToken;
	private String gymRank;

	public ImmutableGymTokenData(boolean isGymToken, String gymRank) {
		this.isGymToken = isGymToken;
		this.gymRank = gymRank;
		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MyKeys.IS_GYM_TOKEN, () -> this.isGymToken);
		registerKeyValue(MyKeys.IS_GYM_TOKEN, this::isGymToken);

		registerFieldGetter(MyKeys.GYM_RANK, () -> this.gymRank);
		registerKeyValue(MyKeys.GYM_RANK, this::gymRank);
	}

	public ImmutableValue<Boolean> isGymToken() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.IS_GYM_TOKEN, isGymToken).asImmutable();
	}

	public ImmutableValue<String> gymRank() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.GYM_RANK, gymRank).asImmutable();
	}

	@Override
	public GymTokenData asMutable() {
		return new GymTokenData(isGymToken, gymRank);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(MyKeys.IS_GYM_TOKEN.getQuery(), this.isGymToken)
				.set(MyKeys.GYM_RANK.getQuery(), this.gymRank);
	}
}
