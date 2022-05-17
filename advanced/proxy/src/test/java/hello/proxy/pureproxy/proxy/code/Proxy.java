package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Proxy implements Subject {

    private Subject target;
    private String cacheValue;

    public Proxy(Subject subject) {
        this.target = subject;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");
        if (cacheValue == null) {
            this.cacheValue = target.operation();
        }
        return this.cacheValue;
    }
}
