package com.github.jamies1211.prisontools.Data.customData.EVTrain;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

/**
 * Created by Jamie on 29/10/2016.
 */
public class EVTrainTokenDataBuilder extends AbstractDataBuilder<EVTrainTokenData> implements DataManipulatorBuilder<EVTrainTokenData, ImmutableEVTrainTokenData> {
	public EVTrainTokenDataBuilder() {
		super(EVTrainTokenData.class, 1);
	}

	@Override
	public EVTrainTokenData create() {
		return new EVTrainTokenData(false, 0.0, 0);
	}

	@Override
	public Optional<EVTrainTokenData> createFrom(DataHolder dataHolder) {
		return create().fill(dataHolder);
	}

	@Override
	protected Optional<EVTrainTokenData> buildContent(DataView container) throws InvalidDataException {
		return create().from(container);
	}
}
