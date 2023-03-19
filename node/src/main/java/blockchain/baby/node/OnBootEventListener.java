package blockchain.baby.node;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
/**
 * Created by tzeyong.
 */
@Component
public class OnBootEventListener {
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

        // to init and
    }
}
