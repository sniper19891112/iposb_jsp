package com.iposb.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;

public class Captcha extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int width = 268;
        int height = 68;
        int fontSize = 33;
        int xGap = 30 ;
        int yGap = 23 ;
        String fontName = "Arial" ;
        Color gradiantStartColor = new Color(60, 60, 60); // dark grey
        Color gradiantEndColor = new Color(140, 140, 140); // light grey
        Color textColor =  new Color(255, 153, 0); // orange

        String[] newData = {"eybpw", "fatgt", "padmj", "kxvba", "wjryk", "tjwyb", "fgkwv", "mwrty"}; // you add more words or read them from db or something...

        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        GradientPaint gp = new GradientPaint(0, 0, gradiantStartColor , 0, height / 2, gradiantEndColor, true);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        Random r = new Random();

        for (int i = 0; i < width - 10; i = i + 25) {
            int q = Math.abs(r.nextInt()) % width;
            int colorIndex = Math.abs(r.nextInt()) % 200;
            g2d.setColor(new Color(colorIndex, colorIndex, colorIndex));
            g2d.drawLine(i, q, width, height);
            g2d.drawLine(q, i, i, height);
        }

        g2d.setColor(textColor);

        int index = Math.abs(r.nextInt()) % newData.length;

        String captcha = newData[index];
        request.getSession().setAttribute("Captcha", captcha);
        
//System.out.print(">>>>>captcha:"+captcha);

        int x = 0;
        int y = 0;

        for (int i = 0; i < newData[index].length(); i++) {
            Font font = new Font(fontName , Font.BOLD, fontSize);
            g2d.setFont(font);
            x += xGap + (Math.abs(r.nextInt()) % 15);
            y = yGap + Math.abs(r.nextInt()) % 20;

            g2d.drawChars(newData[index].toCharArray(), i, 1, x, y);
        }

        for (int i = 0; i < width - 10; i = i + 25) {
            int p = Math.abs(r.nextInt()) % width;
            int q = Math.abs(r.nextInt()) % width;
            int colorIndex = Math.abs(r.nextInt()) % 200;
            g2d.setColor(new Color(colorIndex, colorIndex, colorIndex));
            g2d.drawLine(p, 0, i + p, q);
            g2d.drawLine(p, 0, i + 25, height);
        }

        g2d.dispose();
        
        

        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        os.close();
    }

}