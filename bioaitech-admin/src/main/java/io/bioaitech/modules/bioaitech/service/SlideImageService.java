package io.bioaitech.modules.bioaitech.service;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-12-16 14:43:28
 */
public interface SlideImageService {

    /*
    * 保存切片缩略图，如果成功则返回true
    * params: slideId
    * */
    boolean saveSlideThumbnail(String slideId);

    /*
    * 读取切片缩略图，成功则返回true
    * params: slideId response
    * */
    boolean getSlideThumbnail(String slideId, HttpServletResponse response);

    /*
    * 读取切片带有qcode 二维码
    * params: slideId response
    * */
    boolean getSlideThumbnailWithQcode(String slideId, HttpServletResponse response);
}

