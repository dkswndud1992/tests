package project.main.common.controller;

import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import jxl.Workbook;
import jxl.write.Formula;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import project.main.common.dao.SituationDAO;

@Controller
@RequestMapping("/situationCont/*")
public class SituationContController {
	@Autowired
	@Resource(name="sqlSessionMysql")
	private SqlSession sqlSessionMySql;
	private final Logger log = Logger.getLogger(getClass());
	
    /**
	 *  selectMetList
	 * @param HttpServletRequest
	 * @param Model
	 * @return jsonView
	 * @throws Exception
	*/
	@RequestMapping(value = "/selectMetList.do", method = RequestMethod.GET)
	public String selectMetList(HttpServletRequest req, Model model) throws Exception {
		HashMap<String, Integer> selectParam = new HashMap<String, Integer>();
		ArrayList<HashMap<String, String>> returnParamList = new ArrayList<HashMap<String, String>>();
		int startNum = Objects.isNull(req.getParameter("selectPage")) ? 1 : Integer.parseInt(req.getParameter("selectPage"));
		int limitNum = 100;
		selectParam.put("startNum", startNum-1);
		selectParam.put("limitNum", limitNum);
		try {
			SituationDAO dao = sqlSessionMySql.getMapper(SituationDAO.class);
			returnParamList = dao.selectMetList(selectParam);
		} catch(Exception e) {
			log.error(e.toString());
		}
		model.addAttribute("returnParamList", returnParamList);
		return "jsonView";
	}
	
	/**
	 *  selectMetDownloadPd4ml(old, ajax문제)
	 * @param HttpServletRequest
	 * @param Model
	 * @return jsonView
	 * @throws Exception
	*/
	@RequestMapping(value = "/selectMetDownloadPd4ml.do")
	public String selectMetDownloadPd4ml(HttpServletRequest req, Model model) throws Exception {
		String urlstring = "http://localhost:8080/";
		File output = new File("C:/Users/jyahn/Desktop/output.pdf");
		if (!urlstring.startsWith("http://") && !urlstring.startsWith("file:")) {
			urlstring = "http://" + urlstring;
		}
		java.io.FileOutputStream fos = new java.io.FileOutputStream(output);
		
		PD4ML pd4ml = new PD4ML();
		try {                                                              
        	pd4ml.setPageSize(PD4Constants.A4 );
        } catch (Exception e) {
        	e.printStackTrace();
        }
		pd4ml.setPageInsets(new Insets(0, 0, 0, 0));
		pd4ml.setHtmlWidth(1200);
		// pd4ml.addStyle("http://localhost:8080/css/style.css", true);

        pd4ml.render( urlstring, fos );
		// css와 ajax, 한글깨짐 문제 해결 필요.
        // 서버에서 파일 생성 후 url연결로 보여준뒤 시간경과시 삭제 구현.
		model.addAttribute("returnParamList", "hi");
		return "jsonView";
	}
	
	/**
	 *  selectMetDownloadItextpdf(new, col오류, html읽을려면 jsoup필요)
	 * @param HttpServletRequest
	 * @param Model
	 * @return jsonView
	 * @throws Exception
	*/
	@RequestMapping(value = "/selectMetDownloadItextpdf.do")
	public String selectMetDownloadItextpdf(HttpServletRequest req, Model model) throws Exception {
		// PDF를 작성하는 html (document.body.innerHTML로 가져와 만들어야할듯.)
		String html = "<html>" +
		"<head></head>" +
		"<body>" +
		"<div>Hello world</div>" +
		"<div>명월입니다.</div>" +
		"</body>" +
		"</html>";
		// 파일 IO 스트림을 취득한다.
		try (FileOutputStream os = new FileOutputStream("C:/Users/jyahn/Desktop/output.pdf")) {
			// Pdf형식의 document를 생성한다.
			Document document = new Document(PageSize.A4, 10, 10, 10, 10);
			// PdfWriter를 취득한다.
			PdfWriter writer = PdfWriter.getInstance(document, os);
			// document Open한다.
			document.open();
			// css를 설정할 resolver 인스턴스 생성
			StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver();
			// Css 파일 설정 (css1.css 파일 설정)
			// https://csharp.hotexamples.com/examples/iTextSharp.tool.xml.css/StyleAttrCSSResolver/AddCssFile/php-styleattrcssresolver-addcssfile-method-examples.html
			try (FileInputStream cssStream = new FileInputStream("C:/Users/jyahn/Desktop/css1.css")) {
				cssResolver.addCss(XMLWorkerHelper.getCSS(cssStream));
			}
			// Css 파일 설정 (css2.css 파일 설정)
			try (FileInputStream cssStream = new FileInputStream("C:/Users/jyahn/Desktop/css2.css")) {
				cssResolver.addCss(XMLWorkerHelper.getCSS(cssStream));
			}
			// 폰트 설정
			XMLWorkerFontProvider font = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			// window 폰트 설정
			font.register("c:/windows/fonts/malgun.ttf", "MalgunGothic");
			// 폰트 인스턴스를 생성한다.
			CssAppliersImpl cssAppliers = new CssAppliersImpl(font);
			//htmlContext의 pipeline 생성. (폰트 인스턴스 생성)
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
			// pdf의 pipeline 생성.
			PdfWriterPipeline pdfPipeline = new PdfWriterPipeline(document, writer);
			// Html의pipeline을 생성 (html 태그, pdf의 pipeline설정)
			HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, pdfPipeline);
			// css의pipeline을 합친다.
			CssResolverPipeline cssResolverPipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
			//Work 생성 pipeline 연결
			XMLWorker worker = new XMLWorker(cssResolverPipeline, true);
			//Xml 파서 생성(Html를 pdf로 변환)
			XMLParser xmlParser = new XMLParser(true, worker, Charset.forName("UTF-8"));
			// 출력한다.
			try (StringReader strReader = new StringReader(html)) {
				xmlParser.parse(strReader);
			}
			// document의 리소스 반환
			document.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		model.addAttribute("returnParamList", "hloo");
		return "jsonView";
	}
	
