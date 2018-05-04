package com.github.jamies1211.prisontools.Data.Safari;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

/**
 * Created by Jamie on 29/10/2016.
 */
public class ImmutableSafariTokenData extends AbstractImmutableData<ImmutableSafariTokenData, SafariTokenData> {

	private Boolean isSafariToken;
	private Double safariTime;
	private Integer safariZone;

	public ImmutableSafariTokenData(Boolean isSafariToken, Double safariTime, Integer safariZone) {
		this.isSafariToken = isSafariToken;
		this.safariTime = safariTime;
		this.safariZone = safariZone;
	}

	@Override
	protected void registerGetters() {
		// Getter and ValueGetter for IS_SAFARI_TOKEN
		registerFieldGetter(SafariTokenData.IS_SAFARI_TOKEN, this::getIsSafariToken);
		registerKeyValue(SafariTokenData.IS_SAFARI_TOKEN, this::isSafariToken);
		// Getter and ValueGetter for SAFARI_TIME
		registerFieldGetter(SafariTokenData.SAFARI_TIME, this::getSafariTime);
		registerKeyValue(SafariTokenData.SAFARI_TIME, this::safariTime);
		// Getter and ValueGetter for SAFARI_ZONE
		registerFieldGetter(SafariTokenData.SAFARI_ZONE, this::getSafariZone);
		registerKeyValue(SafariTokenData.SAFARI_ZONE, this::safariZone);
	}

	// Create mutable version of this
	@Override
	public SafariTokenData asMutable() {
		return new SafariTokenData(this.isSafariToken, this.safariTime, this.safariZone);
	}

	// Content Version (may be used for updating custom Data later)
	@Override
	public int getContentVersion() {
		return 1;
	}

	// !IMPORANT! Override toContainer and set your custom Data
	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(SafariTokenData.IS_SAFARI_TOKEN, getIsSafariToken()).set(SafariTokenData.SAFARI_TIME, getSafariTime()).set(SafariTokenData.SAFARI_ZONE, getSafariZone());
	}

	// Getters

	private Boolean getIsSafariToken() {
		return this.isSafariToken;
	}

	private Double getSafariTime() {
		return this.safariTime;
	}

	private Integer getSafariZone() {
		return this.safariZone;
	}

	// Value Getters
	private ImmutableValue<Boolean> isSafariToken() {
		return Sponge.getRegistry().getValueFactory().createValue(SafariTokenData.IS_SAFARI_TOKEN, this.isSafariToken).asImmutable();
	}

	private ImmutableValue<Double> safariTime() {
		return Sponge.getRegistry().getValueFactory().createValue(SafariTokenData.SAFARI_TIME, this.safariTime).asImmutable();
	}

	private ImmutableValue<Integer> safariZone() {
		return Sponge.getRegistry().getValueFactory().createValue(SafariTokenData.SAFARI_ZONE, this.safariZone).asImmutable();
	}
}