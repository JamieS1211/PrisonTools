package com.github.jamies1211.prisontools.Data.EVTrain;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

import static org.spongepowered.api.data.DataQuery.of;
import static org.spongepowered.api.data.key.KeyFactory.makeSingleKey;

/**
 * Created by Jamie on 29/10/2016.
 */
public class EVTrainTokenData extends AbstractData<EVTrainTokenData, ImmutableEVTrainTokenData> {

	// TypeTokens needed for creating Keys (can be created inline too)
	private static TypeToken<Boolean> TT_Boolean = new TypeToken<Boolean>() {};
	private static TypeToken<Value<Boolean>> TTV_Boolean= new TypeToken<Value<Boolean>>() {};
	private static TypeToken<Double> TT_Double = new TypeToken<Double>() {};
	private static TypeToken<Value<Double>> TTV_Double = new TypeToken<Value<Double>>() {};
	private static TypeToken<Integer> TT_Integer = new TypeToken<Integer>() {};
	private static TypeToken<Value<Integer>> TTV_Integer = new TypeToken<Value<Integer>>() {};

	// Keys for this custom Data
	public static Key<Value<Boolean>> IS_EVTRAIN_TOKEN = makeSingleKey(TT_Boolean, TTV_Boolean, of("Boolean"), "prisonTools:evTrainTokenData:boolean", "Boolean");
	public static Key<Value<Double>> EVTRAIN_TIME = makeSingleKey(TT_Double, TTV_Double, of("Double"), "prisonTools:evTrainTokenData:double", "Double");
	public static Key<Value<Integer>> EVTRAIN_ZONE = makeSingleKey(TT_Integer, TTV_Integer, of("Integer"), "prisonTools:evTrainTokenData:integer", "Integer");

	// Live Data in this instance
	private boolean isEVTrainToken;
	private Double evTrainTime;
	private Integer evTrainZone;

	// For DataBuilder and personal use
	public EVTrainTokenData() {
	}

	public EVTrainTokenData(Boolean isEventToken, Double evTrainTime, Integer evTrainZone) {
		this.isEVTrainToken = isEventToken;
		this.evTrainTime = evTrainTime;
		this.evTrainZone = evTrainZone;
		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		// Getter, Setter and ValueGetter for IS_EVTRAIN_TOKEN
		registerFieldGetter(IS_EVTRAIN_TOKEN, EVTrainTokenData.this::getIsSafariToken);
		registerFieldSetter(IS_EVTRAIN_TOKEN, EVTrainTokenData.this::setIsSafariToken);
		registerKeyValue(IS_EVTRAIN_TOKEN, EVTrainTokenData.this::isSafariToken);
		// Getter, Setter and ValueGetter for EVTRAIN_TIME
		registerFieldGetter(EVTRAIN_TIME, EVTrainTokenData.this::getEvTrainTime);
		registerFieldSetter(EVTRAIN_TIME, EVTrainTokenData.this::setEvTrainTime);
		registerKeyValue(EVTRAIN_TIME, EVTrainTokenData.this::evTrainTime);
		// Getter, Setter and ValueGetter for EVTRAIN_ZONE
		registerFieldGetter(EVTRAIN_ZONE, EVTrainTokenData.this::getEvTrainZone);
		registerFieldSetter(EVTRAIN_ZONE, EVTrainTokenData.this::setEvTrainZone);
		registerKeyValue(EVTRAIN_ZONE, EVTrainTokenData.this::evTrainZone);
	}

	// Create immutable version of this
	@Override
	public ImmutableEVTrainTokenData asImmutable() {
		return new ImmutableEVTrainTokenData(this.isEVTrainToken, this.evTrainTime, this.evTrainZone);
	}

	// Fill Data using DataHolder and MergeFunction
	@Override
	public Optional<EVTrainTokenData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<Boolean> isEVTrainToken = dataHolder.get(IS_EVTRAIN_TOKEN);
		Optional<Double> evTrainTime = dataHolder.get(EVTRAIN_TIME);
		Optional<Integer> evTrainZone = dataHolder.get(EVTRAIN_ZONE);
		// Only apply if the custom Data is present
		if (isEVTrainToken.isPresent() && evTrainTime.isPresent() && evTrainZone.isPresent()) {
			EVTrainTokenData data = this.copy();
			data.isEVTrainToken = isEVTrainToken.get();
			data.evTrainTime = evTrainTime.get();
			data.evTrainZone = evTrainZone.get();

			// merge Data
			data = overlap.merge(this, data);
			if (data != this) {
				this.isEVTrainToken = data.isEVTrainToken;
				this.evTrainTime = data.evTrainTime;
			}

			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Fill Data using DataContainer
	@Override
	public Optional<EVTrainTokenData> from(DataContainer container) {
		Optional<Boolean> isSafariToken = container.getBoolean(IS_EVTRAIN_TOKEN.getQuery());
		Optional<Double> evTrainTime = container.getDouble(EVTRAIN_TIME.getQuery());
		Optional<Integer> evTrainZone = container.getInt(EVTRAIN_ZONE.getQuery());
		// Only apply if the custom Data is present
		if (isSafariToken.isPresent() && evTrainTime.isPresent() && evTrainZone.isPresent()) {
			this.isEVTrainToken = isSafariToken.get();
			this.evTrainTime = evTrainTime.get();
			this.evTrainZone = evTrainZone.get();
			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Create copy of this
	@Override
	public EVTrainTokenData copy() {
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
		return super.toContainer().set(IS_EVTRAIN_TOKEN, getIsSafariToken()).set(EVTRAIN_TIME, getEvTrainTime()).set(EVTRAIN_ZONE, getEvTrainZone());
	}

	// Getters

	private Boolean getIsSafariToken() {
		return this.isEVTrainToken;
	}

	private Double getEvTrainTime() {
		return this.evTrainTime;
	}

	private Integer getEvTrainZone() {
		return this.evTrainZone;
	}

	// Setters

	private void setIsSafariToken(Boolean isSafariToken) {
		this.isEVTrainToken = isSafariToken;
	}

	private void setEvTrainTime(Double evTrainTime) {
		this.evTrainTime = evTrainTime;
	}

	private void setEvTrainZone(Integer evTrainZone) {
		this.evTrainZone = evTrainZone;
	}

	// ValueGetters

	private Value<Boolean> isSafariToken() {
		return Sponge.getRegistry().getValueFactory().createValue(IS_EVTRAIN_TOKEN, getIsSafariToken());
	}

	private Value<Double> evTrainTime() {
		return Sponge.getRegistry().getValueFactory().createValue(EVTRAIN_TIME, getEvTrainTime());
	}

	private Value<Integer> evTrainZone() {
		return Sponge.getRegistry().getValueFactory().createValue(EVTRAIN_ZONE, getEvTrainZone());
	}
}
