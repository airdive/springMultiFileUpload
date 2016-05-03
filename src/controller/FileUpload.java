package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileUpload {
	@RequestMapping("/moreFileUpload1")
	public String oneFileUpload(MultipartHttpServletRequest request,
			HttpServletResponse response) {
		try {
			System.out.println("username=" + request.getParameter("username"));

			Map<String, MultipartFile> fileMap = request.getFileMap();
			System.out.println("文件个数为：" + fileMap.size());
			Set<String> fileSet = fileMap.keySet();
			Iterator<String> fileNameIterator = fileSet.iterator();
			while (fileNameIterator.hasNext()) {
				String uploadFileName = fileNameIterator.next();
				System.out.println(uploadFileName);
				MultipartFile file = fileMap.get(uploadFileName);
				uploadFileName = file.getOriginalFilename();
				InputStream isRef = file.getInputStream();
				String targetDir = request.getSession().getServletContext()
						.getRealPath("/upload");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy_MM_dd_hh_mm_ss");
				String getDateString = sdf.format(new Date());
				File targetFile = new File(targetDir, "" + getDateString + "_"
						+ System.nanoTime() + "_" + uploadFileName);
				FileOutputStream fosRef = new FileOutputStream(targetFile);
				IOUtils.copy(isRef, fosRef);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index.jsp";

	}
}
