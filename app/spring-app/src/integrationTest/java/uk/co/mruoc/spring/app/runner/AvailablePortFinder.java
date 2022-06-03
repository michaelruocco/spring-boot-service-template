package uk.co.mruoc.spring.app.runner;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailablePortFinder {

    public static int findAvailableTcpPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            int port = socket.getLocalPort();
            log.info("found free port {}", port);
            return port;
        } catch (IOException e) {
            throw new UncheckedIOException("cannot find free port", e);
        }
    }
}
