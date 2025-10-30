package com.pagewise.observer;

public interface LibraryObserver {
    void update(LibraryEvent event);
    
    default LibraryEvent.EventType[] getInterestedEventTypes() {
        return null;
    }
    
    default boolean isInterestedIn(LibraryEvent.EventType eventType) {
        LibraryEvent.EventType[] interestedTypes = getInterestedEventTypes();
        if (interestedTypes == null || interestedTypes.length == 0) {
            return true;
        }
        
        for (LibraryEvent.EventType type : interestedTypes) {
            if (type == eventType) {
                return true;
            }
        }
        return false;
    }
}
