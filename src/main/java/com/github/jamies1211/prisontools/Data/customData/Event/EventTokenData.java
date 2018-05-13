package com.github.jamies1211.prisontools.Data.customData.Event;

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
public class EventTokenData extends AbstractData<EventTokenData, ImmutableEventTokenData> {
	private boolean isEventToken;
	private String eventType;

	public EventTokenData(Boolean isEventToken, String eventType) {
		this.isEventToken = isEventToken;
		this.eventType = eventType;
		// you must call this!
		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		registerFieldGetter(MyKeys.IS_EVENT_TOKEN, () -> this.isEventToken);
		registerFieldSetter(MyKeys.IS_EVENT_TOKEN, id -> this.isEventToken = isEventToken);
		registerKeyValue(MyKeys.IS_EVENT_TOKEN, this::isEventToken);

		registerFieldGetter(MyKeys.EVENT_TYPE, () -> this.eventType);
		registerFieldSetter(MyKeys.EVENT_TYPE, id -> this.eventType = eventType);
		registerKeyValue(MyKeys.EVENT_TYPE, this::eventType);
	}

	public Value<Boolean> isEventToken() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.IS_EVENT_TOKEN, isEventToken);
	}

	public Value<String> eventType() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.EVENT_TYPE, eventType);
	}

	@Override
	public Optional<EventTokenData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<EventTokenData> otherData_ = dataHolder.get(EventTokenData.class);
		if (otherData_.isPresent()) {
			EventTokenData otherData = otherData_.get();
			EventTokenData finalData = overlap.merge(this, otherData);
			this.isEventToken = finalData.isEventToken;
			this.eventType = finalData.eventType;
		}
		return Optional.of(this);
	}

	// the double method isn't strictly necessary but makes implementing the builder easier
	@Override
	public Optional<EventTokenData> from(DataContainer container) {
		return from((DataView) container);
	}

	public Optional<EventTokenData> from(DataView view) {
		if (view.contains(MyKeys.IS_EVENT_TOKEN.getQuery()) && view.contains(MyKeys.EVENT_TYPE.getQuery())) {
			this.isEventToken = view.getBoolean(MyKeys.IS_EVENT_TOKEN.getQuery()).get();
			this.eventType = view.getString(MyKeys.EVENT_TYPE.getQuery()).get();
			return Optional.of(this);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public EventTokenData copy() {
		return new EventTokenData(this.isEventToken, this.eventType);
	}

	@Override
	public ImmutableEventTokenData asImmutable() {
		return new ImmutableEventTokenData(this.isEventToken, this.eventType);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}

	// IMPORTANT this is what causes your data to be written to NBT
	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(MyKeys.IS_EVENT_TOKEN.getQuery(), this.isEventToken)
				.set(MyKeys.EVENT_TYPE.getQuery(), this.eventType);
	}
}
