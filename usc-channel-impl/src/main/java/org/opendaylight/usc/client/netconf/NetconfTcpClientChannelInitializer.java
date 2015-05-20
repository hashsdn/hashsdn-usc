package org.opendaylight.usc.client.netconf;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Promise;

import org.opendaylight.controller.netconf.client.NetconfClientSession;
import org.opendaylight.controller.netconf.client.NetconfClientSessionListener;
import org.opendaylight.controller.netconf.client.NetconfClientSessionNegotiatorFactory;
import org.opendaylight.controller.netconf.nettyutil.AbstractChannelInitializer;
import org.opendaylight.protocol.framework.SessionListenerFactory;

class NetconfTcpClientChannelInitializer extends AbstractChannelInitializer<NetconfClientSession> implements
        SessionListenerFactory<NetconfClientSessionListener> {

    private final NetconfClientSessionNegotiatorFactory negotiatorFactory;
    private final NetconfClientSessionListener sessionListener;

    NetconfTcpClientChannelInitializer(final NetconfClientSessionNegotiatorFactory negotiatorFactory,
            final NetconfClientSessionListener sessionListener) {
        this.negotiatorFactory = negotiatorFactory;
        this.sessionListener = sessionListener;
    }

    @Override
    protected void initializeSessionNegotiator(final Channel ch, final Promise<NetconfClientSession> promise) {
        ch.pipeline().addAfter(NETCONF_MESSAGE_DECODER, AbstractChannelInitializer.NETCONF_SESSION_NEGOTIATOR,
                negotiatorFactory.getSessionNegotiator(this, ch, promise));
    }

    @Override
    public NetconfClientSessionListener getSessionListener() {
        return sessionListener;
    }
}