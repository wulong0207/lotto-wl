package com.hhly.lotto.base.tags;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author huangb 奖池格式化
 */
@SuppressWarnings("serial")
public class BonusPoolTag extends TagSupport {

	private static Logger logger = Logger.getLogger(BonusPoolTag.class);
	/**
	 * 金额值
	 */
	private String value;
	/**
	 * 输出标识0-原样输出 1-带样式输出
	 */
	private int outFlag = 0;
	/**
	 * outFlag=1时，指定的标签(em,em表示亿万各自对应的标签)
	 */
	private String label = "em,em";
	/**
	 * outFlag=1时，指定的样式名称(red,red表示亿万各自对应的样式名)
	 */
	private String clsName = "red,red";

	private final static String BILLION = "亿";
	private final static String TEN_THOUSAND = "万";

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (value == null || value.trim().length() == 0) {
			this.value = "0";
		} else {
			this.value = value;
		}
	}

	public int getOutFlag() {
		return outFlag;
	}

	public void setOutFlag(int outFlag) {
		this.outFlag = outFlag;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		if (label == null || label.trim().length() == 0) {
			this.label = "em,em";
		} else {
			this.label = label;
		}
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		if (clsName == null || clsName.trim().length() == 0) {
			this.clsName = "red,red";
		} else {
			this.clsName = clsName;
		}
	}

	public int doEndTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		try {
			BigDecimal money = new BigDecimal(value.trim());
			BigDecimal bWan = new BigDecimal(10000);
			BigDecimal bYi = new BigDecimal(100000000);

			if (money.compareTo(bWan) == -1) {
				// 1. 金额<10000，直接显示
				if (outFlag == 1) {
					String[] labs = label.split(",");
					String[] clss = clsName.split(",");
					out.write("<" + labs[0] + " class=\"" + clss[0] + "\">");
					out.write(money.toString());
					out.write("</" + labs[0] + ">");
				} else {
					out.write(money.toString());
				}
			} else if (money.compareTo(bWan) >= 0 && money.compareTo(bYi) == -1) {
				// 2. 100000000>金额>=10000,显示为如：9999.99万，取两位小数字点，后面的数字舍去(截断)
				money = money.movePointLeft(4);
				if (outFlag == 1) {
					String[] labs = label.split(",");
					String[] clss = clsName.split(",");
					out.write("<" + labs[0] + " class=\"" + clss[0] + "\">");
					out.write(money.setScale(2, RoundingMode.DOWN).toString());
					out.write("</" + labs[0] + ">");
				} else {
					out.write(money.setScale(2, RoundingMode.DOWN).toString());
				}
				out.write(TEN_THOUSAND);
			} else {
				// 3.金额>=100000000,显示如：12亿1234万，千位后面的数字舍去
				BigDecimal yi = money.movePointLeft(8).setScale(0, RoundingMode.DOWN);
				String[] labs;
				String[] clss;
				if (outFlag == 1) {
					labs = label.split(",");
					clss = clsName.split(",");
					out.write("<" + labs[0] + " class=\"" + clss[0] + "\">");
					out.write(yi.toString());
					out.write("</" + labs[0] + ">");
				} else {
					out.write(yi.toString());
				}
				out.write(BILLION);
				BigDecimal wan = money.subtract(yi.multiply(bYi)).movePointLeft(4).setScale(0, RoundingMode.DOWN);
				// 万位大于0才显示
				if (wan.compareTo(BigDecimal.ZERO) > 0) {
					if (outFlag == 1) {
						labs = label.split(",");
						clss = clsName.split(",");
						out.write("<" + (labs.length > 1 ? labs[1] : labs[0]) + " class=\"" + (clss.length > 1 ? clss[1] : clss[0]) + "\">");
						out.write(wan.toString());
						out.write("</" + (labs.length > 1 ? labs[1] : labs[0]) + ">");
					} else {
						out.write(wan.toString());
					}
					out.write(TEN_THOUSAND);
				}
			}
		} catch (IOException e) {
			logger.error("奖池标签生成错误！！", e);
		}
		return EVAL_PAGE;
	}

}
