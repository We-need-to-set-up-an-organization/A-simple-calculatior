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
import javax.swing.JTextField;
 
/**
 * һ������������Windows�����Դ��������ı�׼�湦�ܡ�������¡� ������֧�ּ��̲�����
 */
public class Calculator extends JFrame implements ActionListener,MouseListener,KeyListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3766098379635768992L;
	/** �������ϵļ�����ʾ���� */
    private final String[] KEYS = { "7", "8", "9", "/", "sqrt", "4", "5", "6",
            "*", "%", "1", "2", "3", "-", "1/x", "0", "+/-", ".", "+", "=" };
    /** �������ϵĹ��ܼ�����ʾ���� */
    private final String[] COMMAND = { "��", "CE", "C" };
    /** ��������ߵ�M����ʾ���� */
    private final String[] M = { " ", "MC", "MR", "MS", "M+" };
    /** �������ϼ��İ�ť */
    private JButton keys[] = new JButton[KEYS.length];
    /** �������ϵĹ��ܼ��İ�ť */
    private JButton commands[] = new JButton[COMMAND.length];
    /** ��������ߵ�M�İ�ť */
    private JButton m[] = new JButton[M.length];
    /** �������ı��� */
    private JTextField resultText = new JTextField("0");
 
    // ��־�û������Ƿ����������ʽ�ĵ�һ������,�������������ĵ�һ������
    private boolean firstDigit = true;
    // ������м�����
    private double resultNum = 0.0;
    // ��ǰ����������
    private String operator = "=";
    // �����Ƿ�Ϸ�
    private boolean operateValidFlag = true;
 
    /**
     * ���캯��
     */
    public Calculator() {
        super();
        // ��ʼ��������
        init();
        // ���ü������ı�����ɫ
        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle("������");
        // ����Ļ(500, 300)���괦��ʾ������
        this.setLocation(500, 300);
        // �����޸ļ������Ĵ�С
        this.setResizable(false);
        // ʹ�������и������С����
        this.pack();
    }
 
    /**
     * ��ʼ��������
     */
    private void init() {
        // �ı����е����ݲ����Ҷ��뷽ʽ
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        // �������޸Ľ���ı���
        resultText.setEditable(false);
        // �����ı��򱳾���ɫΪ��ɫ
        resultText.setBackground(Color.WHITE);
 
        // ��ʼ���������ϼ��İ�ť����������һ��������
        JPanel calckeysPanel = new JPanel();
        // �����񲼾�����4�У�5�е���������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������
        calckeysPanel.setLayout(new GridLayout(4, 5, 0, 0));
        for (int i = 0; i < KEYS.length; i++) {
            keys[i] = new JButton(KEYS[i]);
            calckeysPanel.add(keys[i]);
            keys[i].setForeground(Color.black);
            keys[i].setPreferredSize(new Dimension(80,60));
        }
        // ��������ú�ɫ��ʾ������������ɫ��ʾ
        keys[3].setForeground(Color.red);
        keys[8].setForeground(Color.red);
        keys[13].setForeground(Color.red);
        keys[18].setForeground(Color.red);
        keys[19].setForeground(Color.red);
        
        // ��ʼ�����ܼ������ú�ɫ��ʾ�������ܼ�����һ��������
        JPanel commandsPanel = new JPanel();
        // �����񲼾�����1�У�3�е���������֮���ˮƽ������Ϊ0�����أ���ֱ������Ϊ0������
        commandsPanel.setLayout(new GridLayout(1, 3, 0, 0));
        for (int i = 0; i < COMMAND.length; i++) {
            commands[i] = new JButton(COMMAND[i]);
            commandsPanel.add(commands[i]);
            commands[i].setForeground(Color.red);
            commands[i].setPreferredSize(new Dimension(80,60));
        }
 
        // ��ʼ��M�����ú�ɫ��ʾ����M������һ��������
        JPanel calmsPanel = new JPanel();
        // �����񲼾ֹ�������5�У�1�е���������֮���ˮƽ������Ϊ0�����أ���ֱ������Ϊ0������
        calmsPanel.setLayout(new GridLayout(5, 1, 0, 0));
        for (int i = 0; i < M.length; i++) {
            m[i] = new JButton(M[i]);
            calmsPanel.add(m[i]);
            m[i].setForeground(Color.red);
            m[i].setPreferredSize(new Dimension(80,60));
        }
 
        // ������м����������岼�֣���calckeys��command������ڼ��������в���
        // ���ı�����ڱ�������calms������ڼ�������������
 
        // �½�һ����Ļ��壬�����潨����command��calckeys������ڸû�����
        JPanel panel1 = new JPanel();
        // ������ñ߽粼�ֹ����������������֮���ˮƽ�ʹ�ֱ�����ϼ����Ϊ0����
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.add("North", commandsPanel);
        panel1.add("Center", calckeysPanel);
 
        // ����һ��������ı���
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.add("Center", resultText);
 
        // ���岼��
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add("North", top);
        getContentPane().add("Center", panel1);
        getContentPane().add("West", calmsPanel);
        // Ϊ����ť����¼�������������������ͼ���������
        // ��ʹ��ͬһ���¼����������������󡣱������������implements ActionListener
        for (int i = 0; i < KEYS.length; i++) {
            keys[i].addActionListener(this);
            keys[i].addMouseListener(this);
            keys[i].addKeyListener(this);
            keys[i].setBackground(Color.WHITE);
        }
        for (int i = 0; i < COMMAND.length; i++) {
            commands[i].addActionListener(this);
            commands[i].addMouseListener(this);
            commands[i].addKeyListener(this);
            commands[i].setBackground(Color.WHITE);
        }
        for (int i = 0; i < M.length; i++) {
            m[i].addActionListener(this);
            m[i].addMouseListener(this);
            m[i].addKeyListener(this);
            m[i].setBackground(Color.WHITE);
        }
    }
 
    /**
     * �����¼�
     */
    public void actionPerformed(ActionEvent e) {
        // ��ȡ�¼�Դ�ı�ǩ
        String label = e.getActionCommand();
        if (label.equals(COMMAND[0])) {
            // �û�����"Backspace"��
            handleBackspace();
        } else if (label.equals(COMMAND[1])) {
            // �û�����"CE"��
            resultText.setText("0");
        } else if (label.equals(COMMAND[2])) {
            // �û�����"C"��
            handleC();
        } else if ("0123456789.".indexOf(label) >= 0) {
            // �û��������ּ�����С�����
            handleNumber(label);
            // handlezero(zero);
        } else {
            // �û������������
            handleOperator(label);
        }
    }
    
    /**
     * �������
     */
    public void mouseClicked(MouseEvent e){
    }
    
    public void mouseEntered(MouseEvent e){
    	//��ȡ�¼�Դ�ı�ǩ
    	JButton btn = (JButton) e.getSource();
    	//������������ɫ�
    	btn.setBackground(Color.LIGHT_GRAY);
    }

    public void mouseExited(MouseEvent e){
     	//��ȡ�¼�Դ�ı�ǩ
    	JButton btn = (JButton) e.getSource();
    	//������������ɫǳ��
    	btn.setBackground(Color.WHITE);
    }
    
    public void mousePressed(MouseEvent e){
    }
    
    public void mouseReleased(MouseEvent e){
       	//��ȡ�¼�Դ�ı�ǩ
    	JButton btn = (JButton) e.getSource();
    	//������������ɫǳ��
    	btn.setBackground(Color.LIGHT_GRAY);
    }
    
    /**
     * �������
     */
    
    public void keyTyped(KeyEvent e){
    }
    
    public void keyPressed(KeyEvent e){
    }
    
    public void keyReleased(KeyEvent e){
    }
    
    /**
     * ����Backspace�������µ��¼�
     */
    private void handleBackspace() {
        String text = resultText.getText();
        int i = text.length();
        if (i > 0) {
            // �˸񣬽��ı����һ���ַ�ȥ��
            text = text.substring(0, i - 1);
            if (text.length() == 0) {
                // ����ı�û�������ݣ����ʼ���������ĸ���ֵ
                resultText.setText("0");
                firstDigit = true;
                operator = "=";
            } else {
                // ��ʾ�µ��ı�
                resultText.setText(text);
            }
        }
    }
 
    /**
     * �������ּ������µ��¼�
     *
     * @param key
     */
    private void handleNumber(String key) {
        if (firstDigit) {
            // ����ĵ�һ������
            resultText.setText(key);
        } else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
            // �������С���㣬����֮ǰû��С���㣬��С���㸽�ڽ���ı���ĺ���
            resultText.setText(resultText.getText() + ".");
        } else if (!key.equals(".")) {
            // �������Ĳ���С���㣬�����ָ��ڽ���ı���ĺ���
            resultText.setText(resultText.getText() + key);
        }
        // �Ժ�����Ŀ϶����ǵ�һ��������
        firstDigit = false;
    }
 
    /**
     * ����C�������µ��¼�
     */
    private void handleC() {
        // ��ʼ���������ĸ���ֵ
        resultText.setText("0");
        firstDigit = true;
        operator = "=";
    }
 
    /**
     * ����������������µ��¼�
     *
     * @param key
     */
    private void handleOperator(String key) {
        if (operator.equals("/")) {
            // ��������
            // �����ǰ����ı����е�ֵ����0
            if (getNumberFromText() == 0.0) {
                // �������Ϸ�
                operateValidFlag = false;
                resultText.setText("��������Ϊ��");
            } else {
                resultNum /= getNumberFromText();
            }
        } else if (operator.equals("1/x")) {
            // ��������
            if (resultNum == 0.0) {
                // �������Ϸ�
                operateValidFlag = false;
                resultText.setText("��û�е���");
            } else {
                resultNum = 1 / resultNum;
            }
        } else if (operator.equals("+")) {
            // �ӷ�����
            resultNum += getNumberFromText();
        } else if (operator.equals("-")) {
            // ��������
            resultNum -= getNumberFromText();
        } else if (operator.equals("*")) {
            // �˷�����
            resultNum *= getNumberFromText();
        } else if (operator.equals("sqrt")) {
            // ƽ��������
            resultNum = Math.sqrt(resultNum);
        } else if (operator.equals("%")) {
            // �ٷֺ����㣬����100
            resultNum = resultNum / 100;
        } else if (operator.equals("+/-")) {
            // ������������
            resultNum = resultNum * (-1);
        } else if (operator.equals("=")) {
            // ��ֵ����
            resultNum = getNumberFromText();
        }
        if (operateValidFlag) {
            // ˫���ȸ�����������
            long t1;
            double t2;
            t1 = (long) resultNum;
            t2 = resultNum - t1;
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));
            } else {
                resultText.setText(String.valueOf(resultNum));
            }
        }
        // ����������û����İ�ť
        operator = key;
        firstDigit = true;
        operateValidFlag = true;
    }
 
    /**
     * �ӽ���ı����л�ȡ����
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

    public static void main(String[] args) {
        Calculator calculator1 = new Calculator();
        calculator1.setVisible(true);
        calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

