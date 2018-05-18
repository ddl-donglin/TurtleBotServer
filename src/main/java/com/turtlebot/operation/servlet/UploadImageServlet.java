package com.turtlebot.operation.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;

public class UploadImageServlet extends HttpServlet {



	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		接收图片
		uploadImage(request, response);
//		接收图片与用户Id
//		changeUserImage(request, response);
		//将图片转移并重命名
        String res = "error";
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", res);
		try {
            res = moveAndRename();
            resultObj.put("result", res);
        } catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
		    response.getWriter().print(resultObj.toString());
        }
	}

	private String moveAndRename() throws InterruptedException, IOException {

		Runtime runtime = Runtime.getRuntime();
		Process pro = runtime.exec("");
		int status = pro.waitFor();
		if (status != 0){
		    //脚本执行出错
            System.out.println("Failed to call shell's command ");
            return "error";
        }else{
		    //脚本执行成功
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringBuffer strbr = new StringBuffer();
            String line;
            while ((line = br.readLine())!= null)
            {
                strbr.append(line).append("\n");
            }

            String result = strbr.toString();
            System.out.println(result);
            return result;
        }

	}

	// 上传图片文件
	private void uploadImage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String message = "";
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
		String wantedFilename = null;
		try{
			DiskFileItemFactory dff = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(dff);
			List<FileItem> items = sfu.parseRequest(request);
            // 获取上传字段
            FileItem imageFileitem = null;
			for (FileItem fileItem: items){
			    if ("wantedFilename".equals(fileItem.getFieldName())){
			        wantedFilename = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8");
                    System.out.println("wantedFilename: "+wantedFilename);
                }else if ("image".equals(fileItem.getFieldName())){
			        imageFileitem = fileItem;
                }
            }

			// 更改文件名为唯一的
			String filename = imageFileitem.getName();
			if (filename != null) {
				filename = IdGenertor.generateGUID() + "." + FilenameUtils.getExtension(filename);
			}
			// 生成存储路径
			String storeDirectory = getServletContext().getRealPath("/files/images");
			File file = new File(storeDirectory);
			if (!file.exists()) {
				file.mkdir();
			}
//			String path = genericPath(filename, storeDirectory);
			// 处理文件的上传
			try {
			    if (wantedFilename == null || "".equals(wantedFilename))
			        wantedFilename = filename;
                imageFileitem.write(new File(storeDirectory, wantedFilename));

				String filePath = "/files/images/" + wantedFilename;
				message = filePath;
			} catch (Exception e) {
			    e.printStackTrace();
				message = "error: upload file exception";
			}
		} catch (Exception e) {
		    e.printStackTrace();
			message = "error: upload file fail";
		} finally {
			response.getWriter().write(message);
		}
	}
	
	// 修改用户的图片
    private void changeUserImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        try{
            DiskFileItemFactory dff = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(dff);
            List<FileItem> items = sfu.parseRequest(request);
            for(FileItem item:items){
                if(item.isFormField()){
                    //普通表单
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();
                    System.out.println("name="+fieldName + ", value="+ fieldValue);
                } else {// 获取上传字段
                    // 更改文件名为唯一的
                    String filename = item.getName();
                    if (filename != null) {
                        filename = IdGenertor.generateGUID() + "." + FilenameUtils.getExtension(filename);
                    }
                    // 生成存储路径
                    String storeDirectory = getServletContext().getRealPath("/files/images");
                    File file = new File(storeDirectory);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String path = genericPath(filename, storeDirectory);
                    // 处理文件的上传
                    try {
                        item.write(new File(storeDirectory + path, filename));

                        String filePath = "/files/images" + path + "/" + filename;
                        System.out.println("filePath="+filePath);
                        message = filePath;
                    } catch (Exception e) {
                        message = "上传图片失败";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "上传图片失败";
        } finally {
            response.getWriter().write(message);
        }
    }
	
	//计算文件的存放目录
	private String genericPath(String filename, String storeDirectory) {
		int hashCode = filename.hashCode();
		int dir1 = hashCode&0xf;
		int dir2 = (hashCode&0xf0)>>4;

		String dir = "/"+dir1+"/"+dir2;
		
		File file = new File(storeDirectory,dir);
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
//        uploadImage(request, response);
	}


}
