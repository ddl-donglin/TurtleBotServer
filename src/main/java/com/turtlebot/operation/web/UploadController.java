package com.turtlebot.operation.web;

import com.turtlebot.operation.dataobject.model.ImageFileModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @Auther: davidddl
 * @Date: 2018/5/17 20:52
 * @Description:
 */

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping(value = "/imageFile",method = RequestMethod.POST)
    @ResponseBody
    public void uploadImageFile(ImageFileModel imageFileModel, HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 生成存储路径
        String storeDirectory = request.getSession().getServletContext().getRealPath("/WEB-INF/files/images");
        File file = new File(storeDirectory);
        if (!file.exists()) {
            boolean flag = file.mkdir();
            System.out.println(flag);
        }
        MultipartFile imageFile = imageFileModel.getImage();
        String wantedFilename = imageFileModel.getWantedFilename();
//        if (wantedFilename == null || wantedFilename.equals(""))
            wantedFilename = imageFile.getOriginalFilename();
        File saveFile = new File(storeDirectory, wantedFilename);
//        if (!saveFile.exists())
//            saveFile.createNewFile();
        System.out.println("save as filename: " + wantedFilename);
        String result = saveFile.getAbsolutePath();
        try {
            imageFile.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.getWriter().print(result);
    }

}
