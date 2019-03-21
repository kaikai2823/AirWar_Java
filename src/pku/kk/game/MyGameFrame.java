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
 * 继承JFrame 方法，在 swing 包下面，
 * 
 */
public class MyGameFrame extends Frame{
	
	Image bj = GameUtil.getImage("images/bj.jpg");
	Image planeImg = GameUtil.getImage("images/plane.png");
	
	Plane plane=new Plane(planeImg,300,300);

	ArrayList<Shell>  shellList = new ArrayList<Shell>(); 
	
	Date startTime = new Date();
	Date endTime;
	int period;//游戏持续时间
	
	/*
	 * 自动被调用，g相当于画笔
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		
		g.drawImage(bj, 0, 0, Constant.GAME_WIDTH,Constant.GAME_HEIGHT,null);
		
		plane.drawSelf(g); //画飞机
		//画出所有炮弹
		for(int i=0;i<shellList.size();i++) {
			Shell b = shellList.get(i);
			b.drawShell(g);
			//飞机和炮弹的碰撞检测
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
				g.drawString("时间："+period+"秒", (int)plane.x, (int)plane.y);
			}
			
		}
	}
	
	/*
	 * 定义内部类，帮助我们反复重画窗口
	 */
	class PaintThread extends Thread{
		
		@Override
		public void run() {
			while(true) {
				repaint(); //画画
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
	 * 定义键盘监听的内部类
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
	 * 初始化窗口，通过this直接调用父类的方法
	 */
	public void launchFrame() {
		this.setTitle("张凯"); //设置窗口名称
		this.setVisible(true); //设置为可见
		this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT); //设置窗口大小
		this.setLocation(500,100); //设置窗口的初始位置
		
		/*
		 * 窗口监听，关闭窗口的同时结束程序
		 */
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		
		new PaintThread().start();//启动重画窗口的线程
		addKeyListener(new KeyMonitor());//给窗口增加键盘监听
		
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
	 * 双缓冲解决闪烁问题，针对Frame类
	 */

	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	} 
}
