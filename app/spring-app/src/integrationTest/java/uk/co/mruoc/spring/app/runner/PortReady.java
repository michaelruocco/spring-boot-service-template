package uk.co.mruoc.spring.app.runner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PortReady implements Callable<Boolean> {

    private final String host;
    private final int port;

    public static PortReady local(int port) {
        return new PortReady("localhost", port);
    }

    @Override
    public Boolean call() {
        try {
            SocketAddress address = new InetSocketAddress(host, port);
            try (SocketChannel channel = SocketChannel.open()) {
                channel.configureBlocking(true);
                channel.connect(address);
                log.info("successfully connect to {} on port {}", host, port);
                return true;
            }
        } catch (IOException e) {
            log.error("failed to connect to {} on port {}", host, port, e);
            return false;
        }
    }
}
