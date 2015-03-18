/*
 * Copyright (c) 2015 Huawei, Inc and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.usc.manager.monitor;

import org.opendaylight.usc.manager.UscMonitorManager;
import org.opendaylight.usc.manager.api.UscEvent;
import org.opendaylight.usc.manager.api.UscMonitorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Channel monitor listener
 */
public class UscChannelListener implements UscMonitorListener {

    private static final Logger LOG = LoggerFactory.getLogger(UscChannelListener.class);

    private UscMonitorManager monitorManager;

    /**
     * create a Channel monitor listener using given monitor manager
     * 
     * @param monitorManager
     *            monitor manager
     */
    public UscChannelListener(UscMonitorManager monitorManager) {
        this.monitorManager = monitorManager;
    }

    @Override
    public void onEvent(UscEvent event) {
        if (monitorManager.getEventHandlerList().containsKey(event)) {
            UscEventHandler handler = monitorManager.getEventHandlerList().get(event);
            UscAsynchronousEventHandler.asynchronizedhandle(event, handler);
        } else {
            LOG.warn("Find a unknown event, the corresponding event handler is not register.EventType = "
                    + event.getClass().toString());
        }
    }

}