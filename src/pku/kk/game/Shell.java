package pku.kk.game;

import java.awt.Color;
import java.awt.Graphics;

public class Shell extends GameObject{
	double degree;
	
	public Shell() {
		x=100;
		y=100;
		width=10;
		height=10;
		speed=3;
		
		degree=Math.random()*Math.PI*2;
	}
	public void drawShell(Graphics g) {
		Color c=g.getColor();
		g.setColor(Color.YELLOW);
		
		g.fillOval((int)x, (int)y, width, height);
		//炮弹沿着任意方向去飞
		x += speed*Math.cos(degree);
		y += speed*Math.sin(degree);
		
		if(x<width||x>Constant.GAME_WIDTH-2*width) {
			degree = Math.PI-degree;
		}
		if(y<32||y>Constant.GAME_HEIGHT-2*height) {
			degree = -degree;
		}
		
		g.setColor(c);
	
		
		
	}
}
