package com.github.jamies1211.prisontools.Data.Safari;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

/**
 * Created by Jamie on 29/10/2016.
 */
public class SafariTokenDataBuilder extends AbstractDataBuilder<SafariTokenData> implements DataManipulatorBuilder<SafariTokenData, ImmutableSafariTokenData> {

	public SafariTokenDataBuilder() {
		super(SafariTokenData.class, 1);
	}

	@Override
	public SafariTokenData create() {
		return new SafariTokenData();
	}

	@Override
	public Optional<SafariTokenData> createFrom(DataHolder dataHolder) {
		return this.create().fill(dataHolder);
	}

	@Override
	protected Optional<SafariTokenData> buildContent(DataView dataView) throws InvalidDataException {
		return this.create().from(dataView.copy());
	}
}
