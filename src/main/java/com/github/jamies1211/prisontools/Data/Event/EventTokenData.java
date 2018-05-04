package com.github.jamies1211.prisontools.Data.Event;

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
public class EventTokenData extends AbstractData<EventTokenData, ImmutableEventTokenData> {

	// TypeTokens needed for creating Keys (can be created inline too)
	private static TypeToken<Boolean> TT_Boolean = new TypeToken<Boolean>() {};
	private static TypeToken<Value<Boolean>> TTV_Boolean= new TypeToken<Value<Boolean>>() {};
	private static TypeToken<String> TT_String = new TypeToken<String>() {};
	private static TypeToken<Value<String>> TTV_String= new TypeToken<Value<String>>() {};

	// Keys for this custom Data
	public static Key<Value<Boolean>> IS_EVENT_TOKEN = makeSingleKey(TT_Boolean, TTV_Boolean, of("Boolean"), "prisonTools:eventTokenData:boolean", "Boolean");
	public static Key<Value<String>> EVENT_TYPE = makeSingleKey(TT_String, TTV_String, of("String"), "prisonTools:eventTokenData:string", "String");

	// Live Data in this instance
	private boolean isEventToken;
	private String eventType;

	// For DataBuilder and personal use
	public EventTokenData() {
	}

	public EventTokenData(Boolean isEventToken, String eventType) {
		this.isEventToken = isEventToken;
		this.eventType = eventType;
		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		// Getter, Setter and ValueGetter for IS_EVENT_TOKEN
		registerFieldGetter(IS_EVENT_TOKEN, EventTokenData.this::getIsEventToken);
		registerFieldSetter(IS_EVENT_TOKEN, EventTokenData.this::setIsEventToken);
		registerKeyValue(IS_EVENT_TOKEN, EventTokenData.this::isEventToken);
		// Getter, Setter and ValueGetter for EVENT_TYPE
		registerFieldGetter(EVENT_TYPE, EventTokenData.this::getEventType);
		registerFieldSetter(EVENT_TYPE, EventTokenData.this::setEventType);
		registerKeyValue(EVENT_TYPE, EventTokenData.this::eventType);
	}

	// Create immutable version of this
	@Override
	public ImmutableEventTokenData asImmutable() {
		return new ImmutableEventTokenData(this.isEventToken, this.eventType);
	}

	// Fill Data using DataHolder and MergeFunction
	@Override
	public Optional<EventTokenData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<Boolean> isEventToken = dataHolder.get(IS_EVENT_TOKEN);
		Optional<String> eventType = dataHolder.get(EVENT_TYPE);
		// Only apply if the custom Data is present
		if (isEventToken.isPresent() && eventType.isPresent()) {
			EventTokenData data = this.copy();
			data.isEventToken = isEventToken.get();
			data.eventType = eventType.get();

			// merge Data
			data = overlap.merge(this, data);
			if (data != this) {
				this.isEventToken = data.isEventToken;
				this.eventType = data.eventType;
			}

			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Fill Data using DataContainer
	@Override
	public Optional<EventTokenData> from(DataContainer container) {
		Optional<Boolean> isEventToken = container.getBoolean(IS_EVENT_TOKEN.getQuery());
		Optional<String> eventType = container.getString(EVENT_TYPE.getQuery());
		// Only apply if the custom Data is present
		if (isEventToken.isPresent() && eventType.isPresent()) {
			this.isEventToken = isEventToken.get();
			this.eventType = eventType.get();
			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Create copy of this
	@Override
	public EventTokenData copy() {
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
		return super.toContainer().set(IS_EVENT_TOKEN, getIsEventToken()).set(EVENT_TYPE, getEventType());
	}

	// Getters

	private Boolean getIsEventToken() {
		return this.isEventToken;
	}

	private String getEventType() {
		return this.eventType;
	}

	// Setters

	private void setIsEventToken(Boolean isEventToken) {
		this.isEventToken = isEventToken;
	}

	private void setEventType(String eventType) {
		this.eventType = eventType;
	}

	// ValueGetters

	private Value<Boolean> isEventToken() {
		return Sponge.getRegistry().getValueFactory().createValue(IS_EVENT_TOKEN, getIsEventToken());
	}

	private Value<String> eventType() {
		return Sponge.getRegistry().getValueFactory().createValue(EVENT_TYPE, getEventType());
	}
}
