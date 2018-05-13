package com.github.jamies1211.prisontools.Data.customData.Event;

import com.github.jamies1211.prisontools.Data.customData.MyKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

/**
 * Created by Jamie on 29/10/2016.
 */
public class ImmutableEventTokenData extends AbstractImmutableData<ImmutableEventTokenData, EventTokenData> {
	private boolean isEventToken;
	private String eventType;

	public ImmutableEventTokenData(boolean isEventToken, String eventType) {
		this.isEventToken = isEventToken;
		this.eventType = eventType;
		registerGetters();
	}

	@Override
	protected void registerGetters() {
		registerFieldGetter(MyKeys.IS_EVENT_TOKEN, () -> this.isEventToken);
		registerKeyValue(MyKeys.IS_EVENT_TOKEN, this::isEventToken);

		registerFieldGetter(MyKeys.EVENT_TYPE, () -> this.eventType);
		registerKeyValue(MyKeys.EVENT_TYPE, this::eventType);
	}

	public ImmutableValue<Boolean> isEventToken() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.IS_EVENT_TOKEN, isEventToken).asImmutable();
	}

	public ImmutableValue<String> eventType() {
		return Sponge.getRegistry().getValueFactory().createValue(MyKeys.EVENT_TYPE, eventType).asImmutable();
	}

	@Override
	public EventTokenData asMutable() {
		return new EventTokenData(isEventToken, eventType);
	}

	@Override
	public int getContentVersion() {
		return 1;
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(MyKeys.IS_EVENT_TOKEN.getQuery(), this.isEventToken)
				.set(MyKeys.EVENT_TYPE.getQuery(), this.eventType);
	}
}
