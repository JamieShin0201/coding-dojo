package me.jamie.advanced.v1;

import lombok.RequiredArgsConstructor;
import me.jamie.advanced.trace.TraceStatus;
import me.jamie.advanced.trace.hellotrace.HelloTraceV1;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepository;
    private final HelloTraceV1 trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.orderItem()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
