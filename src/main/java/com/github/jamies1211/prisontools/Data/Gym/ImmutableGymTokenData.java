package com.github.jamies1211.prisontools.Data.Gym;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

/**
 * Created by Jamie on 29/10/2016.
 */
public class ImmutableGymTokenData extends AbstractImmutableData<ImmutableGymTokenData, GymTokenData> {

	private Boolean isGymToken;
	private String gymRank;

	public ImmutableGymTokenData(Boolean isGymToken, String gymRank) {
		this.isGymToken = isGymToken;
		this.gymRank = gymRank;
	}

	@Override
	protected void registerGetters() {
		// Getter and ValueGetter for IS_GYM_TOKEN
		registerFieldGetter(GymTokenData.IS_GYM_TOKEN, this::getIsGymToken);
		registerKeyValue(GymTokenData.IS_GYM_TOKEN, this::isGymToken);
		// Getter and ValueGetter for GYM_RANK
		registerFieldGetter(GymTokenData.GYM_RANK, this::getGymRank);
		registerKeyValue(GymTokenData.GYM_RANK, this::gymRank);
	}

	// Create mutable version of this
	@Override
	public GymTokenData asMutable() {
		return new GymTokenData(this.isGymToken, this.gymRank);
	}

	// Content Version (may be used for updating custom Data later)
	@Override
	public int getContentVersion() {
		return 1;
	}

	// !IMPORANT! Override toContainer and set your custom Data
	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(GymTokenData.IS_GYM_TOKEN, getIsGymToken()).set(GymTokenData.GYM_RANK, getGymRank());
	}

	// Getters

	private Boolean getIsGymToken() {
		return this.isGymToken;
	}

	private String getGymRank() {
		return this.gymRank;
	}

	// Value Getters
	private ImmutableValue<Boolean> isGymToken() {
		return Sponge.getRegistry().getValueFactory().createValue(GymTokenData.IS_GYM_TOKEN, this.isGymToken).asImmutable();
	}

	private ImmutableValue<String> gymRank() {
		return Sponge.getRegistry().getValueFactory().createValue(GymTokenData.GYM_RANK, this.gymRank).asImmutable();
	}
}