package cz.anona.snyverse.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TestController {

    @ApiOperation(value = "testing method", notes = "return region")
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String testRunningState() {
        return System.getProperty("user.timezone");
    }

}
