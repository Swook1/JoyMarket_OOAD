module JoyMarket {
	opens main;
	opens model;
	opens view;
	opens controller;
	opens database;
	
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;
	requires javafx.controls;
}