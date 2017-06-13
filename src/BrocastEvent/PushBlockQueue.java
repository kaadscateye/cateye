package BrocastEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ��Ϣ���л��嶨��
 * @author hp
 *
 */
public class PushBlockQueue extends LinkedBlockingQueue<Object>{
    
    private static final long serialVersionUID = -8224792866430647454L;
    private static PushBlockQueue pbq = new PushBlockQueue();//����
    private boolean flag = false;
    private DoMsg doMsg;
    private PushBlockQueue(){}
    public static PushBlockQueue getInstance(){
        return pbq;
    }
    
    /**
     * ���м�������
     */
    public void start(){
        if(!this.flag){
            this.flag = true;
        }else{
            throw new IllegalArgumentException("�����Ѵ�������״̬,�������ظ�����.");
        }
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(flag){
                    try {
                        Object obj = take();//ʹ������ģʽ��ȡ������Ϣ
//                        System.out.println(" �������� " + obj);
                        doMsg.Pthread_handlertaskMsg(obj);
                        //����ȡ��Ϣ�����̳߳ش���
//                        es.execute(new PushBlockQueueHandler(obj));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        
    }
    
    /**
     * ֹͣ���м���
     */
    public void stop(){
        this.flag = false;
    }
    public void callBack(DoMsg callback){
    	this.doMsg=callback;
    }
}