package blockchain.baby.node.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tzeyong.
 */
@RestController
public class BcBlockController {
    @GetMapping("/block")
    public String getBlock() {
        return "Hello World";
    }
}
