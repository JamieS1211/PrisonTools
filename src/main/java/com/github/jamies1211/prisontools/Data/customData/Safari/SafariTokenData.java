package com.github.jamies1211.prisontools.Data.customData.Safari;

import com.github.jamies1211.prisontools.Data.customData.MyKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

/**
 * Created by Jamie on 29/10/2016.
 */
public class SafariTokenData extends AbstractData<com.github.jamies1211.prisontools.Data.customData.Safari.SafariTokenData, ImmutableSafariTokenData> {
	private boolean isSafariToken;
	private double safariTime;
	private int safariZone;

	public SafariTokenData(Boolean isSafariToken, Double safariTime, Integer safariZone) {
		this.isSafariToken = isSafariToken;
		this.safariTime = safariTime;
		this.safariZone = safariZone;
		// you must call this!
		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MyKeys.IS_SAFARI_TOKEN, () -> this.isSafariToken);
		registerFieldSetter(MyKeys.IS_SAFARI_TOKEN, id -> this.isSafariToken = isSafariToken);
		registerKeyValue(MyKeys.IS_SAFARI_TOKEN, this::isSafariToken);

		registerFieldGetter(MyKeys.SAFARI_TIME, () -> this.safariTime);
		registerFieldSetter(MyKeys.SAFARI_TIME, id -> this.safariTime = safariTime);
		registerKeyValue(MyKeys.SAFARI_TIME, this::safariTime);

		registerFieldGetter(MyKeys.SAFARI_ZONE, () -> this.safariZone);
		registerFieldSetter(MyKeys.SAFARI_ZONE, id -> this.safariZone = safariZone);
		registerKeyValue(MyKeys.SAFARI_ZONE, this::safariTime);
	}

	public Value<Boolean> isSafariToken() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.IS_SAFARI_TOKEN, isSafariToken);
	}

	public Value<Double> safariTime() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.SAFARI_TIME, safariTime);
	}

	public Value<Integer> safariZone() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.SAFARI_ZONE, safariZone);
	}

	@Override
	public Optional<SafariTokenData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<SafariTokenData> otherData_ = dataHolder.get(SafariTokenData.class);
		if (otherData_.isPresent()) {
			SafariTokenData otherData = otherData_.get();
			SafariTokenData finalData = overlap.merge(this, otherData);
			this.isSafariToken = finalData.isSafariToken;
			this.safariTime = finalData.safariTime;
			this.safariZone = finalData.safariZone;
		}
		return Optional.of(this);
	}

	// the double method isn't strictly necessary but makes implementing the builder easier
	@Override
	public Optional<com.github.jamies1211.prisontools.Data.customData.Safari.SafariTokenData> from(DataContainer container) {
		return from((DataView) container);
	}

	public Optional<com.github.jamies1211.prisontools.Data.customData.Safari.SafariTokenData> from(DataView view) {
		if (view.contains(MyKeys.IS_SAFARI_TOKEN.getQuery()) && view.contains(MyKeys.SAFARI_TIME.getQuery())) {
			this.isSafariToken = view.getBoolean(MyKeys.IS_SAFARI_TOKEN.getQuery()).get();
			this.safariTime = view.getDouble(MyKeys.SAFARI_TIME.getQuery()).get();
			this.safariZone = view.getInt(MyKeys.SAFARI_ZONE.getQuery()).get();
			return Optional.of(this);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public com.github.jamies1211.prisontools.Data.customData.Safari.SafariTokenData copy() {
		return new com.github.jamies1211.prisontools.Data.customData.Safari.SafariTokenData(this.isSafariToken, this.safariTime, this.safariZone);
	}

	@Override
	public ImmutableSafariTokenData asImmutable() {
		return new ImmutableSafariTokenData(this.isSafariToken, this.safariTime, this.safariZone);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}

	// IMPORTANT this is what causes your data to be written to NBT
	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(MyKeys.IS_SAFARI_TOKEN.getQuery(), this.isSafariToken)
				.set(MyKeys.SAFARI_TIME.getQuery(), this.safariTime)
				.set(MyKeys.SAFARI_ZONE.getQuery(), this.safariZone);
	}
}
