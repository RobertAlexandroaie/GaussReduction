/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.GaussMatrix;
import model.StatesAdapter;
import util.StateManager;

/**
 * @author Robert
 */
public class MainFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private ArrayList<JTextField> txtFds;

    private void setPanel(JPanel pnl, StatesAdapter sa, JLabel lbl, int step) {
	TitledBorder border = (TitledBorder) pnl.getBorder();
	if (border != null) {
	    border.setTitle("Pas " + step);
	}
	lbl.setText(sa.sysToColoredString(sa.getGaussMatrix().getbInit()));
	pnl.repaint();
    }

    private void resize() {
	// TODO resize shet
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    MainFrame frame = new MainFrame();
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
    public MainFrame() {
	final StatesAdapter sa = new StatesAdapter();
	txtFds = new ArrayList<>();
	FlowLayout fl = new FlowLayout();

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 378, 304);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	tabbedPane.setBounds(0, 0, 362, 264);
	contentPane.add(tabbedPane);

	JPanel pnlCreate = new JPanel();
	tabbedPane.addTab("Creare", null, pnlCreate, null);
	pnlCreate.setLayout(null);

	JPanel pnlSys = new JPanel();
	pnlSys.setBorder(new TitledBorder(null, "Sistemul de ecuatii", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
	pnlSys.setBounds(0, 0, 357, 200);
	pnlCreate.add(pnlSys);
	pnlSys.setLayout(null);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setViewportBorder(null);
	scrollPane.setBounds(3, 17, 352, 180);
	pnlSys.add(scrollPane);

	final JLabel lblEcSys = new JLabel("");
	scrollPane.setViewportView(lblEcSys);

	JButton btnIncarcaSistem = new JButton("Incarca sistem");
	btnIncarcaSistem.setBounds(0, 200, 357, 35);
	pnlCreate.add(btnIncarcaSistem);

	JPanel pnlGaussRed = new JPanel();
	tabbedPane.addTab("GaussRed", null, pnlGaussRed, null);
	pnlGaussRed.setLayout(null);

	final JPanel pnlStep = new JPanel();
	pnlStep.setBorder(new TitledBorder(null, "Pas", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
	pnlStep.setBounds(0, 0, 357, 200);
	pnlGaussRed.add(pnlStep);
	pnlStep.setLayout(null);

	JScrollPane scrollPane_1 = new JScrollPane();
	scrollPane_1.setBounds(3, 17, 352, 180);
	pnlStep.add(scrollPane_1);

	final JLabel lblRedSys = new JLabel("");
	scrollPane_1.setViewportView(lblRedSys);

	final JButton btnPrev = new JButton("Prev");

	btnPrev.setEnabled(false);
	btnPrev.setBounds(0, 200, 178, 36);
	pnlGaussRed.add(btnPrev);

	final JButton btnNext = new JButton("Next");
	btnNext.setEnabled(false);
	btnNext.setBounds(179, 200, 178, 36);
	pnlGaussRed.add(btnNext);

	JPanel pnlCalcul = new JPanel();
	tabbedPane.addTab("Calcul", null, pnlCalcul, null);
	pnlCalcul.setLayout(null);

	JPanel pnlSolvedSys = new JPanel();
	pnlSolvedSys.setBorder(new TitledBorder(null, "Sistemul de ecuatii", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
	pnlSolvedSys.setBounds(0, 0, 228, 200);
	pnlCalcul.add(pnlSolvedSys);
	pnlSolvedSys.setLayout(null);

	JScrollPane scrollPane_2 = new JScrollPane();
	scrollPane_2.setBounds(3, 17, 220, 180);
	pnlSolvedSys.add(scrollPane_2);

	final JLabel lblSys = new JLabel("");
	scrollPane_2.setViewportView(lblSys);

	JPanel pnlSol = new JPanel();
	pnlSol.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Solutii", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null,
		null));
	pnlSol.setBounds(230, 0, 127, 102);
	pnlCalcul.add(pnlSol);
	pnlSol.setLayout(null);

	JScrollPane scrollPane_3 = new JScrollPane();
	scrollPane_3.setBounds(3, 18, 121, 84);
	pnlSol.add(scrollPane_3);

	final JLabel lblSol = new JLabel("");
	scrollPane_3.setViewportView(lblSol);

	final JButton btnResetB = new JButton("Reseteaza valorile");
	btnResetB.setEnabled(false);
	btnResetB.setBounds(179, 200, 178, 36);
	pnlCalcul.add(btnResetB);

	final JButton btnCalc = new JButton("Calculeaza");

	btnCalc.setEnabled(false);
	btnCalc.setBounds(0, 200, 178, 36);
	pnlCalcul.add(btnCalc);

	final JPanel pnlCoef = new JPanel();
	pnlCoef.setBorder(new TitledBorder(null, "Coeficienti liberi", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	pnlCoef.setBounds(230, 102, 127, 96);
	pnlCalcul.add(pnlCoef);
	pnlCoef.setLayout(null);

	JScrollPane scrollPane_4 = new JScrollPane();
	scrollPane_4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scrollPane_4.setBounds(4, 14, 120, 77);
	pnlCoef.add(scrollPane_4);

	final JPanel pnlFds = new JPanel();
	scrollPane_4.setViewportView(pnlFds);
	pnlFds.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

	btnIncarcaSistem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		File file;
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(MainFrame.this);
		file = fc.getSelectedFile();

		if (file != null) {
		    String filePath = file.getAbsolutePath();
		    GaussMatrix gaussMatrix = GaussMatrix.loadGaussMatrixFromFile(filePath);
		    gaussMatrix.startGaussReduction();
		    sa.setGaussMatrix(gaussMatrix);
		    sa.setStates(StateManager.getStates());
		    btnNext.setEnabled(true);
		    btnPrev.setEnabled(true);
		    if (gaussMatrix.isSingular()) {
			JOptionPane.showMessageDialog(MainFrame.this, "Matrice singulara!");
		    }

		    String txt = sa.sysToColoredString(gaussMatrix.getbInit());
		    lblEcSys.setText(txt);
		    lblSys.setText(txt);
		}
	    }
	});

	tabbedPane.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
		int tabIndex = tabbedPane.getSelectedIndex();

		if (tabIndex == 1) {
		    setPanel(pnlStep, sa, lblRedSys, sa.getStep());
		} else if (tabIndex == 2 && !sa.getGaussMatrix().isSingular() && sa.getStep() == 0) {
		    btnCalc.setEnabled(true);
		    btnResetB.setEnabled(true);
		    int size = sa.getStates().size();

		    pnlFds.setLayout(new GridLayout(size, 1));

		    for (int i = 0; i < size; i++) {
			JTextField newTxtFd = new JTextField(10);
			txtFds.add(newTxtFd);
			pnlFds.add(newTxtFd);
		    }
		    pnlCoef.repaint();
		}
	    }
	});

	btnPrev.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		sa.prev();
		setPanel(pnlStep, sa, lblRedSys, sa.getStep());
	    }
	});

	btnNext.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		sa.next();
		setPanel(pnlStep, sa, lblRedSys, sa.getStep());
	    }
	});

	btnCalc.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		GaussMatrix gm = sa.getGaussMatrix();
		gm.solveUpperTriangularSystem(gm.getbVector());
		lblSol.setText(sa.solToString());
	    }
	});

	btnResetB.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		ArrayList<Double> bVector = new ArrayList<Double>();
		try {
		    GaussMatrix gm = sa.getGaussMatrix();
		    int size = gm.getMatrix().size();
		    for (int i = 0; i < size; i++) {
			Double b = Double.parseDouble(txtFds.get(i).getText());
			bVector.add(b);
		    }
		    gm.solveUpperTriangularSystem(bVector);
		    lblSol.setText(sa.solToString());
		    lblSys.setText(sa.sysToColoredString(bVector));
		} catch (Exception ex) {
		    ex.printStackTrace();
		    JOptionPane.showMessageDialog(MainFrame.this, "Verificati coeficientii! Trebuie sa fie numere reale!");
		}
	    }
	});
    }
}
