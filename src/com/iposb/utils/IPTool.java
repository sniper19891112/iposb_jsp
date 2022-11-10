package com.iposb.utils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;


public class IPTool {

	private IPTool(){}
	
	static Logger logger = Logger.getLogger(IPTool.class);
	static String resPath = "https://static.iposb.com";
	
	/**
	 * 依 IP 取得 user 的國籍代碼: MY, SG, TW, CN ... 或 名稱： Malaysia, Singapore, Taiwan, China ...
	 * @param request
	 * @param code, name
	 * @return
	 */
	public static String countryLookup(HttpServletRequest request, String typ) {
		
		String country = "";

		try {
			
			String sep = System.getProperty("file.separator");

		    String dir = "";
		    if(request != null && request.getSession() != null && request.getSession().getServletContext() != null){
		    	dir = request.getSession().getServletContext().getRealPath("/");
		    	
			    String dbfile = dir + "etc" + sep + "GeoLite2-Country.mmdb"; 

				File database = new File(dbfile);
				DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
				
				String ip = getUserIP(request);
				InetAddress ipAddress = InetAddress.getByName(ip);
				CountryResponse response = dbReader.country(ipAddress);		    
			    
			    Country countries = response.getCountry();
			    
			    if(typ.equals("code")){ //取得國家代碼
			    	country = countries.getIsoCode();
			    } else if(typ.equals("name")){ //取得國家名稱
			    	country = countries.getName();
			    }
			    
		    }
		}
		catch (IOException | GeoIp2Exception e) {
		    logger.error("IO Exception");
		    e.printStackTrace();
		}

		return country;
	}

	
	/**
	 * 根據IP顯示國旗
	 * 
	 * @param request
	 * @param IP
	 * @return
	 */
	public static String countryFlag(HttpServletRequest request, String IP) {
		
		String flagIMG = "";

		try {
			
			String sep = System.getProperty("file.separator");

		    String dir = "";
		    if(request != null && request.getSession() != null && request.getSession().getServletContext() != null){
		    	dir = request.getSession().getServletContext().getRealPath("/");
		    	
			    String dbfile = dir + "etc" + sep + "GeoLite2-Country.mmdb"; 

				File database = new File(dbfile);
				DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
				
				String ip = getUserIP(request);
				InetAddress ipAddress = InetAddress.getByName(ip);
				CountryResponse response = dbReader.country(ipAddress);		    
			      
			    Country countries = response.getCountry();
			    String code = countries.getIsoCode().toString().toLowerCase();
		    	String name = countries.getName().toString();
		    	flagIMG = "<img src='"+resPath+"/resources/common/img/flag/"+code+".gif' title='"+name+"' alt='"+name+"' align='absmiddle' />";

		    }
		}
		catch (IOException | GeoIp2Exception e) {
//		    logger.error("IO Exception");
//		    e.printStackTrace();
		    flagIMG = "<img src='"+resPath+"/resources/common/img/flag/--.gif' title='' alt='' align='absmiddle' />";
		}

		return flagIMG;
	}

	
	/**
	 * 取得 user 的真實 IP (排除掉proxy)
	 * @param request
	 * @return
	 */
	public static String getUserIP(HttpServletRequest request) {

	    //參考自：https://blog.chinaunix.net/u/22371/showart_408907.html　多级反向代理[Squid]下获取客户端真实 IP地址
		String ip = request.getHeader("x-forwarded-for") == null ? "" : request.getHeader("x-forwarded-for") ;
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getHeader("Proxy-Client-IP");
	    }
	    
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getRemoteAddr();
	    }
	    
	    
	    //預防部分自帶超過一組IP的訪客, eg: 192.168.0.1, 202.152.85.143, 202.152.82.165
	    //只取最後面一組的IP. i.e. 202.152.82.165
		if(ip.indexOf(",") != -1){
			List<String> ips = findIPAddresses(ip);
			if(ips.size()>0){
				ip = ips.get(0);
//				logger.info(">>>IP resolved to: " + ip);
			}
		}
	    
	    return ip;
	
	}
	
	
	/**
	 * 根據國碼取得國家名稱
	 * 
	 * @param countrycode
	 * @return
	 */
	public static String getCountryName (String countrycode){
		String countryName = "";

        if(countrycode.equals("AF")){ countryName = "Afghanistan";}
        if(countrycode.equals("AL")){ countryName = "Albania";}
        if(countrycode.equals("DZ")){ countryName = "Algeria";}
        if(countrycode.equals("AS")){ countryName = "American Samoa";}
        if(countrycode.equals("AD")){ countryName = "Andorra";}
        if(countrycode.equals("AO") ){ countryName = "Angola";}
        if(countrycode.equals("AI")){ countryName = "Anguilla";}
        if(countrycode.equals("AR")){ countryName = "Argentina";}
        if(countrycode.equals("AM")){ countryName = "Armenia";}
        if(countrycode.equals("AW")){ countryName = "Aruba";}
        if(countrycode.equals("AU")){ countryName = "Australia";}
        if(countrycode.equals("AT")){ countryName = "Austria";}
        if(countrycode.equals("AZ")){ countryName = "Azerbaijan";}
        if(countrycode.equals("BS")){ countryName = "Bahamas";}
        if(countrycode.equals("BH")){ countryName = "Bahrain";}
        if(countrycode.equals("BD")){ countryName = "Bangladesh";}
        if(countrycode.equals("BB")){ countryName = "Barbados";}
        if(countrycode.equals("BY")){ countryName = "Belarus";}
        if(countrycode.equals("BE")){ countryName = "Belgium";}
        if(countrycode.equals("BZ")){ countryName = "Belize";}
        if(countrycode.equals("BJ")){ countryName = "Benin";}
        if(countrycode.equals("BT")){ countryName = "Bhutan";}
        if(countrycode.equals("BO")){ countryName = "Bolivia";}
        if(countrycode.equals("BA")){ countryName = "Bosnia and Herzegovina";}
        if(countrycode.equals("BW")){ countryName = "Botswana";}
        if(countrycode.equals("BR")){ countryName = "Brazil";}
        if(countrycode.equals("BN")){ countryName = "Brunei";}
        if(countrycode.equals("BG")){ countryName = "Bulgaria";}
        if(countrycode.equals("BF")){ countryName = "Burkina Faso";}
        if(countrycode.equals("BI")){ countryName = "Burundi";}
        if(countrycode.equals("KH")){ countryName = "Cambodia";}
        if(countrycode.equals("CM")){ countryName = "Cameroon";}
        if(countrycode.equals("CA")){ countryName = "Canada";}
        if(countrycode.equals("CV")){ countryName = "Cape Verde";}
        if(countrycode.equals("CF")){ countryName = "Central African Republic";}
        if(countrycode.equals("TD")){ countryName = "Chad";}
        if(countrycode.equals("CL")){ countryName = "Chile";}
        if(countrycode.equals("CN")){ countryName = "China";}
        if(countrycode.equals("CO")){ countryName = "Colombia";}
        if(countrycode.equals("KM")){ countryName = "Comoros";}
        if(countrycode.equals("CR")){ countryName = "Costa Rica";}
        if(countrycode.equals("HR")){ countryName = "Croatia";}
        if(countrycode.equals("CU")){ countryName = "Cuba";}
        if(countrycode.equals("CY")){ countryName = "Cyprus";}
        if(countrycode.equals("CZ")){ countryName = "Czech Republic";}
        if(countrycode.equals("CD")){ countryName = "Democratic Republic of Congo";}
        if(countrycode.equals("DK")){ countryName = "Denmark";}
        if(countrycode.equals("DJ")){ countryName = "Djibouti";}
        if(countrycode.equals("DM")){ countryName = "Dominica";}
        if(countrycode.equals("DO")){ countryName = "Dominican Republic";}
        if(countrycode.equals("EC")){ countryName = "Ecuador";}
        if(countrycode.equals("EG")){ countryName = "Egypt";}
        if(countrycode.equals("SV")){ countryName = "El Salvador";}
        if(countrycode.equals("GQ")){ countryName = "Equatorial Guinea";}
        if(countrycode.equals("ER")){ countryName = "Eritrea";}
        if(countrycode.equals("EE")){ countryName = "Estonia";}
        if(countrycode.equals("ET")){ countryName = "Ethiopia";}
        if(countrycode.equals("FI")){ countryName = "Finland";}
        if(countrycode.equals("FR")){ countryName = "France";}
        if(countrycode.equals("PF")){ countryName = "French Polynesia";}
        if(countrycode.equals("GA")){ countryName = "Gabon";}
        if(countrycode.equals("GM")){ countryName = "Gambia";}
        if(countrycode.equals("GE")){ countryName = "Georgia";}
        if(countrycode.equals("DE")){ countryName = "Germany";}
        if(countrycode.equals("GH")){ countryName = "Ghana";}
        if(countrycode.equals("GI")){ countryName = "Gibraltar";}
        if(countrycode.equals("GR")){ countryName = "Greece";}
        if(countrycode.equals("GL")){ countryName = "Greenland";}
        if(countrycode.equals("GD")){ countryName = "Grenada";}
        if(countrycode.equals("GU")){ countryName = "Guam";}
        if(countrycode.equals("GT")){ countryName = "Guatemala";}
        if(countrycode.equals("GN")){ countryName = "Guinea";}
        if(countrycode.equals("GW")){ countryName = "Guinea-Bissau";}
        if(countrycode.equals("GY")){ countryName = "Guyana";}
        if(countrycode.equals("HT")){ countryName = "Haiti";}
        if(countrycode.equals("HN")){ countryName = "Honduras";}
        if(countrycode.equals("HK")){ countryName = "Hong Kong";}
        if(countrycode.equals("HU")){ countryName = "Hungary";}
        if(countrycode.equals("IS")){ countryName = "Iceland";}
        if(countrycode.equals("IN")){ countryName = "India";}
        if(countrycode.equals("ID")){ countryName = "Indonesia";}
        if(countrycode.equals("IR")){ countryName = "Iran";}
        if(countrycode.equals("IQ")){ countryName = "Iraq";}
        if(countrycode.equals("IE")){ countryName = "Ireland";}
        if(countrycode.equals("IL")){ countryName = "Israel";}
        if(countrycode.equals("IT")){ countryName = "Italy";}
        if(countrycode.equals("CI")){ countryName = "Ivory Coast";}
        if(countrycode.equals("JM")){ countryName = "Jamaica";}
        if(countrycode.equals("JP")){ countryName = "Japan";}
        if(countrycode.equals("JO")){ countryName = "Jordan";}
        if(countrycode.equals("KZ")){ countryName = "Kazakhstan";}
        if(countrycode.equals("KE")){ countryName = "Kenya";}
        if(countrycode.equals("KI")){ countryName = "Kiribati";}
        if(countrycode.equals("KW")){ countryName = "Kuwait";}
        if(countrycode.equals("KG")){ countryName = "Kyrgyzstan";}
        if(countrycode.equals("LA")){ countryName = "Laos";}
        if(countrycode.equals("LV")){ countryName = "Latvia";}
        if(countrycode.equals("LB")){ countryName = "Lebanon";}
        if(countrycode.equals("LS")){ countryName = "Lesotho";}
        if(countrycode.equals("LR")){ countryName = "Liberia";}
        if(countrycode.equals("LY")){ countryName = "Libya";}
        if(countrycode.equals("LI")){ countryName = "Liechtenstein";}
        if(countrycode.equals("LT")){ countryName = "Lithuania";}
        if(countrycode.equals("LU")){ countryName = "Luxembourg";}
        if(countrycode.equals("MO")){ countryName = "Macau";}
        if(countrycode.equals("MK")){ countryName = "Macedonia";}
        if(countrycode.equals("MG")){ countryName = "Madagascar";}
        if(countrycode.equals("MW")){ countryName = "Malawi";}
        if(countrycode.equals("MY")){ countryName = "Malaysian";}
        if(countrycode.equals("MV")){ countryName = "Maldives";}
        if(countrycode.equals("ML") ){ countryName = "Mali";}
        if(countrycode.equals("MT")){ countryName = "Malta";}
        if(countrycode.equals("MH")){ countryName = "Marshall Islands";}
        if(countrycode.equals("MR")){ countryName = "Mauritania";}
        if(countrycode.equals("MU")){ countryName = "Mauritius";}
        if(countrycode.equals("YT")){ countryName = "Mayotte";}
        if(countrycode.equals("MX")){ countryName = "Mexico";}
        if(countrycode.equals("MD")){ countryName = "Moldova";}
        if(countrycode.equals("MC")){ countryName = "Monaco";}
        if(countrycode.equals("MN")){ countryName = "Mongolia";}
        if(countrycode.equals("ME")){ countryName = "Montenegro";}
        if(countrycode.equals("MS")){ countryName = "Montserrat";}
        if(countrycode.equals("MA")){ countryName = "Morocco";}
        if(countrycode.equals("MZ")){ countryName = "Mozambique";}
        if(countrycode.equals("MM")){ countryName = "Myanmar";}
        if(countrycode.equals("NA")){ countryName = "Namibia";}
        if(countrycode.equals("NR")){ countryName = "Nauru";}
        if(countrycode.equals("NP")){ countryName = "Nepal";}
        if(countrycode.equals("NL")){ countryName = "Netherlands";}
        if(countrycode.equals("AN")){ countryName = "Netherlands Antilles";}
        if(countrycode.equals("NC")){ countryName = "New Caledonia";}
        if(countrycode.equals("NZ")){ countryName = "New Zealand";}
        if(countrycode.equals("NI")){ countryName = "Nicaragua";}
        if(countrycode.equals("NE")){ countryName = "Niger";}
        if(countrycode.equals("NG")){ countryName = "Nigeria";}
        if(countrycode.equals("NU")){ countryName = "Niue";}
        if(countrycode.equals("KP")){ countryName = "North Korea";}
        if(countrycode.equals("NO")){ countryName = "Norway";}
        if(countrycode.equals("OM")){ countryName = "Oman";}
        if(countrycode.equals("PK")){ countryName = "Pakistan";}
        if(countrycode.equals("PW")){ countryName = "Palau";}
        if(countrycode.equals("PA")){ countryName = "Panama";}
        if(countrycode.equals("PG")){ countryName = "Papua New Guinea";}
        if(countrycode.equals("PY")){ countryName = "Paraguay";}
        if(countrycode.equals("PE")){ countryName = "Peru";}
        if(countrycode.equals("PH")){ countryName = "Philippines";}
        if(countrycode.equals("PN")){ countryName = "Pitcairn Islands";}
        if(countrycode.equals("PL")){ countryName = "Poland";}
        if(countrycode.equals("PT")){ countryName = "Portugal";}
        if(countrycode.equals("PR")){ countryName = "Puerto Rico";}
        if(countrycode.equals("QA")){ countryName = "Qatar";}
        if(countrycode.equals("RO")){ countryName = "Romania";}
        if(countrycode.equals("RU")){ countryName = "Russia";}
        if(countrycode.equals("RW")){ countryName = "Rwanda";}
        if(countrycode.equals("SH")){ countryName = "Saint Helena and Dependencies";}
        if(countrycode.equals("KN")){ countryName = "Saint Kitts and Nevis";}
        if(countrycode.equals("LC")){ countryName = "Saint Lucia";}
        if(countrycode.equals("PM")){ countryName = "Saint Pierre and Miquelon";}
        if(countrycode.equals("VC")){ countryName = "Saint Vincent and the Grenadines";}
        if(countrycode.equals("WS")){ countryName = "Samoa";}
        if(countrycode.equals("SM")){ countryName = "San Marino";}
        if(countrycode.equals("ST")){ countryName = "Sao Tome and Principe";}
        if(countrycode.equals("SA")){ countryName = "Saudi Arabia";}
        if(countrycode.equals("SN")){ countryName = "Senegal";}
        if(countrycode.equals("RS")){ countryName = "Serbia";}
        if(countrycode.equals("SC")){ countryName = "Seychelles";}
        if(countrycode.equals("SL") ){ countryName = "Sierra Leone";}
        if(countrycode.equals("SG")){ countryName = "Singapore";}
        if(countrycode.equals("SK")){ countryName = "Slovakia";}
        if(countrycode.equals("SI")){ countryName = "Slovenia";}
        if(countrycode.equals("SB")){ countryName = "Solomon Islands";}
        if(countrycode.equals("ZA")){ countryName = "South Africa";}
        if(countrycode.equals("KR")){ countryName = "South Korea";}
        if(countrycode.equals("ES")){ countryName = "Spain";}
        if(countrycode.equals("LK")){ countryName = "Sri Lanka";}
        if(countrycode.equals("SD")){ countryName = "Sudan";}
        if(countrycode.equals("SR")){ countryName = "Suriname";}
        if(countrycode.equals("SZ")){ countryName = "Swaziland";}
        if(countrycode.equals("SE")){ countryName = "Sweden";}
        if(countrycode.equals("CH")){ countryName = "Switzerland";}
        if(countrycode.equals("SY")){ countryName = "Syria";}
        if(countrycode.equals("TW")){ countryName = "Taiwan";}
        if(countrycode.equals("TJ")){ countryName = "Tajikistan";}
        if(countrycode.equals("TZ")){ countryName = "Tanzania";}
        if(countrycode.equals("TH")){ countryName = "Thailand";}
        if(countrycode.equals("TG")){ countryName = "Togo";}
        if(countrycode.equals("TK")){ countryName = "Tokelau";}
        if(countrycode.equals("TO")){ countryName = "Tonga";}
        if(countrycode.equals("TN")){ countryName = "Tunisia";}
        if(countrycode.equals("TR")){ countryName = "Turkey";}
        if(countrycode.equals("TM") ){ countryName = "Turkmenistan";}
        if(countrycode.equals("TV")){ countryName = "Tuvalu";}
        if(countrycode.equals("UG")){ countryName = "Uganda";}
        if(countrycode.equals("UA")){ countryName = "Ukraine";}
        if(countrycode.equals("AE")){ countryName = "United Arab Emirates";}
        if(countrycode.equals("UK")){ countryName = "United Kingdom";}
        if(countrycode.equals("US")){ countryName = "United States";}
        if(countrycode.equals("UY")){ countryName = "Uruguay";}
        if(countrycode.equals("UZ")){ countryName = "Uzbekistan";}
        if(countrycode.equals("UV")){ countryName = "Vanuatu";}
        if(countrycode.equals("VE")){ countryName = "Venezuela";}
        if(countrycode.equals("VN")){ countryName = "Vietnam";}
        if(countrycode.equals("WF")){ countryName = "Wallis and Futuna";}
        if(countrycode.equals("YE")){ countryName = "Yemen";}
        if(countrycode.equals("ZM")){ countryName = "Zambia";}
        if(countrycode.equals("ZW")){ countryName = "Zimbabwe";}

        return countryName;
	} 
	
	
	
	/**
	 * 取得多組 IP 裡的最後一組 IP
	 * https://qbeukes.blogspot.com/2009/09/regular-expression-for-ip-address.html
	 */
	private static final String IP_SEPERATOR = "(\\p{Space}|\\p{Punct})";
	private static final String IP_COMPONENT = "(1?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
	private static final Pattern IP_PATTERN = Pattern.compile("(?<=(^|[" + IP_SEPERATOR + "]))(" + IP_COMPONENT + "\\.){3}" + IP_COMPONENT + "(?=([" + IP_SEPERATOR + "]|$))");
	
	public static List<String> findIPAddresses(String s){
	   List<String> ips = new ArrayList<String>();
	   ips.add(0, "");
	  
	   Matcher m = IP_PATTERN.matcher(s);
	   while (m.find())
	   {
	     String ip = m.group();
	     ips.set(0, ip);
	   }

	   return ips;
	 }


	
	
	
	
}
