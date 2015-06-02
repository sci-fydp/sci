package controllers;

import models.City;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result city() {
    	City city = new City();
    	city.name = "TESTCITY";
    	city.save();
    	return ok(views.html.city.render(city));
    }
}
