package com.walcron.springcloud.gateway.web.api.rest.uploader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import org.springframework.core.io.buffer.DataBuffer;

@Service
public class StorageService {

    Log log = LogFactory.getLog(this.getClass());

    public void store(DataBuffer dataBuffer) {
        log.info("mock storage");
    }
}