	/**
	 *  selectMetDownloadPoi(old, html을 이용하는 방식이 아님. )
	 * @param HttpServletRequest
	 * @param Model
	 * @return jsonView
	 * @throws Exception
	*/
	@RequestMapping(value = "/selectMetDownloadPoi.do")
	public String selectMetDownloadPoi(HttpServletRequest req, Model model) throws Exception {
		Map<String, Object> map = null;
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ArrayList<String> columnList = new ArrayList<>();
		for(int i = 0 ; i < 10; i++) {
			map = new HashMap<String,Object>();
			map.put("seq", i+1);
			map.put("title", "제목"+i);
			map.put("content", "내용"+i);
			list.add(map);
		}
		if(list !=null && list.size() > 0) {
			Map<String, Object> m = list.get(0);
			for(String k : m.keySet()) {
				columnList.add(k);
			}
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("시트명");
		HSSFRow row = null;
		HSSFCell cell = null;
		if (list !=null && list.size()>0) {
			int i = 0;
			for(Map<String,Object> mapobject : list) {
				row= sheet.createRow((short) i );
				i++;
				if(columnList !=null && columnList.size() > 0 ) {
					for(int j = 0; j < columnList.size() ; j++) {
						cell = row.createCell(j);
						cell.setCellValue(String.valueOf(mapobject.get(columnList.get(j))));
					}
				}
			}
		}
		FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/jyahn/Desktop/output.xls");
		workbook.write(fileOutputStream);
		fileOutputStream.close();
		workbook.close();
		System.out.println("엑셀파일 만들기 성공!");
		
		model.addAttribute("returnParamList", "hloos");
		return "jsonView";
	}
	
	/**
	 *  selectMetDownloadJxl(new, html을 이용하는 방식이 아님. 개인적으로 poi보다는 용이하다고 생각함.)
	 * @param HttpServletRequest
	 * @param Model
	 * @return jsonView
	 * @throws Exception
	*/
	@RequestMapping(value = "/selectMetDownloadJxl.do")
	public String selectMetDownloadJxl(HttpServletRequest req, Model model) throws Exception {
		File file = new File("C:/Users/jyahn/Desktop/output.xls");
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		WritableWorkbook workbook = Workbook.createWorkbook(file);
		
		WritableSheet sheet = workbook.createSheet("jooyoun", 0);
		
		// Label label;
		jxl.write.Number number;
		Formula formula;
		for (int i = 0; i < 10 ; i++) {
			for (int j = 0; j < 3 ; j++) {
				// label = null;
				// label = new Label(j,i,"test ("+String.valueOf(i)+","+String.valueOf(j)+");");
				number = new jxl.write.Number(j,i,i+j);
				sheet.addCell(number);
			}
			formula = new Formula (3,i,"A"+(i+1)+"+B"+(i+1)+"+C"+(i+1));
			sheet.addCell(formula);
		}
		workbook.write();
		workbook.close();
		
		model.addAttribute("returnParamList", "hlood");
		return "jsonView";
	}
}


 