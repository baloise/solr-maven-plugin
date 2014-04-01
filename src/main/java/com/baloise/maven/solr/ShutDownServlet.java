package com.baloise.maven.solr;

import java.io.IOException;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShutDownServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletOutputStream outputStream = resp.getOutputStream();
    outputStream.print("Shutting down");
    outputStream.flush();
    System.exit(0);
  }
}
