import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
class MyFrame extends Frame implements ActionListener
{
	TextField txt[]=new TextField[7];
	Button cal,set,abort;
	Checkbox chk1,chk2;
	CheckboxGroup cbg;
	Label lbl[]=new Label[8];
	public MyFrame(String title)
	{
		super(title);
		addWindowListener(new WindowAdapter(){
		                   public void windowClosing(WindowEvent we)
						   {
							   System.exit(0);
						   }
	                     });
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    txt[0]=new TextField(5);
		txt[1]=new TextField(5);
		txt[2]=new TextField(5);
		txt[3]=new TextField(10);
		txt[4]=new TextField(10);
		txt[5]=new TextField(10);
		lbl[0]=new Label("Hours");
		lbl[1]=new Label("Minutes");
		lbl[2]=new Label("Seconds");
		lbl[3]=new Label("Result in seconds");
		lbl[4]=new Label("Date(dd-MM-yyyy)");
		lbl[5]=new Label("Time(hh:mm)");
		cbg=new CheckboxGroup();
		chk1=new Checkbox("Duration",true,cbg);
		chk2=new Checkbox("Date and Time",false,cbg);
		cal=new Button("Calculate");
		set=new Button("SET");
		abort=new Button("ABORT");
	}
	public void dispGUI()
	{
		setSize(500,500);
		GridBagLayout gbl=new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		//gbc.gridwidth = GridBagConstraints.RELATIVE;
		for(int i=0;i<4;i++)
		{
		  gbc.gridx=0;
		  gbc.gridy=i;
		  gbl.setConstraints(lbl[i],gbc);
		  add(lbl[i]);
		  gbc.gridx=1;
		  gbc.gridy=i;
		  gbl.setConstraints(txt[i],gbc);
     	  add(txt[i]);
        }
        for(int i=4;i<6;i++)
		{		
		  gbc.gridx=2;
		  gbc.gridy=i-4;
		  gbl.setConstraints(lbl[i],gbc);
		  add(lbl[i]);
		  gbc.gridx=3;
		  gbc.gridy=i-4;
		  gbl.setConstraints(txt[i],gbc);
     	  add(txt[i]);
		} 
		gbc.gridx=0;
		gbc.gridy=4;
		gbc.gridwidth=2;
		gbl.setConstraints(chk1,gbc);
		add(chk1);
		gbc.gridx=2;
		gbc.gridy=4;
		gbl.setConstraints(chk2,gbc);
		add(chk2);
		gbc.gridx=0;
		gbc.gridy=5;
		gbc.gridwidth=5;
		gbl.setConstraints(cal,gbc);
	    add(cal);
		gbc.gridwidth=2;
		gbc.gridx=0;
		gbc.gridy=6;
		gbl.setConstraints(set,gbc);
     	add(set);
		gbc.gridx=2;
		gbc.gridy=6;
		gbl.setConstraints(abort,gbc);
     	add(abort);
		cal.addActionListener(this);
        abort.addActionListener(this);
        set.addActionListener(this);	
		setVisible(true);		
	}
	void calculateSeconds()
	{
		int hr=0,mi=0,se=0,res;	
		if(chk1.getState())
		{	
		  if(!txt[0].getText().isEmpty()){
		    hr=Integer.parseInt(txt[0].getText());}
		  if(!txt[1].getText().isEmpty())
		    mi=Integer.parseInt(txt[1].getText());
		  if(!txt[2].getText().isEmpty())
		    se=Integer.parseInt(txt[2].getText());
    	  res=hr*3600+mi*60+se;
		 txt[3].setText(((Integer)res).toString());
		}
		else
		{	
		  Date d=null;
		  SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyyhh:mm");
		  try{
		   d=sdf.parse(txt[4].getText()+txt[5].getText());
		  }
		  catch(Exception ex)
		  {
     		ex.printStackTrace();
		  }
		//System.out.println(d);
		  Long sec=d.getTime()-System.currentTimeMillis();
		  sec/=1000;
		  txt[3].setText(sec.toString());
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		String cmd=ae.getActionCommand();
		if(cmd=="ABORT")
		{
			Runtime r=Runtime.getRuntime();
			try{
			Process p=r.exec("shutdown.exe /a");}
			catch(Exception ex){
				ex.printStackTrace();
			}
			return;
		}
		calculateSeconds();
		if(cmd=="SET")
        {
	      Runtime r=Runtime.getRuntime();
	      try{
			  Process p=r.exec("shutdown.exe /s /hybrid /t "+txt[3].getText());
			 }
			catch(Exception ex){
				ex.printStackTrace();
			}
	    }
	}
}
class ShutdownTimer
{
	
	public static void main(String args[])
	{
		MyFrame mf=new MyFrame("Shutdown Timer");
		mf.dispGUI();
	}
}