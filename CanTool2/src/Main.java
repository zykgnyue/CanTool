import java.awt.Window;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.impl.Grego;

import gnu.io.SerialPort;
import serialPort.SerialTool;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;

public class Main extends ApplicationWindow {
	private Text text;
	private Label lblNewLabel=null;
	private Group grpTextD=null;
	public static SerialPort serialPort=null;
	public static StringBuffer stringBuffer=new StringBuffer();
	public static String receive_data=null;
	public static StringBuffer receive_data_buffer=new StringBuffer();
	private Action action;
	public Shell shell;
	public static read_database read_database2;
	public static Map map;
	public static String smart_send_data=null;
	private Text text_1;
	public static Text text_2;
	private Action action_1;
	private Action action_2;
	private static Text text_3;
	public static StringBuffer sb=new StringBuffer();
	/**
	 * Create the application window.
	 */
	public Main() {
		super(null);
		createActions();
//		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		read_database2=new read_database();
		 map =read_database2.analyze_database("database.txt");
		 try{
			  serialPort = SerialTool.openPort("COM4", 9600);
			  serialPort.setSerialPortParams(Integer.parseInt("9600"), Integer.parseInt("8"), Integer.parseInt("1"), SerialPort.PARITY_NONE);
			 SerialTool.addListener(serialPort, new SerialListener(serialPort));
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 
		 }
		 
	}
	private void createActions() {
		{
			action_1 = new Action("\u5173\u4E8E") {
			};
		}
		{
			action_2 = new Action("\u5173\u4E8E") {				@Override
				public void run() {
					final Display display = Display.getDefault();
					final Shell shell = new Shell();
					shell.setLocation(Display.getDefault().getCursorLocation());
					shell.setSize(200, 120);
//					shell.setText("MessageBox 实例");
					MessageDialog.openInformation(shell, null, "这个软件是由21组做的CanToolAPP，实现是基于Eclipse集成开发平台，使用java语言，并且利用了SWT/Jface插件。\r\n主要成员有:\r\n陈煌榕\r\n陈育健\r\n李俊\r\n袁琳琳");
					
					
				}
			};
		}
	}

	
	
	public static byte[] intToBytes2(int n){
	    byte[] b = new byte[4];
	    for(int i = 0;i < 4;i++)
	    {
			b[i]=(byte)(n>>(24-i*8));
	    } 
	    return b;
	}

	public static int toInt(byte[] bi) {
		int len = bi.length;
		int sum = 0;
		int tmp,  max = len - 1;
		for (int i = 0; i < len; ++i) {
		tmp = bi[i];
		sum += tmp * Math.pow(2, max--);
		}
		return sum;
		}
	
	public static byte[] subBytes(byte[] src, int begin, int count) {  
	    byte[] bs = new byte[count];  
	    System.arraycopy(src, begin, bs, 0, count);  
	    return bs;  
	} 
	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.EMBEDDED);
		text = new Text(container, SWT.BORDER);
		text.setBounds(0, 0, 530, 25);	
		Button btnInput = new Button(container, SWT.NONE);
		btnInput.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			        String temp;
			        //以波特率115200打开串口COM11
