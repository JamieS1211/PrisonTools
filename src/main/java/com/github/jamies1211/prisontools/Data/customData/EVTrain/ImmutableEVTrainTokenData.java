package com.github.jamies1211.prisontools.Data.customData.EVTrain;

import com.github.jamies1211.prisontools.Data.customData.MyKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

/**
 * Created by Jamie on 29/10/2016.
 */
public class ImmutableEVTrainTokenData extends AbstractImmutableData<ImmutableEVTrainTokenData, EVTrainTokenData> {
	private boolean isEVTrainToken;
	private double evTrainTime;
	private int evTrainZone;

	public ImmutableEVTrainTokenData(boolean isEVTrainToken, double evTrainTime, int evTrainZone) {
		this.isEVTrainToken = isEVTrainToken;
		this.evTrainTime = evTrainTime;
		this.evTrainZone = evTrainZone;
		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MyKeys.IS_EVTRAIN_TOKEN, () -> this.isEVTrainToken);
		registerKeyValue(MyKeys.IS_EVTRAIN_TOKEN, this::isEVTrainToken);

		registerFieldGetter(MyKeys.EVTRAIN_TIME, () -> this.evTrainTime);
		registerKeyValue(MyKeys.EVTRAIN_TIME, this::evTrainTime);

		registerFieldGetter(MyKeys.EVTRAIN_ZONE, () -> this.evTrainZone);
		registerKeyValue(MyKeys.EVTRAIN_ZONE, this::evTrainZone);
	}

	public ImmutableValue<Boolean> isEVTrainToken() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.IS_EVTRAIN_TOKEN, isEVTrainToken).asImmutable();
	}

	public ImmutableValue<Double> evTrainTime() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.EVTRAIN_TIME, evTrainTime).asImmutable();
	}

	public ImmutableValue<Integer> evTrainZone() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.EVTRAIN_ZONE, evTrainZone).asImmutable();
	}

	@Override
	public EVTrainTokenData asMutable() {
		return new EVTrainTokenData(isEVTrainToken, evTrainTime, evTrainZone);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(MyKeys.IS_EVTRAIN_TOKEN.getQuery(), this.isEVTrainToken)
				.set(MyKeys.EVTRAIN_TIME.getQuery(), this.evTrainTime)
				.set(MyKeys.EVTRAIN_ZONE.getQuery(), this.evTrainZone);
	}
}
