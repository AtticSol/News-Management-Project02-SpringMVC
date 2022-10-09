package by.itac.project02.controller.impl;

import com.mysql.cj.util.StringUtils;

import by.itac.project02.controller.Atribute;
import by.itac.project02.util.Constant;

public class Util {

	
	// first argument - command, others - attributes with values
	static String pageURL(String command, String... param) {
		StringBuffer sb = new StringBuffer();
		sb.append(command);
		sb.append(Atribute.QUERY_SEPARATOR);
		for (int i = 0; i < param.length; i = i + 2) {
			sb.append(param[i]);
			sb.append(Atribute.EQUALS);
			sb.append(param[i + 1]);
			sb.append(Atribute.SEPARATOR);
		}
		return sb.toString();
	}

	static int takeNumber(String inputNumber) {
		if (!StringUtils.isStrictlyNumeric(inputNumber)) {
			return Constant.NO_NUMBER;
		} else if (inputNumber.isEmpty()) {
			return Constant.NO_NUMBER;
		} else {
			return Integer.parseInt(inputNumber);
		}
	}
	
}
