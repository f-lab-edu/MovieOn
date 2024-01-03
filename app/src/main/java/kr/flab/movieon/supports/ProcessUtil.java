package kr.flab.movieon.supports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class ProcessUtil {
    private static final Logger log = LoggerFactory.getLogger(ProcessUtil.class);

    private ProcessUtil() {
    }

    private static int getRandomPort() {
        return new Random().nextInt(50000) + 10000;
    }

    public static boolean isRunningPort(int port) throws IOException {
        return isRunning(executeGrepProcessCommand(port));
    }

    public static int findAvailablePort() {
        for (int i = 0; i < 1000; i++) {
            try {
                int port = getRandomPort();
                if (!isRunning(executeGrepProcessCommand(port))) {
                    return port;
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        throw new RuntimeException("Not Found Available port: 10000 ~ 65535");
    }

    private static boolean isRunning(Process p) {
        StringBuilder pidInfo = new StringBuilder();
        String line;
        try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return StringUtils.hasText(pidInfo.toString());
    }

    private static Process executeGrepProcessCommand(int port) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }
}
