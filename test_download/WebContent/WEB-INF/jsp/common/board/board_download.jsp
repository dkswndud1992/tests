<%@ page contentType="charset=utf-8"%>
<%@ page import="java.util.*,java.io.*,java.net.*"%> 
<%
String filePath = (String)request.getAttribute("filePath");
String fileNameBefore = (String)request.getAttribute("fileName");
String fileName = new String(fileNameBefore.getBytes("UTF-8"), "UTF-8");
String originalFileNameBefore = (String)request.getAttribute("originalFileName");
String originalFileName = new String(originalFileNameBefore.getBytes("UTF-8"), "UTF-8");
String file = filePath + "/" + fileName;
String isFileZip = (String)request.getAttribute("isFileZip");

File f = new File(file); 
if(f.exists()){

	int filesize = (int)f.length(); 
	byte buff[] = new byte[2048]; 
	int bytesRead;

	try {
		out.clear();
		out=pageContext.pushBody();
		
		// 압축완료 확인 쿠키.(1시간)
		Cookie cookie = new Cookie("fileDownloadToken", "TRUE");
		cookie.setMaxAge(60 * 60);
		cookie.setPath("/");
		response.addCookie(cookie);
				
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition","attachment; filename=\""+ originalFileName+"\""); 
		
		String header = request.getHeader("User-Agent");

		// IE 분기.
		if (header.contains("MSIE") || header.contains("Trident")) {
			originalFileName = URLEncoder.encode(originalFileName,"UTF-8").replaceAll("\\+", "%20");
			response.setHeader("Content-Disposition", "attachment;filename=" + originalFileName + ";");
		} else {
			originalFileName = new String(originalFileName.getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFileName + "\"");
		}
		
		FileInputStream fin = new java.io.FileInputStream(f); 
		BufferedInputStream bis = new BufferedInputStream(fin);
		ServletOutputStream fout = response.getOutputStream(); 
		BufferedOutputStream bos = new BufferedOutputStream(fout);

		while((bytesRead = bis.read(buff)) != -1) { 
			bos.write(buff, 0, bytesRead); 
		}
		bos.flush();

		fin.close();
		fout.close(); 
		bis.close();
		bos.close();
		
		// 압축된.zip 삭제.
		if(isFileZip != null || "".equals(isFileZip)){
			f.delete();
		}
		
	} catch( IOException e){
		response.setContentType("text/html; charset=UTF-8");
		out.println("<script type='text/javascript'>alert('서버 점검중 입니다.'); history.back();</script>");
	}
} else {
	response.setContentType("text/html; charset=UTF-8");
	out.println("<script type='text/javascript'>alert('파일이 존재하지 않습니다.'); history.back();</script>");
	
}
%>