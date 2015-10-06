package com.aztask.controllers;

import play.mvc.*;

public class Application extends Controller {
	public static Result index() {
		return badRequest("Bad Request");
    }
}
