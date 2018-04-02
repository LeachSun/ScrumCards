package org.leach.scrumcards.controller;

import org.leach.scrumcards.controller.base.BaseController;
import org.leach.scrumcards.dto.Result;
import org.leach.scrumcards.util.KeyUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leach
 * @date 2017/9/25
 */
@RestController
@RequestMapping(value = "metting/")
public class MettingController extends BaseController {


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Result<String> create() {
        String meetingKey = KeyUtil.generateMeetingKey();
        return successResponse(meetingKey);
    }
}
