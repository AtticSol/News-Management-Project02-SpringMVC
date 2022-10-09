package by.itac.project02.service.validation;

import by.itac.project02.bean.NewsData;

public interface NewsValidationService {

	boolean addNewsDataValidation(NewsData news) throws NewsValidationException;

	boolean newsIdValidation(String[] idNewsArrStr) throws NewsValidationException;

	boolean isNumberValidation(int id) throws NewsValidationException;

}
