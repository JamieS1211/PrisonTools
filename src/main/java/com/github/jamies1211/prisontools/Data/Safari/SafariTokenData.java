package com.github.jamies1211.prisontools.Data.Safari;

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
public class SafariTokenData extends AbstractData<SafariTokenData, ImmutableSafariTokenData> {

	// TypeTokens needed for creating Keys (can be created inline too)
	private static TypeToken<Boolean> TT_Boolean = new TypeToken<Boolean>() {};
	private static TypeToken<Value<Boolean>> TTV_Boolean= new TypeToken<Value<Boolean>>() {};
	private static TypeToken<Double> TT_Double = new TypeToken<Double>() {};
	private static TypeToken<Value<Double>> TTV_Double = new TypeToken<Value<Double>>() {};
	private static TypeToken<Integer> TT_Integer = new TypeToken<Integer>() {};
	private static TypeToken<Value<Integer>> TTV_Integer = new TypeToken<Value<Integer>>() {};

	// Keys for this custom Data
	public static Key<Value<Boolean>> IS_SAFARI_TOKEN = makeSingleKey(TT_Boolean, TTV_Boolean, of("Boolean"), "prisonTools:safariTokenData:boolean", "Boolean");
	public static Key<Value<Double>> SAFARI_TIME = makeSingleKey(TT_Double, TTV_Double, of("Double"), "prisonTools:safariTokenData:double", "Double");
	public static Key<Value<Integer>> SAFARI_ZONE = makeSingleKey(TT_Integer, TTV_Integer, of("Integer"), "prisonTools:safariTokenData:integer", "Integer");

	// Live Data in this instance
	private boolean isSafariToken;
	private Double safariTime;
	private Integer safariZone;

	// For DataBuilder and personal use
	public SafariTokenData() {
	}

	public SafariTokenData(Boolean isEventToken, Double safariTime, Integer safariZone) {
		this.isSafariToken = isEventToken;
		this.safariTime = safariTime;
		this.safariZone = safariZone;
		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		// Getter, Setter and ValueGetter for IS_SAFARI_TOKEN
		registerFieldGetter(IS_SAFARI_TOKEN, SafariTokenData.this::getIsSafariToken);
		registerFieldSetter(IS_SAFARI_TOKEN, SafariTokenData.this::setIsSafariToken);
		registerKeyValue(IS_SAFARI_TOKEN, SafariTokenData.this::isSafariToken);
		// Getter, Setter and ValueGetter for SAFARI_TIME
		registerFieldGetter(SAFARI_TIME, SafariTokenData.this::getSafariTime);
		registerFieldSetter(SAFARI_TIME, SafariTokenData.this::setSafariTime);
		registerKeyValue(SAFARI_TIME, SafariTokenData.this::safariTime);
		// Getter, Setter and ValueGetter for SAFARI_ZONE
		registerFieldGetter(SAFARI_ZONE, SafariTokenData.this::getSafariZone);
		registerFieldSetter(SAFARI_ZONE, SafariTokenData.this::setSafariZone);
		registerKeyValue(SAFARI_ZONE, SafariTokenData.this::safariZone);
	}

	// Create immutable version of this
	@Override
	public ImmutableSafariTokenData asImmutable() {
		return new ImmutableSafariTokenData(this.isSafariToken, this.safariTime, this.safariZone);
	}

	// Fill Data using DataHolder and MergeFunction
	@Override
	public Optional<SafariTokenData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<Boolean> isSafariToken = dataHolder.get(IS_SAFARI_TOKEN);
		Optional<Double> safariTime = dataHolder.get(SAFARI_TIME);
		Optional<Integer> safariZone = dataHolder.get(SAFARI_ZONE);
		// Only apply if the custom Data is present
		if (isSafariToken.isPresent() && safariTime.isPresent() && safariZone.isPresent()) {
			SafariTokenData data = this.copy();
			data.isSafariToken = isSafariToken.get();
			data.safariTime = safariTime.get();
			data.safariZone = safariZone.get();

			// merge Data
			data = overlap.merge(this, data);
			if (data != this) {
				this.isSafariToken = data.isSafariToken;
				this.safariTime = data.safariTime;
			}

			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Fill Data using DataContainer
	@Override
	public Optional<SafariTokenData> from(DataContainer container) {
		Optional<Boolean> isSafariToken = container.getBoolean(IS_SAFARI_TOKEN.getQuery());
		Optional<Double> safariTime = container.getDouble(SAFARI_TIME.getQuery());
		Optional<Integer> safariZone = container.getInt(SAFARI_ZONE.getQuery());
		// Only apply if the custom Data is present
		if (isSafariToken.isPresent() && safariTime.isPresent() && safariZone.isPresent()) {
			this.isSafariToken = isSafariToken.get();
			this.safariTime = safariTime.get();
			this.safariZone = safariZone.get();
			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Create copy of this
	@Override
	public SafariTokenData copy() {
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
		return super.toContainer().set(IS_SAFARI_TOKEN, getIsSafariToken()).set(SAFARI_TIME, getSafariTime()).set(SAFARI_ZONE, getSafariZone());
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

	// Setters

	private void setIsSafariToken(Boolean isSafariToken) {
		this.isSafariToken = isSafariToken;
	}

	private void setSafariTime(Double safariTime) {
		this.safariTime = safariTime;
	}

	private void setSafariZone(Integer safariZone) {
		this.safariZone = safariZone;
	}

	// ValueGetters

	private Value<Boolean> isSafariToken() {
		return Sponge.getRegistry().getValueFactory().createValue(IS_SAFARI_TOKEN, getIsSafariToken());
	}

	private Value<Double> safariTime() {
		return Sponge.getRegistry().getValueFactory().createValue(SAFARI_TIME, getSafariTime());
	}

	private Value<Integer> safariZone() {
		return Sponge.getRegistry().getValueFactory().createValue(SAFARI_ZONE, getSafariZone());
	}
}
