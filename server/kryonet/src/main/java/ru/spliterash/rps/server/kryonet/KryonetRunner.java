package ru.spliterash.rps.server.kryonet;

import com.esotericsoftware.kryonet.Server;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.spliterash.rps.common.kryo.KryoInitializer;

@Component
public class KryonetRunner implements ApplicationRunner, DisposableBean {


    public KryonetRunner(@Value("${kryonet.port}") int port, KryoListener listener) {
        this.port = port;
        this.listener = listener;
    }

    private final KryoListener listener;
    private final int port;

    private Server server;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        server = new Server();
        KryoInitializer.configureKryo(server.getKryo());

        server.start();

        server.bind(port);
        server.addListener(listener);
    }

    @Override
    public void destroy() throws Exception {
        if (server != null)
            server.close();
    }


}
