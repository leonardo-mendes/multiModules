package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.queues.Receiver;

@RestController
@RequestMapping(value = "/queue")
public class QueueController {

    @Autowired
    private Receiver receiver;

    @GetMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAll() {
        receiver.run();
    }
}
