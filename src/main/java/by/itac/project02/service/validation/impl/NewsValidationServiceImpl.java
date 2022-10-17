package by.itac.project02.service.validation.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import by.itac.project02.bean.NewsData;
import by.itac.project02.service.validation.NewsValidationException;
import by.itac.project02.service.validation.NewsValidationService;
import by.itac.project02.util.Constant;
import by.itac.project02.util.InputNewsDataError;

@Service
public class NewsValidationServiceImpl implements NewsValidationService {

	private static final InputNewsDataError noError = InputNewsDataError.NO_ERROR;

	@Override
	public boolean addNewsDataValidation(NewsData news) throws NewsValidationException {

		List<String> errorList = Collections.synchronizedList(new ArrayList<String>());

		error(errorList, lengthError(news.getTitle(), Constant.MAX_TITLE_CHARACTERS_NUMBER,
				Constant.MIN_TITLE_CHARACTERS_NUMBER, InputNewsDataError.TITLE_ERROR));
		error(errorList, dateError(news.getNewsDate()));
		error(errorList, lengthError(news.getBriefNews(), Constant.MAX_BRIEF_CHARACTERS_NUMBER,
				Constant.MIN_BRIEF_CHARACTERS_NUMBER, InputNewsDataError.BRIEF_ERROR));
		error(errorList, lengthError(news.getContent(), Constant.MAX_CONTENT_CHARACTERS_NUMBER,
				Constant.MIN_CONTENT_CHARACTERS_NUMBER, InputNewsDataError.CONTENT_ERROR));

		if (!errorList.isEmpty()) {
			throw new NewsValidationException(errorList, "News not added/updated");
		}
		return true;
	}

	private InputNewsDataError dateError(LocalDate date) {
		if (date == null) {
			return InputNewsDataError.EMPTY_DATE_ERROR;
		}
		return noError;
	}

	@Override
	public boolean isNumberValidation(int number) throws NewsValidationException {
		if (number == Constant.NO_NUMBER) {
			throw new NewsValidationException();
		}
		return true;
	}

	@Override
	public boolean newsIdValidation(String[] idNewsArrStr) throws NewsValidationException {
		List<String> errorList = Collections.synchronizedList(new ArrayList<String>());

		if (idNewsArrStr == null) {
			error(errorList, InputNewsDataError.NO_NEWS_TO_DELETE_ERROR);
			throw new NewsValidationException(errorList, "No news to delete");
		}
		return true;
	}

	private InputNewsDataError lengthError(String validatedParam, int maxCharactersNumber, int minCharactersNumber,
			InputNewsDataError error) {
		if (validatedParam.length() >= maxCharactersNumber || validatedParam.length() < minCharactersNumber) {
			return error;
		}
		return noError;
	}

	private List<String> error(List<String> errorList, InputNewsDataError error) {
		if (!error.equals(noError)) {
			errorList.add(error.getTitle());
		}
		return errorList;
	}
}
