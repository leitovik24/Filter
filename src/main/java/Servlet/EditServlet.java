package Servlet;

import Service.UserService;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/admin/edit")
public class EditServlet extends HttpServlet {
    UserService userService = UserService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getAttribute("access") == "true") {
            long id = Long.parseLong(req.getParameter("id"));
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("id", id);
            User user = userService.getUserById(id);
            if (user != null) {
                req.setAttribute("user", user);
                getServletContext().getRequestDispatcher("/edit.jsp").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        }
        else{
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getAttribute("access") == "true") {
            HttpSession httpSession = req.getSession();
            long id = (Long) httpSession.getAttribute("id");
            httpSession.removeAttribute("id");
            String newName = req.getParameter("name");
            String newEmail = req.getParameter("email");
            String newRole = req.getParameter("role");
            userService.editUser(new User(id, newName, newEmail, newRole));
            String path = req.getContextPath() + "/admin/list";
            resp.sendRedirect(path);
        }
        else{
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
}
