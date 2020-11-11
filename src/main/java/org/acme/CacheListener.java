package org.acme;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;

@Listener
public class CacheListener {

    @CacheEntryCreated
    public void entryCreated(CacheEntryCreatedEvent<String, Score> event) {
        System.out.printf("-- Entry for %s created \n", event.getType());
    }

    @CacheEntryModified
    public void entryUpdated(CacheEntryModifiedEvent<String, Score> event){
        System.out.printf("-- Entry for %s modified\n", event.getType());
    }
}