package pku.kk.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;


public class Plane extends GameObject{
	
	boolean left,up,right,down;
	
	boolean live=true;
	Explode bao;
	@Override
	public void drawSelf(Graphics g) {
		if(live){
			g.drawImage(img,(int)x,(int)y, Constant.PLANE_WIDTH, Constant.PLANE_HEIGHT, null);
			if(left) {
				if(x>2)x-=speed;
			}
			if(right) {
				if(x<500-Constant.PLANE_WIDTH-2)x+=speed;
			}
			if(up) {
				if(y>32)y-=speed;
			}
			if(down) {
				if(y<500-Constant.PLANE_HEIGHT-2)y+=speed;
			}
		}else {
			if(bao==null){
			bao = new Explode(x, y);
			}
			bao.draw(g);
		}
	}
	
	public Plane(Image img,double x,double y) {
		this.img=img;
		this.x=x;
		this.y=y;
		
		this.speed=5;
		this.width=Constant.PLANE_WIDTH;
		this.height=Constant.PLANE_HEIGHT;
	}
	//按下某个键增加相应的方向
	public void addDirection(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left=true;
			break;
		case KeyEvent.VK_UP:
			up=true;
			break;
		case KeyEvent.VK_RIGHT:
			right=true;
			break;
		case KeyEvent.VK_DOWN:
			down=true;
			break;
		}
	}
	
	//按下某个键减相应的方向
	public void minusDirection(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left=false;
			break;
		case KeyEvent.VK_UP:
			up=false;
			break;
		case KeyEvent.VK_RIGHT:
			right=false;
			break;
		case KeyEvent.VK_DOWN:
			down=false;
			break;
		}
	}
}
