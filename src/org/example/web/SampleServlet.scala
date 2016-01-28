package org.example.web

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest

class SampleServlet extends HttpServlet {
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    response.getWriter.write("This is SampleServlet !!!")
  }
}