//			        SerialPort serialPort;
					try {
//						serialPort = SerialTool.openPort("COM3", 115200);
							
				            SerialTool.sendToPort(serialPort,text.getText().getBytes() );
				        
					} catch (Exception e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
			}
		});
		btnInput.setBounds(536, -2, 60, 27);
		btnInput.setText("\u53D1\u9001\u6570\u636E");
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setBounds(0, 31, 80, 17);
		lblNewLabel_1.setText("\u63A5\u6536\u5230\u7684\u5185\u5BB9\uFF1A");
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setBounds(0, 54, 665, 305);
		
		text_2 = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_2.setBounds(271, 0, 394, 305);
		
		text_3 = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_3.setBounds(0, 0, 272, 305);
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Send_Values test1=new Send_Values();
				test1.run();
			}
		});
		btnNewButton.setBounds(600, 0, 65, 25);
		btnNewButton.setText("\u667A\u80FD\u53D1\u9001");
		
		
		new Thread() {//线程操作
            public void run() {
                while(true){
                
                
            	text_2.getDisplay().asyncExec(new Runnable() {
            		public void run() {
            			if(smart_send_data!=null){
//            				text.setText("");
                      		 text.setText(smart_send_data);
                      		 smart_send_data=null;
                      	 }
            		};
            	});
            	
                
                	char[] data=null;
                		if(SerialListener.receive_data!=null){
                			data=SerialListener.receive_data.toCharArray();
                		}
                	
                
                	if(SerialListener.receive_data==null){
                		
                	}
                	else if((data[0]=='t'||data[0]=='T'))
                	{     
                		try {
			                    	//lblNewLabel.getDisplay().asyncExec(new Runnable() {
			                    	text_2.getDisplay().asyncExec(new Runnable() {
			                         @Override
			                         public void run() {
//			                        	 if(smart_send_data!=null){
//			                        		 text.setText(smart_send_data);
//			                        		 smart_send_data=null;
//			                        	 }
			                       if(SerialListener.receive_data!=null){
			                    	   receive_data_buffer.append(SerialListener.receive_data+"\r\n");
			//                    	   String str="t12380011121314151617\t";
			                    	   	byte[] sequence_one=new byte[64];//用于存储data的byte[]
				                   		CAnalData cAnalData=new CAnalData(SerialListener.receive_data);
				                   		cAnalData.computeData();
				                   		char flag=cAnalData.getFLAG();
				                   		String id=String.valueOf(Integer.parseInt(cAnalData.getID(),16));
				                   		int dlc=cAnalData.getDLC();
				                   		int length=cAnalData.getDATA().size();
				                   		StringBuffer data=new StringBuffer();
				                   		int index=0;
				                   		for(int i=0;i<length;i++){
				                   			byte[] temp=Integer.toBinaryString(Integer.valueOf(String.valueOf(cAnalData.getDATA().get(i)[0])).intValue()).getBytes();
				                   			byte[] temp1=new byte[4];
				                   			for(int ii=0;ii<4;ii++){
				                   				temp1[ii] = 0;
				                   			}
				                   			
				                   			for(int k=0;k<temp.length;k++){
				                   				temp[k]=(byte) (temp[k]-48);
				                   			}
				                   			int jj=3;
				                   			int len=temp.length;
				                   			while((len-1)>=0){
				                   				temp1[jj]=temp[len-1];
				                   				len--;
				                   				jj--;
				                   			}
				                   					
				                   			for(int j =0;j<4;j++){
				                   				sequence_one[index]=temp1[j];
				                   				index++;
				                   			}
				                   			byte[] temp2=Integer.toBinaryString(Integer.valueOf(String.valueOf(cAnalData.getDATA().get(i)[1])).intValue()).getBytes();
				                   			byte[] temp3=new byte[4];
				                   			for(int ii=0;ii<4;ii++){
				                   				temp3[ii] = 0;
				                   			}
				                   			for(int k=0;k<temp2.length;k++){
				                   				temp2[k]=(byte) (temp2[k]-48);
				                   			}
				                   			int jjj=3;
				                   			int len1=temp2.length;
				                   			while((len1-1)>=0){
				                   				temp3[jjj]=temp2[len1-1];
				                   				len1--;
				                   				jjj--;
				                   			}
				                   			for(int j =0;j<4;j++){
				                   				sequence_one[index]=temp3[j];
				                   				index++;
				                   			}
				                		}
				                   		
				                   		String data_once="flag:"+flag+'\t'+"id:"+id+'\t'+"dlc:"+dlc+'\t'+"length:"+length+"\t"+"data:"+data;
				                   		
				                   		System.out.println(map.containsKey(id)+"");
				                   		if(map.containsKey(id)){
				                   			List<database_Dao> list = (List<database_Dao>) map.get(id);
					                   		for(int i=0;i<list.size();i++){
					                   			 database_Dao database_Dao2=list.get(i);
					                   			 String signal_name=database_Dao2.getSignal_name();
					                   			 int start_position=database_Dao2.getStart_position();
					                   			 int length1=database_Dao2.getLength();
					                   			 float A=database_Dao2.getA();
					                   			 float B=database_Dao2.getB();
					                   			 String sign=database_Dao2.getSign();
					                   			 float finalnum=Float.valueOf(toInt(subBytes(sequence_one,start_position,length1)))*A+B;
					                   			 stringBuffer.append(signal_name+" : "+finalnum+"\n\r");
			//		                   			 System.out.println("signal_name:"+signal_name+"start_position:"+start_position+"length"+length1+"A:"+A+"B:"+B+"sign:"+sign);
					                   		}
				                   		}
			//	                   		stringBuffer.append(data_once+'\n');
			                            //lblNewLabel.setText(stringBuffer.toString());//输出到Label上
				                   		text_3.setText(receive_data_buffer.toString());
				                   		text_2.setText(stringBuffer.toString());
			                            SerialListener.receive_data=null;
			                       }
			                         }
			                     });
			                     Thread.sleep(1000);//每隔一秒刷新一次
			                 } catch (Exception e) {
			                	text_3.setText( SerialListener.receive_data);
			                	System.out.println("fu");
			                  	SerialListener.receive_data=null;
			                	 e.printStackTrace();
			                	 
			                 }
                	}else{
                		text_3.getDisplay().asyncExec(new Runnable() {
                    		public void run() {
                    			receive_data_buffer.append(SerialListener.receive_data+"\r\n");
                        		text_3.setText(receive_data_buffer.toString());
        	                  	SerialListener.receive_data=null;
                    		};
                    	});
                		
                	}
                    try {
                    	
//                    	
                    	Thread.sleep(1000);//每隔一秒刷新一次
					} catch (Exception e) {
						// TODO: handle exception
					}
                    			
                }
            }
     }.start();
     
     
		return container;
	}

	

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		
		MenuManager menuManager_1 = new MenuManager("\u53C2\u6570\u8BBE\u7F6E");
		Setting_COM_parameter setting_com_parameter = new Setting_COM_parameter(shell);
		Import_File import_file=new Import_File();
		Output_File output_File=new Output_File();
		output_File.setText("\u5BFC\u51FA\u6570\u636E");
		import_file.setText("导入数据文件");
		setting_com_parameter.setToolTipText("\u8BBE\u7F6E\u4E32\u53E3\u53C2\u6570");
		setting_com_parameter.setText("\u8BBE\u7F6E\u53C2\u6570");
		
		
		menuManager_1.setVisible(true);
		MenuManager menuManager_2 = new MenuManager("New MenuManager");
		menuManager_2.setMenuText("\u6587\u4EF6");
		menuManager.add(menuManager_2);
		menuManager_2.add(output_File);
		menuManager_2.add(import_file);
		
		menuManager.add(menuManager_1);
		menuManager_1.add(setting_com_parameter);
		menuManager.add(action_2);
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
//	@Override
//	protected ToolBarManager createToolBarManager(int style) {
//		ToolBarManager toolBarManager = new ToolBarManager(style);
//		return toolBarManager;
//	}

//	/**
//	 * Create the status line manager.
//	 * @return the status line manager
//	 */
//	@Override
//	protected StatusLineManager createStatusLineManager() {
//		StatusLineManager statusLineManager = new StatusLineManager();
//		return statusLineManager;
//	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Main window = new Main();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		shell=newShell;
		newShell.setText("CanTool");
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				Display.getDefault().dispose();  
		        System.exit(0); 
			}
		});
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(681, 450);
	}
}
