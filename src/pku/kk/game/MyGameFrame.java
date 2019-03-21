package pku.kk.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
/*
 * �̳�JFrame �������� swing �����棬
 * 
 */
public class MyGameFrame extends Frame{
	
	Image bj = GameUtil.getImage("images/bj.jpg");
	Image planeImg = GameUtil.getImage("images/plane.png");
	
	Plane plane=new Plane(planeImg,300,300);

	ArrayList<Shell>  shellList = new ArrayList<Shell>(); 
	
	Date startTime = new Date();
	Date endTime;
	int period;//��Ϸ����ʱ��
	
	/*
	 * �Զ������ã�g�൱�ڻ���
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		
		g.drawImage(bj, 0, 0, Constant.GAME_WIDTH,Constant.GAME_HEIGHT,null);
		
		plane.drawSelf(g); //���ɻ�
		//���������ڵ�
		for(int i=0;i<shellList.size();i++) {
			Shell b = shellList.get(i);
			b.drawShell(g);
			//�ɻ����ڵ�����ײ���
			boolean peng=b.getRect().intersects(plane.getRect());
			if(peng) {
				plane.live=false;
				if(endTime==null) {
					endTime=new Date();
					period=(int)(endTime.getTime()-startTime.getTime())/1000;
				}
			}
			if(!plane.live) {
				g.setColor(Color.RED);
				g.drawString("ʱ�䣺"+period+"��", (int)plane.x, (int)plane.y);
			}
			
		}
	}
	
	/*
	 * �����ڲ��࣬�������Ƿ����ػ�����
	 */
	class PaintThread extends Thread{
		
		@Override
		public void run() {
			while(true) {
				repaint(); //����
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/*
	 * ������̼������ڲ���
	 */
	class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
		
	}
	
	/*
	 * ��ʼ�����ڣ�ͨ��thisֱ�ӵ��ø���ķ���
	 */
	public void launchFrame() {
		this.setTitle("�ſ�"); //���ô�������
		this.setVisible(true); //����Ϊ�ɼ�
		this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT); //���ô��ڴ�С
		this.setLocation(500,100); //���ô��ڵĳ�ʼλ��
		
		/*
		 * ���ڼ������رմ��ڵ�ͬʱ��������
		 */
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		
		new PaintThread().start();//�����ػ����ڵ��߳�
		addKeyListener(new KeyMonitor());//���������Ӽ��̼���
		
		for(int i=0;i<50;i++) {
			Shell b=new Shell();
			shellList.add(b);
		}
	}
	
	public static void main(String[] args) {
		MyGameFrame f=new MyGameFrame();
		f.launchFrame();
	}
	
	/*
	 * ˫��������˸���⣬���Frame��
	 */

	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//������Ϸ���ڵĿ�Ⱥ͸߶�
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	} 
}
