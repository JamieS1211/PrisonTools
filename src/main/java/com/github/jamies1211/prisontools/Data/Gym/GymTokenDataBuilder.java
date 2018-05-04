package com.github.jamies1211.prisontools.Data.Gym;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

/**
 * Created by Jamie on 29/10/2016.
 */
public class GymTokenDataBuilder extends AbstractDataBuilder<GymTokenData> implements DataManipulatorBuilder<GymTokenData, ImmutableGymTokenData> {

	public GymTokenDataBuilder() {
		super(GymTokenData.class, 1);
	}

	@Override
	public GymTokenData create() {
		return new GymTokenData();
	}

	@Override
	public Optional<GymTokenData> createFrom(DataHolder dataHolder) {
		return this.create().fill(dataHolder);
	}

	@Override
	protected Optional<GymTokenData> buildContent(DataView dataView) throws InvalidDataException {
		return this.create().from(dataView.copy());
	}
}
