package com.st.dtit.cam.authentication.utility.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEventLogger implements CacheEventListener {

        private static final Logger LOGGER = LoggerFactory.getLogger(CacheEventLogger.class);

        @Override
        public void onEvent(CacheEvent cacheEvent) {
                LOGGER.info("Catch Event : {}  {} -> {}", cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
        }
}
