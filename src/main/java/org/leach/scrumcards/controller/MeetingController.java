package org.leach.scrumcards.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.leach.scrumcards.controller.base.BaseController;
import org.leach.scrumcards.dto.Result;
import org.leach.scrumcards.util.KeyUtil;
import org.leach.scrumcards.util.QrcodeHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;

/**
 * @author Leach
 * @date 2017/9/25
 */
@RestController
@RequestMapping(value = "meeting/")
public class MeetingController extends BaseController {


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Result<String> create() {
        String meetingKey = KeyUtil.generateMeetingKey();
        return successResponse(meetingKey);
    }

    @RequestMapping(value = "memberQrcode", method = RequestMethod.GET)
    @ResponseBody
    public void memberQrcode(@RequestParam(defaultValue = "200", required = false) int width,
                             @RequestParam(defaultValue = "200", required = false) int height,
                             String url) throws IOException, WriterException {

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width or height can't be negative");
        }

        generate(url, width, height);
    }

    private void generate(String qrcodeUrl, int width, int height) throws WriterException, IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage image = QrcodeHelper.generateQrcodeImage(qrcodeUrl, width, height);

        ImageIO.write(image, "png", byteArrayOutputStream);

        HttpServletResponse response = getResponse();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(byteArrayOutputStream.toByteArray());
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
