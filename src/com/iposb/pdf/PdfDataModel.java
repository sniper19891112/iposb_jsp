package com.iposb.pdf;

import javax.servlet.ServletOutputStream;

public class PdfDataModel {
	
	private ServletOutputStream pdf = null;

	
	
	public PdfDataModel(){
	}

	public ServletOutputStream getPdf() {
		return pdf;
	}

	public void setPdf(ServletOutputStream out) {
		this.pdf = out;
	}



}