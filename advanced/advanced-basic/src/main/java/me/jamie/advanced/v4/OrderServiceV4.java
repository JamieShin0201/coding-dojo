package me.jamie.advanced.v4;

import lombok.RequiredArgsConstructor;
import me.jamie.advanced.trace.logtrace.LogTrace;
import me.jamie.advanced.trace.template.AbstractTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
    }
}
