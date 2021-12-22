package io.bioaitech.modules.bioaitech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.bioaitech.modules.bioaitech.service.SlideImageService;
import javax.servlet.http.HttpServletResponse;


/**
 *
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
@RestController
@RequestMapping("bioaitech/SlideImage")
public class SlideImageController {
    @Autowired
    private SlideImageService slideImageService;

    @RequestMapping("/thumbnail/{slideId}")
    public void thumbnail(@PathVariable String slideId, HttpServletResponse response) {
        slideImageService.getSlideThumbnail(slideId, response);
    }

    @RequestMapping("/thumbnailQcode/{slideId}")
    public void thumbnailQcode(@PathVariable String slideId, HttpServletResponse response) {
        slideImageService.getSlideThumbnailWithQcode(slideId, response);
    }
}
