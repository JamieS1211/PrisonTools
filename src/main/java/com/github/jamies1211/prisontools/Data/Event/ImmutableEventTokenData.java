package com.github.jamies1211.prisontools.Data.Event;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

/**
 * Created by Jamie on 29/10/2016.
 */
public class ImmutableEventTokenData extends AbstractImmutableData<ImmutableEventTokenData, EventTokenData> {

	private Boolean isEventToken;
	private String eventType;

	public ImmutableEventTokenData(Boolean isEventToken, String eventType) {
		this.isEventToken = isEventToken;
		this.eventType = eventType;
	}

	@Override
	protected void registerGetters() {
		// Getter and ValueGetter for IS_EVENT_TOKEN
		registerFieldGetter(EventTokenData.IS_EVENT_TOKEN, this::getIsEventToken);
		registerKeyValue(EventTokenData.IS_EVENT_TOKEN, this::isEventToken);
		// Getter and ValueGetter for EVENT_TYPE
		registerFieldGetter(EventTokenData.EVENT_TYPE, this::getEventType);
		registerKeyValue(EventTokenData.EVENT_TYPE, this::eventType);
	}

	// Create mutable version of this
	@Override
	public EventTokenData asMutable() {
		return new EventTokenData(this.isEventToken, this.eventType);
	}

	// Content Version (may be used for updating custom Data later)
	@Override
	public int getContentVersion() {
		return 1;
	}

	// !IMPORANT! Override toContainer and set your custom Data
	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(EventTokenData.IS_EVENT_TOKEN, getIsEventToken()).set(EventTokenData.EVENT_TYPE, getEventType());
	}

	// Getters

	private Boolean getIsEventToken() {
		return this.isEventToken;
	}

	private String getEventType() {
		return this.eventType;
	}

	// Value Getters
	private ImmutableValue<Boolean> isEventToken() {
		return Sponge.getRegistry().getValueFactory().createValue(EventTokenData.IS_EVENT_TOKEN, this.isEventToken).asImmutable();
	}

	private ImmutableValue<String> eventType() {
		return Sponge.getRegistry().getValueFactory().createValue(EventTokenData.EVENT_TYPE, this.eventType).asImmutable();
	}
}