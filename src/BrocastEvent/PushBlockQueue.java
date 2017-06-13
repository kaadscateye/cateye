package BrocastEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消息队列缓冲定义
 * @author hp
 *
 */
public class PushBlockQueue extends LinkedBlockingQueue<Object>{
    
    private static final long serialVersionUID = -8224792866430647454L;
    private static PushBlockQueue pbq = new PushBlockQueue();//单例
    private boolean flag = false;
    private DoMsg doMsg;
    private PushBlockQueue(){}
    public static PushBlockQueue getInstance(){
        return pbq;
    }
    
    /**
     * 队列监听启动
     */
    public void start(){
        if(!this.flag){
            this.flag = true;
        }else{
            throw new IllegalArgumentException("队列已处于启动状态,不允许重复启动.");
        }
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(flag){
                    try {
                        Object obj = take();//使用阻塞模式获取队列消息
//                        System.out.println(" 处理请求 " + obj);
                        doMsg.Pthread_handlertaskMsg(obj);
                        //将获取消息交由线程池处理
//                        es.execute(new PushBlockQueueHandler(obj));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        
    }
    
    /**
     * 停止队列监听
     */
    public void stop(){
        this.flag = false;
    }
    public void callBack(DoMsg callback){
    	this.doMsg=callback;
    }
}