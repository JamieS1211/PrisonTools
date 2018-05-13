package com.github.jamies1211.prisontools.Data.customData.Gym;

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
public class GymTokenData extends AbstractData<GymTokenData, ImmutableGymTokenData> {
	private boolean isGymToken;
	private String gymRank;

	public GymTokenData(Boolean isGymToken, String gymRank) {
		this.isGymToken = isGymToken;
		this.gymRank = gymRank;
		// you must call this!
		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MyKeys.IS_GYM_TOKEN, () -> this.isGymToken);
		registerFieldSetter(MyKeys.IS_GYM_TOKEN, id -> this.isGymToken = isGymToken);
		registerKeyValue(MyKeys.IS_GYM_TOKEN, this::isGymToken);

		registerFieldGetter(MyKeys.GYM_RANK, () -> this.gymRank);
		registerFieldSetter(MyKeys.GYM_RANK, id -> this.gymRank = gymRank);
		registerKeyValue(MyKeys.GYM_RANK, this::gymRank);
	}

	public Value<Boolean> isGymToken() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.IS_GYM_TOKEN, isGymToken);
	}

	public Value<String> gymRank() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.GYM_RANK, gymRank);
	}

	@Override
	public Optional<GymTokenData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<GymTokenData> otherData_ = dataHolder.get(GymTokenData.class);
		if (otherData_.isPresent()) {
			GymTokenData otherData = otherData_.get();
			GymTokenData finalData = overlap.merge(this, otherData);
			this.isGymToken = finalData.isGymToken;
			this.gymRank = finalData.gymRank;
		}
		return Optional.of(this);
	}

	// the double method isn't strictly necessary but makes implementing the builder easier
	@Override
	public Optional<GymTokenData> from(DataContainer container) {
		return from((DataView) container);
	}

	public Optional<GymTokenData> from(DataView view) {
		if (view.contains(MyKeys.IS_GYM_TOKEN.getQuery()) && view.contains(MyKeys.GYM_RANK.getQuery())) {
			this.isGymToken = view.getBoolean(MyKeys.IS_GYM_TOKEN.getQuery()).get();
			this.gymRank = view.getString(MyKeys.GYM_RANK.getQuery()).get();
			return Optional.of(this);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public GymTokenData copy() {
		return new GymTokenData(this.isGymToken, this.gymRank);
	}

	@Override
	public ImmutableGymTokenData asImmutable() {
		return new ImmutableGymTokenData(this.isGymToken, this.gymRank);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}

	// IMPORTANT this is what causes your data to be written to NBT
	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(MyKeys.IS_GYM_TOKEN.getQuery(), this.isGymToken)
				.set(MyKeys.GYM_RANK.getQuery(), this.gymRank);
	}
}
