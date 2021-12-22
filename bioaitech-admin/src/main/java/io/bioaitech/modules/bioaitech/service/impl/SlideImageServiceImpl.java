package io.bioaitech.modules.bioaitech.service.impl;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.bioaitech.modules.bioaitech.service.SlideImageService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;


@Service("tissueChipImgguidService")
public class SlideImageServiceImpl implements SlideImageService {
    @Value("${SlideServer.BaseUrl}")
    public String baseUrl;
    @Value("${SlideServer.ThumbnailCacheDir}")
    public String thumbnailCacheDir;

    //判断本地是否存在，如果存在从本地读取，如果不存在则从网络获取，并保存到本地,如果response不为空则把图像返回到response
    @Override
    public boolean getSlideThumbnail(String slideId, HttpServletResponse response) {
        boolean flag = false;
        if(slideId == null || !slideId.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}")) {
            //slideId格式不正确
            createWordsImage("500",200,263,response);
            return flag;
        }

        byte[] data = getSlideThumbnailBytes(slideId, response);
        if (data.length > 0) {
            flag = true;
            if (null != response) {
                try {
                    OutputStream out = response.getOutputStream();
                    response.setContentType("image/jpeg");
                    out.write(data);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public byte[] getSlideThumbnailBytes(String slideId, HttpServletResponse response) {
        Path slidePath = Paths.get(thumbnailCacheDir, slideId+"_thumbnail.jpg");
        byte[] data = new byte[0];
        if(slidePath != null && slidePath.toFile().isFile()) {
            try {
                data = Files.readAllBytes(slidePath);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                URL url = new URL(baseUrl+"MoticGallery/Slide/Thumbnail?id=" + slideId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10 * 1000);
                if(connection.getResponseCode() == 200) {
                    InputStream inputStream = connection.getInputStream();
                    data = readInputStream(inputStream);
                    File dest = slidePath.toFile();
                    //判断文件父目录是否存在
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    if (data != null) {
                        Files.write(slidePath, data);
                    }
                }else{
                    createWordsImage("404", 200, 163, response);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Override
    public boolean saveSlideThumbnail(String slideId) {
        return this.getSlideThumbnail(slideId, null);
    }

    @Override
    public boolean getSlideThumbnailWithQcode(String slideId, HttpServletResponse response) {
        boolean flag = false;
        if (null == slideId || !slideId.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}")) {
            //slideId 格式不正确
            createWordsImage("500", 200, 163, response);
            return flag;
        }

        Path slidePath = Paths.get(thumbnailCacheDir, slideId + "_thumbnail_withqcode.jpg");

        byte[] data = new byte[0];
        if (slidePath != null && slidePath.toFile().isFile()) {
            try {
                data = Files.readAllBytes(slidePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            byte[] thumbnaildata = getSlideThumbnailBytes(slideId, response);
            if (thumbnaildata.length > 0) {
                Resource resource = new ClassPathResource("/sourcefile/image/qcode/logoWithQcode_zkgh_163.jpg");
                byte[] thumbnaillogo = resource.readBytes();

                //将b作为输入流；
                ByteArrayInputStream thumb = new ByteArrayInputStream(thumbnaildata);
                ByteArrayInputStream thumblogo = new ByteArrayInputStream(thumbnaillogo);

                try {
                    BufferedImage[] images = new BufferedImage[2];
                    images[0] = ImageIO.read(thumblogo);
                    images[1] = ImageIO.read(thumb);

                    int[][] ImageArrays = new int[images.length][];
                    for (int i = 0; i < images.length; i++) {
                        int width = images[i].getWidth();
                        int height = images[i].getHeight();
                        ImageArrays[i] = new int[width * height];
                        ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
                    }

                    int newHeight = 0;
                    int newWidth = 0;
                    for (int i = 0; i < images.length; i++) {
                        newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                        newWidth += images[i].getWidth();
                    }
                    if (newWidth < 1) {
                        return false;
                    }


                    // 生成新图片
                    BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                    int width_i = 0;
                    for (int i = 0; i < images.length; i++) {
                        ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
                                images[i].getWidth());
                        width_i += images[i].getWidth();
                    }
                    //输出想要的图片
                    File dest = slidePath.toFile();
                    //判断文件父目录是否存在
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    ImageIO.write(ImageNew, "jpg", dest);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(ImageNew, "jpg", out);
                    data = out.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (data.length > 0) {
            flag = true;
            if (null != response) {
                try {
                    OutputStream out = response.getOutputStream();
                    response.setContentType("image/jpeg");
                    out.write(data);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public byte[] readInputStream(InputStream inStream) throws Exception {

        BufferedImage bufImage = ImageIO.read(inStream);
        int width = bufImage.getWidth();
        int height = bufImage.getHeight();

        /*调整后的图片的宽度和高度*/
        int toWidth = (int) ((163.0 / height) * width);
        int toHeight = 163;

        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(bufImage.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(result, "jpg", out);
        byte[] data = out.toByteArray();
        return data;
    }

    public void createWordsImage(String str, Integer width, Integer height, HttpServletResponse response) {
        Font font = new Font("宋体", Font.BOLD, 30);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0,0, width, height);
        g.setColor(Color.white);
        g.fillRect(0,0,width,height);
        g.setColor(Color.DARK_GRAY);
        g.setFont(font);
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int asc = fm.getAscent();
        int desc = fm.getDescent();
        int y = (clip.height - (asc + desc))/2+asc;
        g.drawString(str, (clip.width - fm.stringWidth(str))/2,y);
        g.dispose();
        try {
            ImageIO.write(image,"png", response.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
