package me.jamie.advanced.trace.template;

import me.jamie.advanced.trace.TraceStatus;
import me.jamie.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace logTrace) {
        this.trace = logTrace;
    }

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
