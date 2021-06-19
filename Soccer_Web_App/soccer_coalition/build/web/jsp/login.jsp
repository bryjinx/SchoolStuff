<%-- 
    Document   : login
    Created on : Apr 11, 2018, 10:15:43 AM
    Author     : Mike
--%>

<body>
  <script type="text/javascript">
      // puts cursor into username box on page load
      document.formLogin.userBox.focus();
  </script>
  <div class="centered-area">
    <form method="POST" action='UserController' name="formLogin">
      <h3> Welcome! please enter your login information </h3><br />
      <input type="hidden" name="operation" value="login">
      <input id="userBox" style="width:100%" type="text" name="username" placeholder="Enter Username" required/><br />
      <br />
      <input style="width:100%" type="password" name="password" placeholder="Enter Password" required/><br />
      <br />
      <input style="margin:0 auto; display:block" type="submit" value="Login" />
    </form>
    <%
        String loginFeedback;
        if ((loginFeedback = (String) session.getAttribute("loginFeedback")) != null) {
    %>
    <p> <%=loginFeedback%> </p>
    <%}%>
  </div>
</body>
