import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends Frame implements MouseListener
{
	int n;
	int time;
	int arr[][];
	int mask[][];
	int x=0,y=0,b1=0;
	boolean game_end;
	Minesweeper()
	{
		super("Minesweeper");
		game_end=false;
		n=10;
		arr=new int[9][9];
		mask=new int[9][9];
		for(int i=0;i<9;i++)
		for(int j=0;j<9;j++)
			mask[i][j]=0;
		//mask[2][3]=1;
		algorithm();
		setVisible(true);
		setSize(450,450);
		addMouseListener(this);
	}
	
	public void display()
	{
		for(int i=0;i<9;i++)
		{
		for(int j=0;j<9;j++)
			if(arr[j][i]==-1)
				System.out.print("M  ");
			else
				System.out.print(arr[j][i]+"  ");
		System.out.println();
		}
	}
	
	public int random(int i,int j)
	{
		int r;
		while(true)
		{
			r=i+(int)(Math.random()*j);
			if(i<=r && r<=j)
				break;
		}
		return r;
	}

	public void algorithm()
	{
		for(int i=0;i<10;i++)
		{
			while(true)
			{
			int k=random(0,80);
			int a=k/9;
			int b=k%9;
			if(arr[a][b]!=-1){
				arr[a][b]=-1;
				break;
				}
			}
		}
		setValues();
	}
	
	public void setValues()
	{
		int c;
		int u1,u2,l1,l2;
		for(int i=0;i<9;i++)
		for(int j=0;j<9;j++)
		{
			if(i==0 && j==0)
			{
			l1=0;l2=0;u1=1;u2=1;
			}
			else if(i==8 && j==8)
			{
			l1=7;l2=7;u1=8;u2=8;
			}
			else if(i==0 && j==8)
			{
			l1=0;l2=7;u1=1;u2=8;
			}
			else if(i==8 && j==0)
			{
			l1=7;l2=0;u1=8;u2=1;
			}
			else if(i==0)
			{
			l1=i;l2=j-1;u1=i+1;u2=j+1;
			}
			else if(j==0)
			{
			l1=i-1;l2=j;u1=i+1;u2=j+1;
			}
			else if(i==8)
			{
			l1=i-1;l2=j-1;u1=i;u2=j+1;
			}
			else if(j==8)
			{
			l1=i-1;l2=j-1;u1=i+1;u2=j;
			}
			else
			{
			l1=i-1;l2=j-1;u1=i+1;u2=j+1;
			}
			c=0;
			if(arr[i][j]!=-1)
			{
				try
				{
					for(int k=l1;k<=u1;k++)
					for(int m=l2;m<=u2;m++)
					if(arr[k][m]==-1)
						c++;
				}
				catch(Exception exp)
				{
				}
				arr[i][j]=c;
			}			
		}
	}
	boolean active=true;
	
	public void paint(Graphics g)
	{
		g.setColor(new Color(255,0,0));
		int x1=100,y1=100;
		if(active)
		{
		g.drawString("Mines : "+Integer.toString(n)+"                 Time : "+Integer.toString(time/1000)+Integer.toString(time/100)+Integer.toString(time/10)+Integer.toString(time%10)+" sec.",100,70);
		g.setColor(new Color(192,192,192));
		for(int i=0;i<9;i++)
		{
		y1=100;
		for(int j=0;j<9;j++)
		{			
			if(mask[i][j]==0)				
				g.fillRect(x1,y1,28,28);
			else if(mask[i][j]==1)
			{
				g.setColor(new Color(0,0,0));
				g.drawRect(x1,y1,28,28);
				if(arr[i][j]!=0)
					g.drawString(Integer.toString(arr[i][j]),x1+12,y1+18);				
				g.setColor(new Color(192,192,192));
			}
			else if(mask[i][j]==2)
			{
				g.fillRect(x1,y1,28,28);
				g.setColor(new Color(0,0,0));
				g.drawLine(x1,y1,x1+28,y1+28);
				g.drawLine(x1+28,y1,x1,y1+28);
				g.setColor(new Color(192,192,192));
			}
			if(game_end && arr[i][j]==-1)
			{
				g.setColor(new Color(255,0,0));
				g.fillRect(x1,y1,28,28);
				g.setColor(new Color(192,192,192));
				active=false;
			}
			y1+=30;
		}
		x1+=30;
		}
		g.setColor(new Color(255,0,0));
		if(gameOver() || gameOver1())
		{
			game_end=true;
			g.drawString("You Won the Game. ",100,420);
		}
		if(active)
			g.drawString("X = "+x+"; Y = "+y+"; b1 = "+b1,100,400);
		else
			g.drawString("Game Over.",100,400);
		}
	}
	
	boolean gameOver()
	{
		for(int i=0;i<9;i++)
		for(int j=0;j<9;j++)
		{
			if(arr[i][j]==-1)
			{
				if(mask[i][j]!=2)
					return false;
			}
		}
		return true;
	}
	
	boolean gameOver1()
	{
		int c=0;
		for(int i=0;i<9;i++)
		for(int j=0;j<9;j++)
		{
			if(arr[i][j]!=-1)
				if(mask[i][j]!=0)
					c++;
		}
		if(c==71)
			return true;
		else
			return false;
	}

	public void mouseClicked(MouseEvent e)
	{
	if(active)
	{
		x=e.getX();
		y=e.getY();
	if((x>=100 && x<=370) && (y>=100 && y<=370))
	{
		b1=e.getButton();
		int i=(x-100)/30;
		int j=(y-100)/30;
	if(b1==1)
	{
		//if(arr[i][j]==0 && (mask[i][j]!=1 || mask[i][j]!=2))
		if(arr[i][j]==0 && mask[i][j]!=-1)
		{
			for(int m=i;m>=0;m--)
			{
				if(arr[m][j]==0){
					if(mask[m][j]!=2)
						mask[m][j]=1;
					}
				else if(arr[m][j]!=-1)
				{
					if(mask[m][j]!=2)
						mask[m][j]=1;
					break;
				}
				else
					break;
				for(int n=j;n>=0;n--)
				if(arr[m][n]==0){
					if(mask[m][n]!=2)
						mask[m][n]=1;
					}
				else if(arr[m][n]!=-1)
				{
					if(mask[m][n]!=2)
						mask[m][n]=1;
					break;
				}
				else
					break;
				for(int n=j;n<9;n++)
				if(arr[m][n]==0){
					if(mask[m][n]!=2)
						mask[m][n]=1;
					}
				else if(arr[m][n]!=-1)
				{
					if(mask[m][n]!=2)
						mask[m][n]=1;
					break;
				}
				else
					break;
			}
		
			for(int m=i+1;m<9;m++)
			{
				if(arr[m][j]==0){
					if(mask[m][j]!=2)	
						mask[m][j]=1;
					}
				else if(arr[m][j]!=-1)
				{
					if(mask[m][j]!=2)
						mask[m][j]=1;
					break;
				}
				else
					break;
				for(int n=j;n>=0;n--)
				if(arr[m][n]==0){
					if(mask[m][n]!=2)
						mask[m][n]=1;
					}
				else if(arr[m][n]!=-1)
				{
					if(mask[m][n]!=2)
						mask[m][n]=1;
					break;
				}
				else
					break;
				for(int n=j;n<9;n++)
				if(arr[m][n]==0){
					if(mask[m][n]!=2)
						mask[m][n]=1;
					}
				else if(arr[m][n]!=-1)
				{
					if(mask[m][n]!=2)
						mask[m][n]=1;
					break;
				}
				else
					break;
			}
		}
		else if(mask[i][j]==2 && arr[i][j]!=-1)
			mask[i][j]=1;
		else if(arr[i][j]!=-1)
			mask[i][j]=1;
		else
		{
			game_end=true;
		}
	}
	else if(b1==3)
	{
		if(mask[i][j]==0){
			mask[i][j]=2;
			n--;
		}
		else if(mask[i][j]==2){
			mask[i][j]=0;
			n++;
		}
	}
	repaint();
	}
	}
	}
	
	public void setTime(int t)
	{
		if(active)
		{
			time=t;
			repaint();
		}
	}
	
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}