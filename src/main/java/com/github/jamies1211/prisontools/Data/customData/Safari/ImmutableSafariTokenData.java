package com.github.jamies1211.prisontools.Data.customData.Safari;

import com.github.jamies1211.prisontools.Data.customData.MyKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

/**
 * Created by Jamie on 29/10/2016.
 */
public class ImmutableSafariTokenData extends AbstractImmutableData<ImmutableSafariTokenData, SafariTokenData> {
	private boolean isSafariToken;
	private double safariTime;
	private int safariZone;

	public ImmutableSafariTokenData(boolean isSafariToken, double safariTime, int safariZone) {
		this.isSafariToken = isSafariToken;
		this.safariTime = safariTime;
		this.safariZone = safariZone;
		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MyKeys.IS_SAFARI_TOKEN, () -> this.isSafariToken);
		registerKeyValue(MyKeys.IS_SAFARI_TOKEN, this::isSafariToken);

		registerFieldGetter(MyKeys.SAFARI_TIME, () -> this.safariTime);
		registerKeyValue(MyKeys.SAFARI_TIME, this::safariTime);

		registerFieldGetter(MyKeys.SAFARI_ZONE, () -> this.safariZone);
		registerKeyValue(MyKeys.SAFARI_ZONE, this::safariZone);
	}

	public ImmutableValue<Boolean> isSafariToken() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.IS_SAFARI_TOKEN, isSafariToken).asImmutable();
	}

	public ImmutableValue<Double> safariTime() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.SAFARI_TIME, safariTime).asImmutable();
	}

	public ImmutableValue<Integer> safariZone() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.SAFARI_ZONE, safariZone).asImmutable();
	}

	@Override
	public SafariTokenData asMutable() {
		return new SafariTokenData(isSafariToken, safariTime, safariZone);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(MyKeys.IS_SAFARI_TOKEN.getQuery(), this.isSafariToken)
				.set(MyKeys.SAFARI_TIME.getQuery(), this.safariTime)
				.set(MyKeys.SAFARI_ZONE.getQuery(), this.safariZone);
	}
}
