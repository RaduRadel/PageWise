package com.pagewise.observer;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationCenter {
    private final List<LibraryObserver> observers;
    private final Map<LibraryEvent.EventType, List<LibraryObserver>> eventTypeObservers;
    
    public NotificationCenter() {
        this.observers = new CopyOnWriteArrayList<>();
        this.eventTypeObservers = new HashMap<>();
        
        for (LibraryEvent.EventType eventType : LibraryEvent.EventType.values()) {
            eventTypeObservers.put(eventType, new CopyOnWriteArrayList<>());
        }
    }
    
    public void addObserver(LibraryObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void addObserver(LibraryObserver observer, LibraryEvent.EventType... eventTypes) {
        if (observer == null) return;
        
        addObserver(observer);
        
        if (eventTypes != null && eventTypes.length > 0) {
            for (LibraryEvent.EventType eventType : eventTypes) {
                List<LibraryObserver> typeObservers = eventTypeObservers.get(eventType);
                if (typeObservers != null && !typeObservers.contains(observer)) {
                    typeObservers.add(observer);
                }
            }
        }
    }
    
    public void removeObserver(LibraryObserver observer) {
        if (observer != null) {
            observers.remove(observer);
            
            for (List<LibraryObserver> typeObservers : eventTypeObservers.values()) {
                typeObservers.remove(observer);
            }
        }
    }

    public void notifyObservers(LibraryEvent event) {
        if (event == null) return;
        
        List<LibraryObserver> typeObservers = eventTypeObservers.get(event.getType());
        
        if (typeObservers != null) {
            for (LibraryObserver observer : typeObservers) {
                try {
                    observer.update(event);
                } catch (Exception e) {
                    System.err.println("Error notifying observer: " + e.getMessage());
                }
            }
        }
        
        for (LibraryObserver observer : observers) {
            if (observer.isInterestedIn(event.getType())) {
                try {
                    observer.update(event);
                } catch (Exception e) {
                    System.err.println("Error notifying observer: " + e.getMessage());
                }
            }
        }
    }
    
    public int getObserverCount() {
        return observers.size();
    }
    
    public int getObserverCount(LibraryEvent.EventType eventType) {
        List<LibraryObserver> typeObservers = eventTypeObservers.get(eventType);
        return typeObservers != null ? typeObservers.size() : 0;
    }

    public void clearObservers() {
        observers.clear();
        for (List<LibraryObserver> typeObservers : eventTypeObservers.values()) {
            typeObservers.clear();
        }
    }

    public List<LibraryObserver> getAllObservers() {
        return new ArrayList<>(observers);
    }
}
