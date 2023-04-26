public class publicclassTestJSch {
    staticint lport = 3306;//本地端口
    static String rhost = "10.10.10.10";//远程MySQL服务器
    staticint rport = 3306;//远程MySQL服务端口

    publicstaticvoidgo() {
        String user = "username";//SSH连接用户名
        String password = "password";//SSH连接密码
        String host = "10.10.10.11";//SSH服务器
        int port = 2222;//SSH访问端口
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    publicstaticvoidsql() {
        Connection conn = null;
        ResultSet rs = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            st = conn.createStatement();
            String sql = "SELECT COUNT(1) FROM All";
            rs = st.executeQuery(sql);
            while (rs.next())
                System.out.println(rs.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            st.close();
            conn.close();
        }
    }

    publicstaticvoidmain(String[] args) {
        go();
        sql();
    }
}