package dev.jeffersonfreitas.caixaki.report.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import dev.jeffersonfreitas.caixaki.utils.DateUtils;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportUtils implements Serializable{
	private static final long serialVersionUID = 1L;

	private static final String UNDERLINE = "_";
	private static final String FOLDER_REPORT = "/reports";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private static final String EXTENSION_ODS = "ods";
	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_HTML = "html";
	private static final String EXTENSION_PDF = "pdf";
	private static String SEPARATOR = File.separator;
	private static final int REPORT_PDF = 1;
	private static final int REPORT_EXCEL = 2;
	private static final int REPORT_HTML = 3;
	private static final int REPORT_PLAN_OPEN_OFFICE = 4;
	private static final String DOT = ".";
	private StreamedContent returnArchieve = null;
	private String pathArchieveReport = null;
	private JRExporter typeExported = null;
	private String extentionArchieveExported = "";
	private String pathSubreport = "";
	private File generatedArchieve = null;
	
	
	public StreamedContent reportGenerated(List<?> listDataBeanCollectionReport, HashMap reportParams, String reportName,
			String nameReportOut, int typeReport) throws Exception{
		
		//Cria a lista de collectionDataSouce de beans que carregam os dados para o relatório
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDataBeanCollectionReport);
		
		//Fornece o caminho fisico até a pasta (reports) que contém os relatórios compilados .jasper
		FacesContext context = FacesContext.getCurrentInstance();
		context.responseComplete();
		ServletContext scontext = (ServletContext) context.getExternalContext().getContext();
		String pathReport = scontext.getRealPath(FOLDER_REPORT);
		
		//Ex: c:/caixaki/reports/rel_bairro.jasper
		File file = new File(pathReport + SEPARATOR + reportName + DOT + "jasper");
		
		//Para geração de relatórios fora do eclipse
		if(pathReport == null 
				|| (pathReport != null && pathReport.isEmpty())
				|| !file.exists()) {
			pathReport = this.getClass().getResource(FOLDER_REPORT).getPath();
			SEPARATOR = "";
		}
		
		//Caminho para imagens
		reportParams.put("REPORT_PARAMS_IMG", pathReport);
		
		//Caminho completo até o relatório compilado indicado
		String pathReportJasper = pathReport + SEPARATOR + reportName + DOT + "jasper";
		
		//Carregar o relatório jasper indicado
		JasperReport reportJasper = (JasperReport) JRLoader.loadObjectFromFile(pathReportJasper);
		
		//Seta params SUBREPORT_DIR como caminho fisico para subreports
		pathSubreport = pathReport + SEPARATOR;
		reportParams.put("SUBREPORT_DIR", pathSubreport);
		
		//Carrega todo o arquivo compilado para a memória
		JasperPrint print = JasperFillManager.fillReport(reportJasper, reportParams, jrbcds);
		
		//Exportar o formado do relatório
		switch (typeReport) {
		case REPORT_PDF:
			typeExported = new JRPdfExporter();
			extentionArchieveExported = EXTENSION_PDF;
			break;
		case REPORT_HTML:
			typeExported = new JRHtmlExporter();
			extentionArchieveExported = EXTENSION_HTML;
			break;
		case REPORT_EXCEL:
			typeExported = new JRXlsExporter();
			extentionArchieveExported = EXTENSION_XLS;
			break;
		case REPORT_PLAN_OPEN_OFFICE:
			typeExported = new JROdsExporter();
			extentionArchieveExported = EXTENSION_ODS;
			break;
		default:
			typeExported = new JRPdfExporter();
			extentionArchieveExported = EXTENSION_PDF;
			break;
		}
		
		//Setar o nome do relatório
		nameReportOut += UNDERLINE + DateUtils.nowDateReportFormat();
		
		//Caminho relatorio exportado
		pathArchieveReport = pathReport + SEPARATOR + nameReportOut + DOT + extentionArchieveExported;
		
		//Criar novo file exportado
		generatedArchieve = new File(pathArchieveReport);
		
		//Preparar a impressao
		typeExported.setParameter(JRExporterParameter.JASPER_PRINT, print);
		
		//Nome do arquivo a ser exportado/impresso
		typeExported.setParameter(JRExporterParameter.OUTPUT_FILE, generatedArchieve);
		
		//Executar a exportação
		typeExported.exportReport();
		
		//Remove o arquivo do servidor após ser feito o donwload pelo usuário
		generatedArchieve.deleteOnExit();
		
		//Cria o inputStream para ser usado pelo Primefaces
		InputStream contentReport = new FileInputStream(generatedArchieve);
		
		//Faz o retorno da aplicação
		returnArchieve = new DefaultStreamedContent(contentReport, "application/"+extentionArchieveExported, 
				nameReportOut + DOT + extentionArchieveExported);
		return returnArchieve;
	}
	
}
