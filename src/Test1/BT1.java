package Test1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class BT1 extends JFrame {

	private JPanel contentPane;
	private JTextField txtString;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BT1 frame = new BT1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BT1() {
		JFrame temp = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nh\u1EADp chu\u1ED7i");
		lblNewLabel.setBounds(55, 59, 67, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Th\u1EF1c hi\u1EC7n");
		lblNewLabel_1.setBounds(55, 96, 67, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("K\u1EBFt qu\u1EA3");
		lblNewLabel_2.setBounds(55, 137, 67, 14);
		contentPane.add(lblNewLabel_2);
		
		txtString = new JTextField();
		txtString.setBounds(133, 56, 221, 20);
		contentPane.add(txtString);
		txtString.setColumns(10);
		
		String[] Func = { "Ä�áº¿m tá»«", "Ä�áº¿m tá»« trÃ¹ng láº·p", "Ä�áº£o chuá»—i"};
		JComboBox cbbFunc = new JComboBox(Func);
		cbbFunc.setBounds(132, 92, 222, 22);
		contentPane.add(cbbFunc);
		
		JEditorPane txtResult = new JEditorPane();
		txtResult.setBounds(132, 137, 222, 97);
		contentPane.add(txtResult);
		
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = txtString.getText();
				if (text.length() == 0) return;
				switch(cbbFunc.getSelectedIndex()) {
				case 0: txtResult.setText(WordCount(text)); break;
				case 1: txtResult.setText(RepeatedWordCount(text)); break;
				case 2: txtResult.setText(WordReverse(text)); break;
				}
			}
		});
		btnView.setBounds(133, 245, 67, 23);
		contentPane.add(btnView);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtString.setText("");
				txtResult.setText("");
			}
		});
		btnReset.setBounds(210, 245, 67, 23);
		contentPane.add(btnReset);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				temp.dispose();
			}
		});
		btnExit.setBounds(287, 245, 67, 23);
		contentPane.add(btnExit);
	}
	
	private String WordCount(String s) {
		String[] words = s.split(" ");
		return words.length + "";
	}
	
	private String RepeatedWordCount(String s) {
		String res = new String();
		s.toLowerCase();
		String[] words = s.split(" ");
		ArrayList<String> explored = new ArrayList<String>();
		ArrayList<String> wcount = new ArrayList<String>();
		
		Arrays.sort(words);
		
		int count = 0;
		for (String string : words) {
			if (explored.indexOf(string) == -1) {
				explored.add(string);
				wcount.add(count + "");
				count = 1;
			}
			else count++;
		}
		
		int index = 0;
		for(String str: explored){
			if (index == explored.size() - 1) res += str + " : " + wcount.get(index);
			else res += str + " : " + wcount.get(index) + "\n";
			index++;
		}
		
		return res;
	}
	
	private String WordReverse(String s) {
		String[] temp = s.split(" ");
		
		s = "";
		
		for (int i = temp.length - 1; i>=0; i--) {
			if (i == 0) s += temp[i];
			else s += temp[i] + " ";
		}
		
		return s;
	}
}
