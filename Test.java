public class Test extends Thread
{
	Minesweeper obj;
	int i=0;
	Test()
	{
		obj=new Minesweeper();
		//obj.display();
	}
	public void run()
	{
		try
		{
		while(true){
		obj.setTime(i++);
		this.sleep(1000);
		}
		}
		catch(Exception exp)
		{
			System.out.println("Test.run : "+exp);
		}
	}
	public static void main(String args[])
	{
		try
		{
		Test t1=new Test();			
		t1.start();
		}
		catch(Exception exp)
		{
			System.out.println("Main : "+exp);
		}
	}
}