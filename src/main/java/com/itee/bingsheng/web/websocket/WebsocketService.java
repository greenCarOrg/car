package com.itee.bingsheng.web.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 功能描述
 *
 * @param
 * @author Created by Administrator on 2017/8/18.
 **/
@ServerEndpoint("/onWebsocket")
public class WebsocketService {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebsocketService> webSocketSet = new CopyOnWriteArraySet<WebsocketService>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        //System.out.println("来自客户端的消息:" + message);
        //群发消息
        for(WebsocketService item: webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        //System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        //System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message+"");
        //this.session.getAsyncRemote().sendText(message);
    }

    public static void sendMsg(String message) throws IOException{
        //群发消息
        for(WebsocketService item: webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebsocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebsocketService.onlineCount--;
    }
}
