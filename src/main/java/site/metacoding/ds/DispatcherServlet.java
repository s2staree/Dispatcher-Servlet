package site.metacoding.ds;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("doGet 호출됨");
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("doPost 호출됨");
		doProcess(req, resp);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doProcess 요청됨");
		String httpMethod = req.getMethod();
//		System.out.println(httpMethod);
		String identifier = req.getRequestURI();
//		System.out.println(identifier);
		
		// 공통 로직 처리
		UserController c = new UserController();
//		if(identifier.equals("/join")) {
//			c.join();
//		}else if(identifier.equals("/login")) {
//			c.login();
//		}else {
//			System.out.println("잘못된 요청입니다.");
//		}
		
		Method[] methodList = c.getClass().getDeclaredMethods();
		for (Method method : methodList) {	// 포이츠 문법
//			System.out.println(method.getName());
			Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class);
			RequestMapping requestMapping = (RequestMapping) annotation;
			try {
//				System.out.println(requestMapping.value());
				if(identifier.equals(requestMapping.value())) {
					method.invoke(c);
				}
			} catch (Exception e) {
				System.out.println(method.getName() + "은 어노테이션이 없습니다.");
			}
			
		}
	}
}
