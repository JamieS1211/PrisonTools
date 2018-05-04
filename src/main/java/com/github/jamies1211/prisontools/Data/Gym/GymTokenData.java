package com.github.jamies1211.prisontools.Data.Gym;

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
public class GymTokenData extends AbstractData<GymTokenData, ImmutableGymTokenData> {

	// TypeTokens needed for creating Keys (can be created inline too)
	private static TypeToken<Boolean> TT_Boolean = new TypeToken<Boolean>() {};
	private static TypeToken<Value<Boolean>> TTV_Boolean= new TypeToken<Value<Boolean>>() {};
	private static TypeToken<String> TT_String = new TypeToken<String>() {};
	private static TypeToken<Value<String>> TTV_String= new TypeToken<Value<String>>() {};

	// Keys for this custom Data
	public static Key<Value<Boolean>> IS_GYM_TOKEN = makeSingleKey(TT_Boolean, TTV_Boolean, of("Boolean"), "prisonTools:gymTokenData:boolean", "Boolean");
	public static Key<Value<String>> GYM_RANK = makeSingleKey(TT_String, TTV_String, of("String"), "prisonTools:gymTokenData:string", "String");

	// Live Data in this instance
	private boolean isGymToken;
	private String gymRank;

	// For DataBuilder and personal use
	public GymTokenData() {
	}

	public GymTokenData(Boolean isGymToken, String gymRank) {
		this.isGymToken = isGymToken;
		this.gymRank = gymRank;
		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		// Getter, Setter and ValueGetter for IS_GYM_TOKEN
		registerFieldGetter(IS_GYM_TOKEN, GymTokenData.this::getIsGymToken);
		registerFieldSetter(IS_GYM_TOKEN, GymTokenData.this::setIsGymToken);
		registerKeyValue(IS_GYM_TOKEN, GymTokenData.this::isGymToken);
		// Getter, Setter and ValueGetter for GYM_RANK
		registerFieldGetter(GYM_RANK, GymTokenData.this::getGymRank);
		registerFieldSetter(GYM_RANK, GymTokenData.this::setGymRank);
		registerKeyValue(GYM_RANK, GymTokenData.this::gymRank);
	}

	// Create immutable version of this
	@Override
	public ImmutableGymTokenData asImmutable() {
		return new ImmutableGymTokenData(this.isGymToken, this.gymRank);
	}

	// Fill Data using DataHolder and MergeFunction
	@Override
	public Optional<GymTokenData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<Boolean> isGymToken = dataHolder.get(IS_GYM_TOKEN);
		Optional<String> gymRank = dataHolder.get(GYM_RANK);
		// Only apply if the custom Data is present
		if (isGymToken.isPresent() && gymRank.isPresent()) {
			GymTokenData data = this.copy();
			data.isGymToken = isGymToken.get();
			data.gymRank = gymRank.get();

			// merge Data
			data = overlap.merge(this, data);
			if (data != this) {
				this.isGymToken = data.isGymToken;
				this.gymRank = data.gymRank;
			}

			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Fill Data using DataContainer
	@Override
	public Optional<GymTokenData> from(DataContainer container) {
		Optional<Boolean> isGymToken = container.getBoolean(IS_GYM_TOKEN.getQuery());
		Optional<String> gymRank = container.getString(GYM_RANK.getQuery());
		// Only apply if the custom Data is present
		if (isGymToken.isPresent() && gymRank.isPresent()) {
			this.isGymToken = isGymToken.get();
			this.gymRank = gymRank.get();
			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Create copy of this
	@Override
	public GymTokenData copy() {
		return new GymTokenData(this.isGymToken, this.gymRank);
	}

	// Content Version (may be used for updating custom Data later)
	@Override
	public int getContentVersion() {
		return 1;
	}

	// !IMPORANT! Override toContainer and set your custom Data
	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(IS_GYM_TOKEN, getIsGymToken()).set(GYM_RANK, getGymRank());
	}

	// Getters

	private Boolean getIsGymToken() {
		return this.isGymToken;
	}

	private String getGymRank() {
		return this.gymRank;
	}

	// Setters

	private void setIsGymToken(Boolean isGymToken) {
		this.isGymToken = isGymToken;
	}

	private void setGymRank(String gymRank) {
		this.gymRank = gymRank;
	}

	// ValueGetters

	private Value<Boolean> isGymToken() {
		return Sponge.getRegistry().getValueFactory().createValue(IS_GYM_TOKEN, getIsGymToken());
	}

	private Value<String> gymRank() {
		return Sponge.getRegistry().getValueFactory().createValue(GYM_RANK, getGymRank());
	}
}
