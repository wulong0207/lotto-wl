package com.hhly.lotto.base.tags;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author huangb 金额格式化标签
 */
@SuppressWarnings("serial")
public class MoneyTag extends TagSupport {

	private static Logger logger = Logger.getLogger(MoneyTag.class);

	/**
	 * 金额值
	 */
	private String value;
	/**
	 * 小数位数，范围0-3
	 */
	private int digits = 0;
	/**
	 * 舍入模式 1-向下舍入(截断) 2-向上舍入(四舍五入)
	 */
	private int roundingMode = 1;
	/**
	 * 是否千分位显示 Y/N
	 */
	private String isQFW;
	/**
	 * 是否加人民币符号 Y/N
	 */
	private String isSign;
	/**
	 * 是否加人民币单位 Y/N
	 */
	private String isUnit;
	/**
	 * 人民币符号
	 */
	private final static String RMB = "￥";
	/**
	 * 人民币单位
	 */
	private final static String RMB_CHAR = "元";

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (value == null || value.trim().length() == 0) {
			value = "0";
		} else {
			this.value = value;
		}
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		if(digits < 0 || digits > 3) {
			this.digits = 0;
		} else {
			this.digits = digits;
		}
	}

	public int getRoundingMode() {
		return roundingMode;
	}

	public void setRoundingMode(int roundingMode) {
		this.roundingMode = roundingMode;
	}

	public String getIsQFW() {
		return isQFW;
	}

	public void setIsQFW(String isQFW) {
		this.isQFW = isQFW;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public String getIsUnit() {
		return isUnit;
	}

	public void setIsUnit(String isUnit) {
		this.isUnit = isUnit;
	}

	public int doEndTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		try {
			if ("Y".equals(isSign)) {
				out.write(RMB);
			}
			BigDecimal money = new BigDecimal(value.trim());
			// 舍入模式
			RoundingMode mode = RoundingMode.HALF_UP;
			if(roundingMode == 2) {
				mode = RoundingMode.HALF_UP;
			} else {
				mode = RoundingMode.DOWN;
			}
			if ("Y".equals(isQFW)) {
				if(digits == 1) {
					out.write(new DecimalFormat("###,##0.0").format(money.setScale(1, mode)));
				} else if(digits == 2) {
					out.write(new DecimalFormat("###,##0.00").format(money.setScale(2, mode)));
				} else if(digits == 3) {
					out.write(new DecimalFormat("###,##0.000").format(money.setScale(3, mode)));
				} else {
					out.write(new DecimalFormat("###,##0").format(money.setScale(0, mode)));
				}
			} else {
				if(digits == 1) {
					out.write(money.setScale(1, mode).toString());
				} else if(digits == 2) {
					out.write(money.setScale(2, mode).toString());
				} else if(digits == 3) {
					out.write(money.setScale(3, mode).toString());
				} else {
					out.write(money.setScale(0, mode).toString());
				}
			}
			if ("Y".equals(isUnit)) {
				out.write(RMB_CHAR);
			}
		} catch (IOException e) {
			logger.error("金额标签生成错误！！", e);
		}
		return EVAL_PAGE;
	}
}
