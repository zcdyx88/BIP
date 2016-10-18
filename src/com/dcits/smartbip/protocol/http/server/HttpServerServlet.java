package com.dcits.smartbip.protocol.http.server;

import com.dcits.smartbip.runtime.model.impl.ProcessDispatcher;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vincentfxz on 16/5/23.
 */
public class HttpServerServlet extends HttpServlet {

	private static final Log log = LogFactory.getLog(HttpServerServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		InputStream input = req.getInputStream();
		input = new BufferedInputStream(input);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 8];
		int offset = 0;
		while ((offset = input.read(buffer)) > 0) {
			byteArrayOutputStream.write(buffer, 0, offset);
		}
		byte[] content = byteArrayOutputStream.toByteArray();
		
		ProcessDispatcher processDispatcher = new ProcessDispatcher(content, new BufferedOutputStream(resp.getOutputStream()));
		processDispatcher.dispatch(content);

	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}
}
