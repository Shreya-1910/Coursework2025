package com.comp2042.events;

/**
 *Represents a move event that is triggered during the game. This event encapsulates the type of action.
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * @param eventType The type of event such as down,right,left and rotate.
     * @param eventSource The source of the event such as User and Thread.
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * @return The {@link EventType} representing the action.
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * @return The {@link EventSource} indicating whether the event was triggered by the user or the game thread.
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
