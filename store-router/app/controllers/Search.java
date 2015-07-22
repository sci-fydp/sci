package controllers;


import java.util.List;

import models.Item;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Search extends Controller {

    public static Result index() {
        return ok(views.html.web.main.render());
    }
    
    public static Result search(String searchText) {
    	if(searchText.length() < 3)
    		return ok(Json.toJson("{message: \"need more characters\""));
    	
    	List<Item> searchedItems = Item.find.where().contains("name", searchText).setMaxRows(10).findList();
    	return ok(Json.toJson(searchedItems));
    }
    
}
