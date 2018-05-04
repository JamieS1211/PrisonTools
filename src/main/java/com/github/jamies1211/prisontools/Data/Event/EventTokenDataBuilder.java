package com.github.jamies1211.prisontools.Data.Event;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

/**
 * Created by Jamie on 29/10/2016.
 */
public class EventTokenDataBuilder extends AbstractDataBuilder<EventTokenData> implements DataManipulatorBuilder<EventTokenData, ImmutableEventTokenData> {

	public EventTokenDataBuilder() {
		super(EventTokenData.class, 1);
	}

	@Override
	public EventTokenData create() {
		return new EventTokenData();
	}

	@Override
	public Optional<EventTokenData> createFrom(DataHolder dataHolder) {
		return this.create().fill(dataHolder);
	}

	@Override
	protected Optional<EventTokenData> buildContent(DataView dataView) throws InvalidDataException {
		return this.create().from(dataView.copy());
	}
}
