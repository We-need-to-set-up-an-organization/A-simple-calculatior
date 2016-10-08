import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.*;

/**
 * 一个科学计算器，与Windows附件自带计算器的科学版功能、界面相仿。 支持键盘操作。
 */
public class Science_Calculator extends JFrame implements ActionListener,MouseListener,KeyListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3766098379635768992L;
	/** 计算器上的键的显示名字 */
    private final String[] KEYS = { "CE", "C", "←", "÷", "7", "8", "9", "×",
            "4", "5", "6", "－", "1", "2", "3", "＋", "±", "0", "·", "=" };
    /** 计算器上的功能键的显示名字 */
    private final String[] COMMAND = { "%", "√", "x²" ,"1/x"};
    /** 计算器上的M键的显示名字 */
    private final String[] M = {"MC", "MR", "MS", "M+","M-","M1" };
    /** 计算器上的切换键的显示名字 */
    private final String[] SWITCHBUTTON = {"⇌","↺"}; 
    /** 计算器上键的按钮 */
    private JButton keys[] = new JButton[KEYS.length];
    /** 计算器上的功能键的按钮 */
    private JButton commands[] = new JButton[COMMAND.length];
    /** 计算器左边的M的按钮 */
    private JButton m[] = new JButton[M.length];
    /** 计算器上的切换键的按钮 */
    private final JButton switchbutton[] = new JButton[SWITCHBUTTON.length]; 
    /** 计算结果文本框 */
    private JTextField resultText = new JTextField("0");
    /** 计算过程文本框 */
    private JTextField operationText = new JTextField("");
    /** 占位文本框 */
    private JTextField nullText = new JTextField("");
    /** 切换标示文本框 */
    private JTextField switchText = new JTextField("  标准");
    /** 历史文本域 */
    private JTextArea historyText = new JTextArea("尚无历史记录");
    /** 运算键画板 */
    private JPanel calckeysPanel = new JPanel();
    /** 功能键画板 */
    private JPanel commandsPanel = new JPanel();
    /** M键画板 */
    private JPanel calmsPanel = new JPanel();
    /** 按键画板 */
    private JPanel panel1 = new JPanel();
    /** 文本框画板 */
    private JPanel top = new JPanel();
    /** 功能切换画板*/
    private JPanel switchpanel = new JPanel();
    
    // 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
    private boolean firstDigit = true;
    // 计算的中间结果
    private double resultNum = 0.0;
    // 计算的存储结果
    private double memory[] ={0.0,0.0,0.0};
    // 当前储存的内存编号
    private int memoryList;
    // 当前运算的运算符
    private String operator = "=";
    // 当前内存是否存有数字
    private boolean memoryFlag[] = {false,false,false};
    // 当前是否具有数字输入
    private boolean operation = true;
    // 操作是否合法
    private boolean operateValidFlag = true;
 
    /**
     * 构造函数
     */
    public Science_Calculator() {
        super();
        // 初始化计算器
        init();
        // 设置计算器的背景颜色
        this.setForeground(Color.black);
        this.setTitle("计算器");
        // 在屏幕(500, 300)坐标处显示计算器
        this.setLocation(500, 300);
        // 不许修改计算器的大小
        this.setResizable(false);
        // 使计算器中各组件大小合适
        this.pack();
    }
 
    /**
     * 初始化计算器
     */
    private void init() {
        // 文本框中的内容采用右对齐方式
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        operationText.setHorizontalAlignment(JTextField.RIGHT);
        switchText.setHorizontalAlignment(JTextField.LEFT);
        // 不允许修改结果文本框
        resultText.setEditable(false);
        operationText.setEditable(false);
        nullText.setEditable(false);
        switchText.setEditable(false);
        // 设置文本框背景颜色为黑色，无边框,字体为白色，调整字号
        resultText.setBackground(new Color(0,0,0));
        resultText.setForeground(new Color(255,251,240));
        resultText.setBorder(null);
        resultText.setFont(new java.awt.Font("微软雅黑", 0, 54));
        operationText.setBackground(new Color(0,0,0));
        operationText.setForeground(new Color(200,200,200));
        operationText.setBorder(null);
        operationText.setFont(new java.awt.Font("微软雅黑", 0, 18));
        nullText.setBackground(new Color(0,0,0));
        nullText.setBorder(null);
        switchText.setBackground(new Color(0,0,0));
        switchText.setForeground(new Color(255,251,240));
        switchText.setBorder(null);
        switchText.setFont(new java.awt.Font("微软雅黑", 1, 20));
        // 添加事件侦听器以便输入
        resultText.addKeyListener(this);
        operationText.addKeyListener(this);
        nullText.addKeyListener(this);
        switchText.addKeyListener(this);
        
        // 初始化计算器上键的按钮，将键放在一个画板内
        // 用网格布局器，4行，5列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
        // 前景色白色，背景色灰色，不显示边框和焦点框，大小为水平方向99个象素，垂直方向69个象素
        calckeysPanel.setLayout(new GridLayout(5, 4, 0, 0));
        for (int i = 0; i < KEYS.length; i++) {
            keys[i] = new JButton(KEYS[i]);
            calckeysPanel.add(keys[i]);
            keys[i].setBackground(Color.darkGray);
            keys[i].setForeground(Color.white);
            keys[i].setBorderPainted(false);
            keys[i].setFocusPainted(false);
            keys[i].setPreferredSize(new Dimension(99,69));
        }
        
        // 调整各按钮的字体和字号
        keys[0].setFont(new java.awt.Font("Malgun Gothic", 0, 20));
        keys[1].setFont(new java.awt.Font("Malgun Gothic", 0, 20));
        keys[2].setFont(new java.awt.Font("微软雅黑", 0, 20));
        keys[3].setFont(new java.awt.Font("Yu Mincho Light", 0, 26));
        keys[4].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[5].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[6].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[7].setFont(new java.awt.Font("Yu Mincho Light", 0, 26));
        keys[8].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[9].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[10].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[11].setFont(new java.awt.Font("Yu Mincho Light", 0, 26));
        keys[12].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[13].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[14].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[15].setFont(new java.awt.Font("Yu Mincho Light", 0, 26));
        keys[16].setFont(new java.awt.Font("Yu Mincho Light", 0, 26));
        keys[17].setFont(new java.awt.Font("微软雅黑", 0, 26));
        keys[18].setFont(new java.awt.Font("微软雅黑", 0, 34));
        keys[19].setFont(new java.awt.Font("Yu Mincho Light", 0, 26));       
        
        // 初始化功能键，将功能键放在一个画板内 
        // 将边框设置为上下两侧，宽度为1个象素，颜色深灰
        commandsPanel.setBorder(new MatteBorder(1, 0, 1, 0, new Color(100, 100, 100)));
        // 用网格布局器，1行，3列的网格，网格之间的水平方向间隔为0个象素，垂直方向间隔为0个象素
        // 前景色白色，背景色灰色，不显示边框和焦点框，大小为水平方向99个象素，垂直方向69个象素
        commandsPanel.setLayout(new GridLayout(1, 4, 0, 0));
        for (int i = 0; i < COMMAND.length; i++) {
            commands[i] = new JButton(COMMAND[i]);
            commandsPanel.add(commands[i]);
            commands[i].setBackground(Color.darkGray);
            commands[i].setForeground(Color.white);
            commands[i].setBorderPainted(false);
            commands[i].setFocusPainted(false);
            commands[i].setPreferredSize(new Dimension(99,69));
        }
        
        commands[0].setFont(new java.awt.Font("Yu Mincho", 0, 18));
        commands[1].setFont(new java.awt.Font("Yu Mincho", 0, 18));
        commands[2].setFont(new java.awt.Font("Times New Roman", 2, 20));
        commands[3].setFont(new java.awt.Font("Times New Roman", 2, 20));
 
        // 初始化M键，将M键放在一个画板内
        // 用网格布局管理器，5行，1列的网格，网格之间的水平方向间隔为0个象素，垂直方向间隔为0个象素
        // 前景色白色，背景色灰色，不显示边框和焦点框，大小为水平方向66个象素，垂直方向40个象素
        calmsPanel.setLayout(new GridLayout(1, 6, 0, 0));
        for (int i = 0; i < M.length; i++) {
            m[i] = new JButton(M[i]);
            calmsPanel.add(m[i]);
            m[i].setBackground(Color.black);
            m[i].setForeground(Color.white);
            m[i].setBorderPainted(false);
            m[i].setFocusPainted(false);
            m[i].setPreferredSize(new Dimension(66,40));
            m[i].setFont(new java.awt.Font("微软雅黑", 0, 14));
        }
 
        // 下面进行计算器的整体布局，将calckeys和command画板放在计算器的中部，
        // 将文本框和calms画板放在计算器南部。
        
        // 初始化一个大的画板，将上面建立的command、calckeys和calmsPanel画板放在里面
        // 画板采用边界布局管理器，画板里组件之间的水平和垂直方向上间隔都为0象素
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.add("North", calmsPanel);
        panel1.add("Center", commandsPanel);
        panel1.add("South", calckeysPanel);
        
        // 初始化一个画板，将文本框放在里面
        top.setLayout(new BorderLayout(0, 0));
        top.add("North", operationText);
        top.add("Center", resultText);
        top.add("South", nullText);
        
        // 初始化切换键，将切换键放在一个画板内
        switchpanel.setLayout(new BorderLayout(0,0)); 
        for (int i = 0; i < SWITCHBUTTON.length; i++) {
        switchbutton[i] = new JButton(SWITCHBUTTON[i]);
        switchbutton[i].setBackground(Color.black);
        switchbutton[i].setForeground(Color.white);
        switchbutton[i].setBorderPainted(false);
        switchbutton[i].setFocusPainted(false);
        switchbutton[i].setPreferredSize(new Dimension(60,60));
        switchbutton[i].setFont(new java.awt.Font("", 0, 24));
        }
        // 将切换键放在两侧，切换标示文本框放在中间，向左对齐
        switchpanel.add("West", switchbutton[0]);
        switchpanel.add("Center", switchText);
        switchpanel.add("East", switchbutton[1]);
 
        // 整体布局
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane().add("North", switchpanel);
        getContentPane().add("Center", top);
        getContentPane().add("South", panel1);
        
        // 为各按钮添加事件侦听器、鼠标侦听器和键盘侦听器
        // 都使用同一个事件侦听器，即本对象。本类的声明中有implements ActionListener
        for (int i = 0; i < KEYS.length; i++) {
            keys[i].addActionListener(this);
            keys[i].addMouseListener(this);
            keys[i].addKeyListener(this);
        }
        for (int i = 0; i < COMMAND.length; i++) {
            commands[i].addActionListener(this);
            commands[i].addMouseListener(this);
            commands[i].addKeyListener(this);
        }
        for (int i = 0; i < M.length; i++) {
            m[i].addActionListener(this);
            m[i].addMouseListener(this);
            m[i].addKeyListener(this);
        }
        for (int i = 0; i < SWITCHBUTTON.length; i++) {
            switchbutton[i].addActionListener(this);
            switchbutton[i].addMouseListener(this);
            switchbutton[i].addKeyListener(this);
        }
    }
    
    /**
     * 处理鼠标
     */
    public void mouseClicked(MouseEvent e){
    }
    
    public void mouseEntered(MouseEvent e){
    	// 获取事件源的标签
    	JButton btn = (JButton) e.getSource();
    	// 组件颜色深化
    	btn.setBackground(Color.LIGHT_GRAY);
    }

    public void mouseExited(MouseEvent e){
     	// 获取事件源的标签
    	JButton btn = (JButton) e.getSource();
    	// 组件颜色浅化
    	btn.setBackground(Color.darkGray);
    	for (int i = 0; i < M.length; i++) {
    		if(btn == m[i])
    			btn.setBackground(Color.BLACK);
    	}for (int i = 0; i < SWITCHBUTTON.length; i++) {
    		if(btn == switchbutton[i])
    			btn.setBackground(Color.BLACK);
    	}
    }
    
    public void mousePressed(MouseEvent e){
    	// 无事件
    }
    
    public void mouseReleased(MouseEvent e){
    	// 无事件
    }
    
    /**
     * 处理键盘
     */
    public void keyTyped(KeyEvent e){
    	// 获取按键 
    	if (e.getKeyChar()=='\b'){
        	// 用户按了"Backspace"键
    		handleBackspace();
    	}else if("0123456789".indexOf(e.getKeyChar()) >= 0){ 
    		// 用户按了数字键
    		handleNumber(String.valueOf(e.getKeyChar()));
        }else if(e.getKeyChar()=='/'){
        	// 用户按了"/"键
        	handleOperator("÷");
        }else if(e.getKeyChar()=='*'){
        	// 用户按了"*"键
        	handleOperator("×");
        }else if(e.getKeyChar()=='-'){
        	// 用户按了"-"键
        	handleOperator("－");
        }else if(e.getKeyChar()=='+'){
        	// 用户按了"+"键
        	handleOperator("＋");
        }else if(e.getKeyChar()=='='){
        	// 用户按了"="键
        	handleOperator(String.valueOf(e.getKeyChar()));
        }else if(e.getKeyChar()=='。'){
        	// 用户按了"。"键
        	handleNumber("·");
        }else if(e.getKeyChar()=='.'){
        	// 用户按了小数点键
        	handleNumber("·");
        }
    }
    
    public void keyPressed(KeyEvent e){
    	// 分类
    	if (e.getKeyChar()=='\b'){
    		// 用户按了"Backspace"键
    		keys[2].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='1'){
    		// 用户按了"1"键
    		keys[12].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='2'){
    		// 用户按了"2"键
    		keys[13].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='3'){
    		// 用户按了"3"键
    		keys[14].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='4'){
    		// 用户按了"4"键
    		keys[8].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='5'){
    		// 用户按了"5"键
    		keys[9].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='6'){
    		// 用户按了"6"键
    		keys[10].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='7'){
    		// 用户按了"7"键
    		keys[4].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='8'){
    		// 用户按了"8"键
    		keys[5].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='9'){
    		// 用户按了"9"键
    		keys[6].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='0'){
    		// 用户按了"0"键
    		keys[17].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='+'){
    		// 用户按了"+"键
    		keys[15].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='-'){
    		// 用户按了"-"键
    		keys[11].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='/'){
    		// 用户按了"/"键
    		keys[3].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='*'){
    		// 用户按了"*"键
    		keys[7].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='='){
    		// 用户按了"="键
    		keys[19].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='。'){
    		// 用户按了"。"键
    		keys[18].setBackground(Color.LIGHT_GRAY);
    	}else if(e.getKeyChar()=='.'){
    		// 用户按了"."键
    		keys[18].setBackground(Color.LIGHT_GRAY);
    	}
    }
    
    public void keyReleased(KeyEvent e){
    	// 分类
    	if (e.getKeyChar()=='\b'){
    		// 用户按了"Backspace"键
    		keys[2].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='1'){
    		// 用户按了"1"键
    		keys[12].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='2'){
    		// 用户按了"2"键
    		keys[13].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='3'){
    		// 用户按了"3"键
    		keys[14].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='4'){
    		// 用户按了"4"键
    		keys[8].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='5'){
    		// 用户按了"5"键
    		keys[9].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='6'){
    		// 用户按了"6"键
    		keys[10].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='7'){
    		// 用户按了"7"键
    		keys[4].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='8'){
    		// 用户按了"8"键
    		keys[5].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='9'){
    		// 用户按了"9"键
    		keys[6].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='0'){
    		// 用户按了"0"键
    		keys[17].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='+'){
    		// 用户按了"+"键
    		keys[15].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='-'){
    		// 用户按了"-"键
    		keys[11].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='/'){
    		// 用户按了"/"键
    		keys[3].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='*'){
    		// 用户按了"*"键
    		keys[7].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='='){
    		// 用户按了"="键
    		keys[19].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='。'){
    		// 用户按了"。"键
    		keys[18].setBackground(Color.darkGray);
    	}else if(e.getKeyChar()=='.'){
    		// 用户按了"."键
    		keys[18].setBackground(Color.darkGray);
    	}
    }
    
    /**
     * 处理事件
     */
    public void actionPerformed(ActionEvent e) {
        // 获取事件源的标签
        String label = e.getActionCommand();
        if (label.equals(KEYS[2])) {
            // 用户按了"Backspace"键
            handleBackspace();
        } else if (label.equals(KEYS[0])) {
            // 用户按了"CE"键
            resultText.setText("0");
        } else if (label.equals(KEYS[1])) {
            // 用户按了"C"键
            handleC();
        } else if ("0123456789·".indexOf(label) >= 0) {
            // 用户按了数字键或者小数点键
            handleNumber(label);
        } else if("MSMRMCM+M-M1M2M3".indexOf(label) >= 0) {
        	//用户按了M键
        	handleM(label);
        } else if("⇌↺↻".indexOf(label)>= 0){
        	//用户按了切换键
        	handleSwitch(label);
        } else{
            // 用户按了运算符键
            handleOperator(label);
        }
    }
    
    /**
     * 处理Backspace键被按下的事件
     */
    private void handleBackspace() {
        String text = resultText.getText();
        int i = text.length();
        if (i > 0) {
            // 退格，将文本最后一个字符去掉
            text = text.substring(0, i - 1);
            if (text.length() == 0 || text.equals("除数不能为")) {
                // 如果文本没有了内容，则初始化计算器的各种值
                resultText.setText("0");
                operationText.setText("");
                resultNum = 0;
                firstDigit = true;
                operation = true;
                operator = "=";
            } else {
                // 显示新的文本
                resultText.setText(text);
            }
        }
    }
 
    /**
     * 处理数字键被按下的事件
     *
     * @param key
     */
    private void handleNumber(String key) {
    	if ((key.equals("·")) && (resultText.getText().indexOf(".") < 0)) {
            // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
            resultText.setText(resultText.getText() + ".");
        }else if (firstDigit) {
            // 输入的第一个数字
            resultText.setText(key);
        }  else if (!key.equals("·")) {
            // 如果输入的不是小数点且不是第一个数字，则将数字附在结果文本框的后面
            resultText.setText(resultText.getText() + key);
        }
        // 以后输入的肯定不是第一个数字了
        firstDigit = false;
        // 类型为数字符
        operation = true;
    }
 
    /**
     * 处理C键被按下的事件
     */
    private void handleC() {
        // 初始化计算器的各种值
        resultText.setText("0");
        operationText.setText("");
        resultNum = 0;
        firstDigit = true;
        operation = true;
        operator = "=";
    }
 
    /**
     * 处理M键被按下的事件
     */
    private void handleM(String key) {
        long t1;
        double t2;
    	 if (key.equals("MC")) {
         	memory[memoryList] = 0;
         } else if (key.equals("MS")) {
         	memory[memoryList] = getNumberFromText();
         } else if (key.equals("MR")) {
         	t1 = (long) memory[memoryList];
             t2 = memory[memoryList] - t1;
             if (t2 == 0) {
                 resultText.setText(String.valueOf(t1));
             } else {
                 resultText.setText(String.valueOf(memory[memoryList]));
             }
         } else if (key.equals("M+")) {
         	memory[memoryList] += getNumberFromText();
         } else if (key.equals("M+")) {
         	memory[memoryList] -= getNumberFromText();
         } else if (key.equals("M1")) {
         	memoryList = 1;
         	m[5].setText("M2");
         	// panel1.repaint(); 
         	// panel1.revalidate(); 
         } else if (key.equals("M2")) {
         	m[5].setText("M3");
         	memoryList = 2;
         	// panel1.revalidate(); 
         } else if (key.equals("M3")) {
         	m[5].setText("M1");
         	memoryList = 0;
         	// panel1.revalidate(); 
         } 
    }
    
    /**
     * 处理切换键被按下的事件
     */
    private void handleSwitch(String key) {
    	if (key.equals("⇌")) {
    		
    	} else	{
    	
    	}
    }
    
    /**
     * 处理运算符键被按下的事件
     *
     * @param key
     */
    private void handleOperator(String key) {
        long t1;
        double t2;
        if ("＋－×÷".indexOf(key) >= 0) {
        	operationAction();
            operation = false;
            operationText.setText(operationText.getText()+" "+key+" ");
        } else if(key.equals("1/x")){
        	if(operation) operationAction();
            operation = false;
        	  // 倒数运算
            if (resultNum == 0.0) {
                // 操作不合法
                operateValidFlag = false;
                resultText.setText("除数不能为零");
                operator = "=";
            } else {
                resultNum = 1 / resultNum;
                operationText.setText("1/("+operationText.getText()+")");
            }
        } else if (key.equals("x²")) {
        	if(operation) operationAction();
        	// 寻找基本运算符并删去
        	else operationText.setText(operationText.getText().substring(0,operationText.getText().length()-3)+operationText.getText().substring(operationText.getText().length()-3,operationText.getText().length()).replace(" "+operator+" ",""));	
            operation = false;
            // 平方运算
        	resultNum *= resultNum;
        	operationText.setText("sqr("+operationText.getText()+")");
        } else if (key.equals("√")) {
        	if(operation) operationAction();
        	else operationText.setText(operationText.getText().substring(0,operationText.getText().length()-3)+operationText.getText().substring(operationText.getText().length()-3,operationText.getText().length()).replace(" "+operator+" ",""));	        	operation = false;
            operation = false;
            // 平方根运算
            resultNum = Math.sqrt(resultNum);
            operationText.setText("sqrt("+operationText.getText()+")");
        } else if (key.equals("%")) {
        	if(operation) operationAction();
        	else operationText.setText(operationText.getText().substring(0,operationText.getText().length()-3)+operationText.getText().substring(operationText.getText().length()-3,operationText.getText().length()).replace(" "+operator+" ",""));	        	operation = false;
            operation = false;
            // 百分号运算，除以100
            resultNum = resultNum / 100;
        } else if (key.equals("±")) {
        	if(operation) operationAction();
        	else operationText.setText(operationText.getText().substring(0,operationText.getText().length()-3)+operationText.getText().substring(operationText.getText().length()-3,operationText.getText().length()).replace(" "+operator+" ",""));	        	operation = false;
            operation = false;
            // 正数负数运算
            resultNum = resultNum * (-1);
        } else if (key.equals("=")) {
        	operationAction();
            operation = true;
        	operationText.setText("");
        }
        if (operateValidFlag) {
            // 双精度浮点数的运算
            t1 = (long) resultNum;
            t2 = resultNum - t1;
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));
            } else {
                resultText.setText(String.valueOf(resultNum));
            }
            resultNum = getNumberFromText();
            operator = key;
        } 
        // 运算符等于用户按的按钮
        firstDigit = true;
        operateValidFlag = true;
    }
 
    /**
     * 从结果文本框中获取数字
     *
     * @return
     */
    private double getNumberFromText() {
        double result = 0;
        try {
            	result = Double.valueOf(resultText.getText()).doubleValue();
            
        } catch (NumberFormatException e) {
        }
        return result;
    }
    
    /**
     * 改变运算文本框的数字
     *
     * @param key
     */
    private void operationAction(){
        long t1;
        double t2;
        if(!operation){
    		// 防止误输入
        	resultNum = getNumberFromText();
        	operationText.setText(operationText.getText().substring(0,operationText.getText().length()-3));
        	// 当前输入运算符
        	operator = "=";
        }else{
        	// 显示运算过程
        	t1 = (long) getNumberFromText();
        	t2 = getNumberFromText() - t1;
        	if (t2 == 0) {
        		operationText.setText(operationText.getText()+t1);
        	}else{
        		operationText.setText(operationText.getText()+getNumberFromText());
        	}
        }
        if (operator.equals("÷")) {
            // 除法运算
            // 如果当前结果文本框中的值等于0
            if (getNumberFromText() == 0.0) {
                // 操作不合法
                operateValidFlag = false;
                resultText.setText("除数不能为零");
            } else {
                resultNum /= getNumberFromText();
            }
        }  else if (operator.equals("＋")) {
            // 加法运算
            resultNum += getNumberFromText();
        } else if (operator.equals("－")) {
            // 减法运算
            resultNum -= getNumberFromText();
        } else if (operator.equals("×")) {
            // 乘法运算
            resultNum *= getNumberFromText();
        } else if (operator.equals("=")){
        	// 赋值运算
            resultNum = getNumberFromText();
        }
    }
}

