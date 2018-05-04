package com.github.jamies1211.prisontools.Data.EVTrain;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

/**
 * Created by Jamie on 29/10/2016.
 */
public class ImmutableEVTrainTokenData extends AbstractImmutableData<ImmutableEVTrainTokenData, EVTrainTokenData> {

	private Boolean isEVTrainToken;
	private Double evTrainTime;
	private Integer evTrainZone;

	public ImmutableEVTrainTokenData(Boolean isEVTrainToken, Double evTrainTime, Integer evTrainZone) {
		this.isEVTrainToken = isEVTrainToken;
		this.evTrainTime = evTrainTime;
		this.evTrainZone = evTrainZone;
	}

	@Override
	protected void registerGetters() {
		// Getter and ValueGetter for IS_EVTRAIN_TOKEN
		registerFieldGetter(EVTrainTokenData.IS_EVTRAIN_TOKEN, this::getIsEVTrainToken);
		registerKeyValue(EVTrainTokenData.IS_EVTRAIN_TOKEN, this::isEVTrainToken);
		// Getter and ValueGetter for EVTRAIN_TIME
		registerFieldGetter(EVTrainTokenData.EVTRAIN_TIME, this::getEvTrainTime);
		registerKeyValue(EVTrainTokenData.EVTRAIN_TIME, this::evTrainTime);
		// Getter and ValueGetter for EVTRAIN_ZONE
		registerFieldGetter(EVTrainTokenData.EVTRAIN_ZONE, this::getEvTrainZone);
		registerKeyValue(EVTrainTokenData.EVTRAIN_ZONE, this::evTrainZone);
	}

	// Create mutable version of this
	@Override
	public EVTrainTokenData asMutable() {
		return new EVTrainTokenData(this.isEVTrainToken, this.evTrainTime, this.evTrainZone);
	}

	// Content Version (may be used for updating custom Data later)
	@Override
	public int getContentVersion() {
		return 1;
	}

	// !IMPORANT! Override toContainer and set your custom Data
	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(EVTrainTokenData.IS_EVTRAIN_TOKEN, getIsEVTrainToken()).set(EVTrainTokenData.EVTRAIN_TIME, getEvTrainTime()).set(EVTrainTokenData.EVTRAIN_ZONE, getEvTrainZone());
	}

	// Getters

	private Boolean getIsEVTrainToken() {
		return this.isEVTrainToken;
	}

	private Double getEvTrainTime() {
		return this.evTrainTime;
	}

	private Integer getEvTrainZone() {
		return this.evTrainZone;
	}

	// Value Getters
	private ImmutableValue<Boolean> isEVTrainToken() {
		return Sponge.getRegistry().getValueFactory().createValue(EVTrainTokenData.IS_EVTRAIN_TOKEN, this.isEVTrainToken).asImmutable();
	}

	private ImmutableValue<Double> evTrainTime() {
		return Sponge.getRegistry().getValueFactory().createValue(EVTrainTokenData.EVTRAIN_TIME, this.evTrainTime).asImmutable();
	}

	private ImmutableValue<Integer> evTrainZone() {
		return Sponge.getRegistry().getValueFactory().createValue(EVTrainTokenData.EVTRAIN_ZONE, this.evTrainZone).asImmutable();
	}
}