package controllers;

import org.joda.time.DateTime;

import logics.EricLogic;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Eric extends Controller {
	
	public static Result stuff() {
		return ok(Json.toJson(EricLogic.generateItemsForUser()));
	}
}
