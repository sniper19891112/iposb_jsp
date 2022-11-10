package com.iposb.report;

import javax.servlet.ServletOutputStream;

public class ReportDataModel {
	
	private ServletOutputStream pdf = null;

	
	
	public ReportDataModel(){
	}

	public ServletOutputStream getPdf() {
		return pdf;
	}

	public void setPdf(ServletOutputStream out) {
		this.pdf = out;
	}



}