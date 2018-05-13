package com.github.jamies1211.prisontools.Data.customData.EVTrain;

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
public class EVTrainTokenData extends AbstractData<com.github.jamies1211.prisontools.Data.customData.EVTrain.EVTrainTokenData, ImmutableEVTrainTokenData> {
	private boolean isEVTrainToken;
	private double evTrainTime;
	private int evTrainZone;

	public EVTrainTokenData(Boolean isEVTrainToken, Double evTrainTime, Integer evTrainZone) {
		this.isEVTrainToken = isEVTrainToken;
		this.evTrainTime = evTrainTime;
		this.evTrainZone = evTrainZone;
		// you must call this!
		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MyKeys.IS_EVTRAIN_TOKEN, () -> this.isEVTrainToken);
		registerFieldSetter(MyKeys.IS_EVTRAIN_TOKEN, id -> this.isEVTrainToken = isEVTrainToken);
		registerKeyValue(MyKeys.IS_EVTRAIN_TOKEN, this::isEVTrainToken);

		registerFieldGetter(MyKeys.EVTRAIN_TIME, () -> this.evTrainTime);
		registerFieldSetter(MyKeys.EVTRAIN_TIME, id -> this.evTrainTime = evTrainTime);
		registerKeyValue(MyKeys.EVTRAIN_TIME, this::evTrainTime);

		registerFieldGetter(MyKeys.EVTRAIN_ZONE, () -> this.evTrainZone);
		registerFieldSetter(MyKeys.EVTRAIN_ZONE, id -> this.evTrainZone = evTrainZone);
		registerKeyValue(MyKeys.EVTRAIN_ZONE, this::evTrainTime);
	}

	public Value<Boolean> isEVTrainToken() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.IS_EVTRAIN_TOKEN, isEVTrainToken);
	}

	public Value<Double> evTrainTime() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.EVTRAIN_TIME, evTrainTime);
	}

	public Value<Integer> evTrainZone() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.EVTRAIN_ZONE, evTrainZone);
	}

	@Override
	public Optional<EVTrainTokenData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<EVTrainTokenData> otherData_ = dataHolder.get(EVTrainTokenData.class);
		if (otherData_.isPresent()) {
			EVTrainTokenData otherData = otherData_.get();
			EVTrainTokenData finalData = overlap.merge(this, otherData);
			this.isEVTrainToken = finalData.isEVTrainToken;
			this.evTrainTime = finalData.evTrainTime;
			this.evTrainZone = finalData.evTrainZone;
		}
		return Optional.of(this);
	}

	// the double method isn't strictly necessary but makes implementing the builder easier
	@Override
	public Optional<com.github.jamies1211.prisontools.Data.customData.EVTrain.EVTrainTokenData> from(DataContainer container) {
		return from((DataView) container);
	}

	public Optional<com.github.jamies1211.prisontools.Data.customData.EVTrain.EVTrainTokenData> from(DataView view) {
		if (view.contains(MyKeys.IS_EVTRAIN_TOKEN.getQuery()) && view.contains(MyKeys.EVTRAIN_TIME.getQuery())) {
			this.isEVTrainToken = view.getBoolean(MyKeys.IS_EVTRAIN_TOKEN.getQuery()).get();
			this.evTrainTime = view.getDouble(MyKeys.EVTRAIN_TIME.getQuery()).get();
			this.evTrainZone = view.getInt(MyKeys.EVTRAIN_ZONE.getQuery()).get();
			return Optional.of(this);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public com.github.jamies1211.prisontools.Data.customData.EVTrain.EVTrainTokenData copy() {
		return new com.github.jamies1211.prisontools.Data.customData.EVTrain.EVTrainTokenData(this.isEVTrainToken, this.evTrainTime, this.evTrainZone);
	}

	@Override
	public ImmutableEVTrainTokenData asImmutable() {
		return new ImmutableEVTrainTokenData(this.isEVTrainToken, this.evTrainTime, this.evTrainZone);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}

	// IMPORTANT this is what causes your data to be written to NBT
	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(MyKeys.IS_EVTRAIN_TOKEN.getQuery(), this.isEVTrainToken)
				.set(MyKeys.EVTRAIN_TIME.getQuery(), this.evTrainTime)
				.set(MyKeys.EVTRAIN_ZONE.getQuery(), this.evTrainZone);
	}
}
