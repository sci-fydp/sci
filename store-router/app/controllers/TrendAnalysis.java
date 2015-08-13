package controllers;

import org.joda.time.DateTime;

import logics.TrendAnalysisLogic;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class TrendAnalysis extends Controller {
	
	public static Result stuff() {
		return ok(Json.toJson(TrendAnalysisLogic.generateItemsForUser()));
	}
}
