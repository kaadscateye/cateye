package BrocastEvent;
/**
 * ������Ϣ����ʵ��
 * 
 * @author hp
 * 
 */
public class PushBlockQueueHandler implements Runnable {

	private Object obj;

	public PushBlockQueueHandler(Object obj) {
		this.obj = obj;
	}

	@Override
	public void run() {
		doBusiness();
	}

	/**
	 * ҵ����ʱ��
	 */
	public void doBusiness() {
		System.out.println(" �������� " + obj);
	}

}