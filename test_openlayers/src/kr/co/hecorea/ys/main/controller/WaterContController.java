package kr.co.hecorea.ys.main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.files.ShpFiles;
import org.geotools.data.shapefile.shp.ShapefileException;
import org.geotools.data.shapefile.shp.ShapefileReader;
import org.geotools.data.shapefile.shp.ShapefileReader.Record;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.ibatis.session.SqlSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.hecorea.ys.main.dao.WaterContDAO;



@Controller
@RequestMapping("/water/*")
public class WaterContController {
	@Autowired
	@Resource(name="sqlSessionPostgre")
	private SqlSession sqlSessionPostgre;
	
	private final Logger log = Logger.getLogger(getClass());
	
	private final String shpPath = "C:\\workspace\\eclipse_workspace\\test\\test_openlayers\\WebContent\\shp\\";
	
	/**
	 * main ?????????
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping("/intro.do")
	public String intro(HttpServletResponse response) throws Exception {
		try {
			// ?????????????????? ?????? ?????????. https????????? ??????
			// response.setHeader("Set-Cookie", "SameSite=None; Secure");
		} catch(Exception e) {
			log.error(e.toString());
		}
		return "main/intro";
	}
	
	
	/**
	 * geo data ????????????
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping("/selectData.do")
	public String selectData(HttpServletRequest req, Model model) throws Exception {
		try {
			WaterContDAO waterContDAO = sqlSessionPostgre.getMapper(WaterContDAO.class);
			
			model.addAttribute("geojson", waterContDAO.selectData());
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		return "jsonView";
	}
	
	
	/**
	 * upload test
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/test.do", method = RequestMethod.POST)
	public String test(HttpServletRequest req, Model model) throws Exception {
		ArrayList<HashMap<String, String>> param = new ArrayList<HashMap<String, String>>();
		model.addAttribute("state", "init");
		
		// ?????? ????????? ??????
		String filePath = shpPath;
		
		int size = 1024 * 1024 * 500; // ?????? ????????? ?????? : 500Mb
		String fileNm = "";    //  ????????? ????????? ?????? ????????? ????????? ?????? ???????????? ??????
		String realFileNm = "";    // ???????????? ?????? ??????
		
		try {
			// DefaultFileRenamePolicy ????????? ????????? ????????? ????????? ?????? ????????? ???
			// request, ??????????????????, ??????, ???????????????, ?????????????????? ?????? ??????
			MultipartRequest multi = new MultipartRequest(req, filePath, size, "utf-8", new DefaultFileRenamePolicy());
			
			// param.put("contents", multi.getParameter("contents"));
			
			// ????????? ?????? ?????????????????? ????????????.
			Enumeration<?> files = multi.getFileNames();
			
			if(!files.hasMoreElements()) {
				model.addAttribute("state", "no_file");
				return "jsonView";
			}
			
			String str = (String)files.nextElement();
			
			//????????? ????????? ???????????? ??? ????????? ?????? ?????? 1,2,3 ?????? ????????? ?????? ?????? ???????????? ????????????.
			// ?????? ????????? ????????? FilesystemName????????? ?????? ??? ?????? ????????? ????????????. (?????? ??????)
			fileNm = multi.getFilesystemName(str);
			//?????? ?????? ????????? ????????????.
			realFileNm = multi.getOriginalFileName(str);
			
			String tempDirName = FilenameUtils.getBaseName(fileNm);
			String tempFileName = FilenameUtils.getBaseName(realFileNm);
			String fileExtension = FilenameUtils.getExtension(fileNm);
			
			// zip????????? ????????????.
			if(!"zip".equals(fileExtension.toLowerCase())) {
				deleteFile(filePath+fileNm);
				model.addAttribute("state", "no_zip");
				return "jsonView";
			}
			
			Path source = Paths.get(filePath+fileNm);
			Path target = Paths.get(filePath+tempDirName);
			
			Files.createDirectory(target);
			unzipFile(source, target);

			String targetChildren = filePath+tempDirName+File.separator+tempFileName;
			param = findShape(targetChildren);
			
			deleteFile(filePath+tempDirName);
			
			// ???????????? ?????????, ????????? ?????? ?????? shp ????????? ????????? ?????????
			if(param.isEmpty()) {
				model.addAttribute("state", "no_data");
				return "jsonView";
			}
			
			WaterContDAO waterContDAO = sqlSessionPostgre.getMapper(WaterContDAO.class);
			waterContDAO.insertGeom(param);
		} catch(Exception e) {
			log.error(e.toString());
			model.addAttribute("state", "error");
			return "jsonView";
		}
		
		model.addAttribute("state", "success");
		return "jsonView";
	}
	
	
	// ?????? ????????????
	private void unzipFile(Path sourceZip, Path targetDir) {
		try{
			FileInputStream fi = new FileInputStream(sourceZip.toFile());
			ArchiveInputStream ai = new ArchiveStreamFactory().createArchiveInputStream("zip", fi);
			
			ZipArchiveEntry entry;
			
			while ((entry = (ZipArchiveEntry)ai.getNextEntry()) != null) {
				boolean isDirectory = false;
				if (entry.getName().endsWith(File.separator)) {
					isDirectory = true;
				}

				Path newPath = zipSlipProtect(entry, targetDir);
				if (isDirectory) {
					Files.createDirectories(newPath);
				} else {
					if (newPath.getParent() != null) {
						if (Files.notExists(newPath.getParent())) {
							Files.createDirectories(newPath.getParent());
						}
					}
					// copy files
					Files.copy(ai, newPath, StandardCopyOption.REPLACE_EXISTING);
				}
			}
			
			ai.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		}
	}

	
	// ?????? ???????????? ????????????
	private Path zipSlipProtect(ZipEntry zipEntry, Path targetDir)
	throws IOException {
		// test zip slip vulnerability
		Path targetDirResolved = targetDir.resolve(zipEntry.getName());

		// make sure normalized file still has targetDir as its prefix
		// else throws exception
		Path normalizePath = targetDirResolved.normalize();
		if (!normalizePath.startsWith(targetDir)) {
			throw new IOException("Bad zip entry: " + zipEntry.getName());
		}
		return normalizePath;
	}
	
	
	// ?????? ??????
	private void deleteFile(String fullFileName){
		File file = new File(fullFileName);
		try {
	    	if( file.exists() ){ //????????????????????????
	    		if(file.isDirectory()){ //????????? ?????????????????? ??????
	    			File[] files = file.listFiles();
	    			for( int i=0; i<files.length; i++){
	    				deleteFile(files[i].getCanonicalPath());
	    			}
	    		}
	    		if(file.delete()){
	    			// System.out.println(file.getName()+"???????????? ??????");
	    		}else{
	    			System.out.println(file.getName()+"???????????? ??????");
	    		}
	    		
	    	}else{
	    		System.out.println(fullFileName+" ????????? ???????????? ????????????.");
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private ArrayList<HashMap<String, String>> findShape(String target){
		ArrayList<HashMap<String, String>> returnVal = new ArrayList<HashMap<String, String>>();
		
		try {
			File f = new File(target+".shp");
			if(!f.exists()) {
				System.out.println(target+".shp"+" ????????? ????????????.");
				return returnVal;
			}
			
			// shp ???????????? geom, dbf ??????
			ShapefileReader r = null;
			DbaseFileReader d = null;
			ShpFiles shpFile = new ShpFiles(f);
			
			GeometryFactory geometryFactory = new GeometryFactory();
			r = new ShapefileReader(shpFile, true, false, geometryFactory);
			d = new DbaseFileReader(shpFile, false, Charset.forName("x-windows-949"));
			
			// ????????? ??????
			DbaseFileHeader header = d.getHeader();
			int numFields = header.getNumFields();
			HashMap<String, Integer> intColumn = new HashMap<String, Integer>();
			Set<String> set = new HashSet<String>();
			set.add("OBJECTID");
			set.add("CTP_KOR_NM");
			
			for(int iField=0; iField < numFields; ++iField) {
				String fieldName = header.getFieldName(iField);
				if(set.contains(fieldName)) intColumn.put(fieldName, iField);
			}
			
			if(set.size() != intColumn.size()) {
				System.out.println("?????? ?????? ??????.");
				r.close();
				d.close();
				return returnVal;
			}

			// ????????? ??????
			while (r.hasNext() && d.hasNext()) {
				HashMap<String, String> tempColumn = new HashMap<String, String>();
				Record record = r.nextRecord();
				Geometry shape = (Geometry)record.shape();
				
				Object[] values = d.readEntry();
				for( String key : intColumn.keySet() ){
					tempColumn.put(key, values[intColumn.get(key)].toString());
				}
				
				tempColumn.put("geom", shape.toString());
				returnVal.add(tempColumn);
			}
			
			r.close();
			d.close();
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();   
		} catch (ShapefileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		return returnVal;
	}
	
}
