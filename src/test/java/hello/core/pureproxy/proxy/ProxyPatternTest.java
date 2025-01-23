package hello.core.pureproxy.proxy;

import hello.core.pureproxy.proxy.code.CacheProxy;
import hello.core.pureproxy.proxy.code.ProxyPatternClient;
import hello.core.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        ProxyPatternClient client = new ProxyPatternClient(new RealSubject());
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        ProxyPatternClient client = new ProxyPatternClient(new CacheProxy(new RealSubject()));
        client.execute();
        client.execute();
        client.execute();
    }

}
